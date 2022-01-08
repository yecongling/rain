package cn.soft.common.aspect;

import cn.soft.common.api.CommonAPI;
import cn.soft.common.aspect.annotation.PermissionData;
import cn.soft.common.system.util.JWTUtil;
import cn.soft.common.system.util.RainDataAuthorUtils;
import cn.soft.common.system.vo.SysPermissionDataRuleModel;
import cn.soft.common.system.vo.SysUserCacheInfo;
import cn.soft.common.util.SpringContextUtils;
import cn.soft.common.util.oConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 数据权限切面处理类
 * 当被请求的方法有注解PermissionData时,会在往当前request中写入数据权限信息
 */
@Aspect
@Component
@Slf4j
public class PermissionDataAspect {

    private CommonAPI commonAPI;

    @Autowired
    public void setCommonAPI(CommonAPI commonAPI) {
        this.commonAPI = commonAPI;
    }

    /**
     * 定义切点
     */
    @Pointcut("@annotation(cn.soft.common.aspect.annotation.PermissionData)")
    public void pointCut() {
    }

    /**
     * 环绕
     *
     * @param point 切点
     * @return /
     * @throws Throwable /
     */
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        PermissionData pd = method.getAnnotation(PermissionData.class);
        String component = pd.pageComponent();

        String requestMethod = request.getMethod();
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        requestPath = filterUrl(requestPath);
        log.debug("拦截请求 >> " + requestPath + ";请求类型 >> " + requestMethod);
        String username = JWTUtil.getUserNameByToken(request);
        // 查询数据权限信息
        List<SysPermissionDataRuleModel> dataRules = commonAPI.queryPermissionDataRule(component, requestPath, username);
        if (dataRules != null && dataRules.size() > 0) {
            // 临时存储
            RainDataAuthorUtils.installDataSearchCondition(request, dataRules);
            SysUserCacheInfo cacheUser = commonAPI.getCacheUser(username);
            RainDataAuthorUtils.installUserInfo(cacheUser);
        }
        return point.proceed();
    }

    /**
     * 路径替换双斜杠
     *
     * @param requestPath /
     * @return /
     */
    private String filterUrl(String requestPath) {
        String url = "";
        if (oConvertUtils.isNotEmpty(requestPath)) {
            url = requestPath.replace("\\", "/");
            url = url.replace("//", "/");
            if (url.contains("//")) {
                url = filterUrl(url);
            }
        }
        return url;
    }

    /**
     * 获取请求地址
     *
     * @param request 请求
     * @return 请求地址
     */
    private String getJgAuthRequestPath(HttpServletRequest request) {
        String queryString = request.getQueryString();
        String requestPath = request.getRequestURI();
        if (oConvertUtils.isNotEmpty(queryString)) {
            requestPath += "?" + queryString;
        }
        // 去掉其他参数(保留一个参数) 例如：loginController.do?login
        if (requestPath.contains("&")) {
            requestPath = requestPath.substring(0, requestPath.indexOf("&"));
        }
        if (requestPath.contains("=")) {
            if (requestPath.contains(".do")) {
                requestPath = requestPath.substring(0, requestPath.indexOf(".do") + 3);
            } else {
                requestPath = requestPath.substring(0, requestPath.indexOf("?"));
            }
        }
        // 去掉项目路径
        requestPath = requestPath.substring(request.getContextPath().length() + 1);
        return filterUrl(requestPath);
    }

    private boolean moHuContain(List<String> list, String key) {
        for (String str : list) {
            if (key.contains(str)) {
                return true;
            }
        }
        return false;
    }
}
