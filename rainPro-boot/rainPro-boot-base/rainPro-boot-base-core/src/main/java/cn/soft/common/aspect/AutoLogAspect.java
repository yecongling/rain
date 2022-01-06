package cn.soft.common.aspect;

import cn.soft.modules.base.mapper.BaseCommonMapper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统日志  切面处理类
 */
@Aspect
@Component
public class AutoLogAspect {

    @Resource
    private BaseCommonMapper baseCommonMapper;

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(cn.soft.common.aspect.annotation.AutoLog)")
    public void logPointCut() {

    }

}
