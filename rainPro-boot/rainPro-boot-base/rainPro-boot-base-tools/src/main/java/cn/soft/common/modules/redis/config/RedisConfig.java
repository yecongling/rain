package cn.soft.common.modules.redis.config;

import cn.soft.common.constant.CacheConstant;
import cn.soft.common.modules.redis.writer.RainProRedisCacheWriter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

import static java.util.Collections.singletonMap;

/**
 * 开启缓存支持
 */
@Slf4j
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * RedisTemplate的配置
     * @param lettuceConnectionFactory 连接工程
     * @return 返回
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        log.info("------------redis config init------------");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    /**
     * 缓存配置管理器
     * @param lettuceConnectionFactory 连接池工厂
     * @return 返回缓存管理器对象
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = jacksonSerializer();
        // 配置序列化（解决乱码的问题）,并且配置缓存默认有效期 6小时
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(6));
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        //.disableCachingNullValues();

        // 以锁写入的方式创建RedisCacheWriter对象
        //update-begin-author:taoyan date:20210316 for:注解CacheEvict根据key删除redis支持通配符*
        RedisCacheWriter writer = new RainProRedisCacheWriter(lettuceConnectionFactory, Duration.ofMillis(50L));
        //RedisCacheWriter.lockingRedisCacheWriter(factory);
        // 创建默认缓存配置对象
        /* 默认配置，设置缓存有效期 1小时*/
        // RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));
        /* 自定义配置test:demo 的超时时间为 5分钟*/
        // 注解CacheEvict根据key删除redis支持通配符*
        return RedisCacheManager.builder(writer).cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(singletonMap(CacheConstant.SYS_DICT_TABLE_CACHE,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).disableCachingNullValues()
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))))
                .withInitialCacheConfigurations(singletonMap(CacheConstant.TEST_DEMO_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).disableCachingNullValues()))
                .withInitialCacheConfigurations(singletonMap(CacheConstant.PLUGIN_MALL_RANKING, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)).disableCachingNullValues()))
                .withInitialCacheConfigurations(singletonMap(CacheConstant.PLUGIN_MALL_PAGE_LIST, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)).disableCachingNullValues()))
                .transactionAware().build();
    }

    /**
     * 序列化生成器
     * @return
     */
    private Jackson2JsonRedisSerializer jacksonSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
}
