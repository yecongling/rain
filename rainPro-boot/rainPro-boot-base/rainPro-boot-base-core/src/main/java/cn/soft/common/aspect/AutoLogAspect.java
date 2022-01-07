package cn.soft.common.aspect;

import cn.soft.common.constant.CommonConstant;
import cn.soft.modules.base.mapper.BaseCommonMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    /**
     * 切面
     * @param point 连接点
     * @return 返回执行结果
     * @throws Throwable /
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time, result);
        return result;
    }

    /**
     * 保存日志
     * @param joinPoint 连接点
     * @param time 时长
     * @param obj 执行结果
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time, Object obj) {

    }

    /**
     * 获取操作类型
     */
    private int getOperateType(String methodName,int operateType) {
        if (operateType > 0) {
            return operateType;
        }
        if (methodName.startsWith("list")) {
            return CommonConstant.OPERATE_TYPE_1;
        }
        if (methodName.startsWith("add")) {
            return CommonConstant.OPERATE_TYPE_2;
        }
        if (methodName.startsWith("edit")) {
            return CommonConstant.OPERATE_TYPE_3;
        }
        if (methodName.startsWith("delete")) {
            return CommonConstant.OPERATE_TYPE_4;
        }
        if (methodName.startsWith("import")) {
            return CommonConstant.OPERATE_TYPE_5;
        }
        if (methodName.startsWith("export")) {
            return CommonConstant.OPERATE_TYPE_6;
        }
        return CommonConstant.OPERATE_TYPE_1;
    }

    /**
     * 获取请求参数
     * @param request 请求
     * @param joinPoint 切入点
     * @return 返回请求的参数
     */
    public String getRequestParams(HttpServletRequest request, JoinPoint joinPoint) {

        return "";
    }

}
