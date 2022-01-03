package cn.soft.common.modules.redis.listener;

import cn.soft.common.base.BaseMap;

/**
 * 自定义消息监听
 */
public interface RainProRedisListener {

    /**
     * 收到消息
     * @param message 消息
     */
    void onMessage(BaseMap message);
}
