package cn.soft.common.modules.redis.writer;

import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.cache.CacheStatistics;
import org.springframework.data.redis.cache.CacheStatisticsCollector;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.function.Function;

/**
 * 参照DefaultRedisCacheWriter 重写remove方法实现通配符*删除
 */
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


    }

    @Override
    public byte[] get(String name, byte[] key) {
        return new byte[0];
    }

    @Override
    public byte[] putIfAbsent(String name, byte[] key, byte[] value, Duration ttl) {
        return new byte[0];
    }

    @Override
    public void remove(String name, byte[] key) {

    }

    @Override
    public void clean(String name, byte[] pattern) {

    }

    @Override
    public void clearStatistics(String name) {

    }

    @Override
    public RedisCacheWriter withStatisticsCollector(CacheStatisticsCollector cacheStatisticsCollector) {
        return null;
    }

    @Override
    public CacheStatistics getCacheStatistics(String cacheName) {
        return null;
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
     * 创建cacheLockKey
     * @param name 名
     * @return /
     */
    private static byte[] createCacheLockKey(String name) {
        return (name + "~lock").getBytes(StandardCharsets.UTF_8);
    }
}
