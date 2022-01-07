package cn.soft.common.aspect;

import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.util.oConvertUtils;
import cn.soft.modules.base.mapper.BaseCommonMapper;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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
        String heepMethod = request.getMethod();
        StringBuilder params = new StringBuilder();
        if ("POST".equals(heepMethod) || "PUT".equals(heepMethod) || "PATCH".equals(heepMethod)) {
            Object[] paramsArray = joinPoint.getArgs();
            Object[] arguments = new Object[params.length()];
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof BindResult || paramsArray[i] instanceof ServletRequest || paramsArray[i] instanceof ServletResponse || paramsArray[i] instanceof MultipartFile) {
                    continue;
                }
                arguments[i] = paramsArray[i];
            }
            // 日志数据太长的直接过滤掉
            PropertyFilter propertyFilter = (o, name, value) -> {
                return value == null || value.toString().length() <= 500;
            };
            params = new StringBuilder(JSONObject.toJSONString(arguments, propertyFilter));
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 请求的方法参数值
            Object[] args = joinPoint.getArgs();
            // 请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);
            if (args != null && parameterNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params.append(" ").append(parameterNames[i]).append(": ").append(args[i]);
                }
            }
        }
        return params.toString();
    }

    /**
     * online日志内容拼接
     * @param obj 对象
     * @param content 内容
     * @return /
     */
    private String getOnlineLogContent(Object obj, String content){
        if (obj instanceof Result){
            Result res = (Result)obj;
            String msg = res.getMessage();
            String tableName = res.getOnlTable();
            if(oConvertUtils.isNotEmpty(tableName)){
                content+=",表名:"+tableName;
            }
            if(res.isSuccess()){
                content+= ","+(oConvertUtils.isEmpty(msg)?"操作成功":msg);
            }else{
                content+= ","+(oConvertUtils.isEmpty(msg)?"操作失败":msg);
            }
        }
        return content;
    }

}
