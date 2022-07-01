package cn.soft.common.modules.redis.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 参照DefaultRedisCacheWriter 重写remove方法实现通配符*删除
 */
@Slf4j
public class RainProRedisCacheWriter implements RedisCacheWriter {

    private final RedisConnectionFactory connectionFactory;
    private final Duration sleepTime;

    public RainProRedisCacheWriter(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, Duration.ZERO);
    }

    public RainProRedisCacheWriter(RedisConnectionFactory connectionFactory, Duration sleepTime) {
        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");
        Assert.notNull(sleepTime, "SleepTime must not be null!");
        this.connectionFactory =connectionFactory;
        this.sleepTime = sleepTime;
    }

    @Override
    public void put(String name, byte[] key, byte[] value, Duration ttl) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");
        this.execute(name, (connection) -> {
            if (shouldExpireWithin(ttl)) {
                connection.set(key, value, Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.upsert());
            } else {
                connection.set(key, value);
            }
            return "OK";
        });
    }

    @Override
    public byte[] get(String name, byte[] key) {
        return this.execute(name, (connection) -> connection.get(key));
    }

    @Override
    public byte[] putIfAbsent(String name, byte[] key, byte[] value, Duration ttl) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");
        return (byte[])this.execute(name, (connection) -> {
            if (this.isLockingCacheWriter()) {
                this.doLock(name, connection);
            }
            Object result;
            try {
                boolean put;
                if (shouldExpireWithin(ttl)) {
                    put = connection.set(key, value, Expiration.from(ttl), RedisStringCommands.SetOption.ifAbsent());
                } else {
                    put = connection.setNX(key, value);
                }
                if (!put) {
                    byte[] var11 = connection.get(key);
                    return var11;
                }
                result = null;
            } finally {
                if (this.isLockingCacheWriter()) {
                    this.doUnlock(name, connection);
                }

            }
            return (byte[])result;
        });
    }

    @Override
    public void remove(String name, byte[] key) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        String keyString = new String(key);
        log.info("redis remove key:" + keyString);
        if(keyString.endsWith("*")){
            execute(name, connection -> {
                // 获取某个前缀所拥有的所有的键，某个前缀开头，后面肯定是*
                Set<byte[]> keys = connection.keys(key);
                int delNum = 0;
                for (byte[] keyByte : keys) {
                    delNum += connection.del(keyByte);
                }
                return delNum;
            });
        }else{
            this.execute(name, (connection) -> connection.del(new byte[][]{key}));
        }
    }

    @Override
    public void clean(String name, byte[] pattern) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(pattern, "Pattern must not be null!");
        this.execute(name, (connection) -> {
            boolean wasLocked = false;
            try {
                if (this.isLockingCacheWriter()) {
                    this.doLock(name, connection);
                    wasLocked = true;
                }
                byte[][] keys = (byte[][])((Set) Optional.ofNullable(connection.keys(pattern)).orElse(Collections.emptySet())).toArray(new byte[0][]);
                if (keys.length > 0) {
                    connection.del(keys);
                }
            } finally {
                if (wasLocked && this.isLockingCacheWriter()) {
                    this.doUnlock(name, connection);
                }

            }
            return "OK";
        });
    }

    /**
     * 上锁（调用Redis的方法  有这个可以则设置值  没有不操作）
     * @param name 名
     * @param connection 连接
     * @return /
     */
    private Boolean doLock(String name, RedisConnection connection) {
        return connection.setNX(createCacheLockKey(name), new byte[0]);
    }

    /**
     * 解锁（删除）
     * @param name 名
     * @param connection 连接
     * @return /
     */
    private Long doUnlock(String name, RedisConnection connection) {
        return connection.del(new byte[][]{createCacheLockKey(name)});
    }

    /**
     * 检测锁是否存在
     * @param name 名
     * @param connection 连接
     * @return /
     */
    boolean doCheckLock(String name, RedisConnection connection) {
        return connection.exists(createCacheLockKey(name));
    }

    /**
     * 是否是锁定的CacheWriter(设置了沉睡时间)
     * @return /
     */
    private boolean isLockingCacheWriter() {
        return !this.sleepTime.isZero() && !this.sleepTime.isNegative();
    }

    /**
     * 执行
     * @param name 名
     * @param callback 回调
     * @param <T> 类型
     * @return /
     */
    private <T> T execute(String name, Function<RedisConnection, T> callback) {
        try (RedisConnection connection = this.connectionFactory.getConnection()) {
            this.checkAndPotentiallyWaitUntilUnlocked(name, connection);
            return callback.apply(connection);
        }
    }

    /**
     * 执行
     * @param callback 回调
     */
    private void executeLockFree(Consumer<RedisConnection> callback) {
        try (RedisConnection connection = this.connectionFactory.getConnection()) {
            callback.accept(connection);
        }
    }

    /**
     * 检测
     * @param name 名
     * @param connection 连接
     */
    private void checkAndPotentiallyWaitUntilUnlocked(String name, RedisConnection connection) {
        if (this.isLockingCacheWriter()) {
            try {
                while (this.doCheckLock(name, connection)) {
                    Thread.sleep(this.sleepTime.toMillis());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new PessimisticLockingFailureException(String.format("Interrupted while waiting to unlock cache %s", name), e);
            }
        }
    }

    /**
     * 是否过期的数据
     * @param sleepTime 时间
     * @return /
     */
    private static boolean shouldExpireWithin(@Nullable Duration sleepTime) {
        return sleepTime != null && !sleepTime.isZero() && !sleepTime.isNegative();
    }

    /**
     * 创建cacheLockKey
     * @param name 名
     * @return /
     */
    private static byte[] createCacheLockKey(String name) {
        return (name + "~lock").getBytes(StandardCharsets.UTF_8);
    }
}
