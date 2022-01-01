package cn.soft.common.modules.redis.receiver;

import cn.hutool.core.util.ObjectUtil;
import cn.soft.common.base.BaseMap;
import cn.soft.common.constant.GlobalConstants;
import cn.soft.common.modules.redis.listener.RainProRedisListener;
import cn.soft.common.util.SpringContextHolder;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Redis的消息接收器
 */
@Component
@Data
public class RedisReceiver {

    /**
     * 接收消息并调用业务逻辑处理器
     * @param params 参数
     */
    public void onMessage(BaseMap params) {
        Object handlerName = params.get(GlobalConstants.HANDLER_NAME);
        RainProRedisListener messageListener = SpringContextHolder.getHandler(handlerName.toString(), RainProRedisListener.class);
        if (ObjectUtil.isNotEmpty(messageListener)) {
            messageListener.onMessage(params);
        }
    }
}
