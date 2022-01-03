package cn.soft.common.modules.redis.client;

import cn.soft.common.base.BaseMap;
import cn.soft.common.constant.GlobalConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Redis客户端
 */
@Configuration
public class RainProRedisClient {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 发送消息
     * @param handlerName 处理器名
     * @param params 参数
     */
    public void sendMessage(String handlerName, BaseMap params) {
        params.put(GlobalConstants.HANDLER_NAME, handlerName);
        redisTemplate.convertAndSend(GlobalConstants.REDIS_TOPIC_NAME, params);
    }
}
