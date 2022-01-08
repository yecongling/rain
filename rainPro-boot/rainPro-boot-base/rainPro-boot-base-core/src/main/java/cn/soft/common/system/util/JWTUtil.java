package cn.soft.common.system.util;

import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.constant.DataBaseConstant;
import cn.soft.common.exception.RainBootException;
import cn.soft.common.system.vo.LoginUser;
import cn.soft.common.system.vo.SysUserCacheInfo;
import cn.soft.common.util.DateUtils;
import cn.soft.common.util.SpringContextUtils;
import cn.soft.common.util.oConvertUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import org.apache.shiro.SecurityUtils;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * JWT工具类
 */
public class JWTUtil {
    // Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在redis缓存时间为准）
    public static final long EXPIRE_TIME = 30 * 60 * 1000;

    /**
     * 响应错误
     *
     * @param response 响应对象
     * @param code     响应码
     * @param errorMsg 错误消息
     */
    public static void responseError(ServletResponse response, Integer code, String errorMsg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Result jsonResult = new Result(code, errorMsg);
        OutputStream os = null;
        try {
            os = httpServletResponse.getOutputStream();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(401);
            os.write(new ObjectMapper().writeValueAsString(jsonResult).getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,30min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 根据request中的token获取用户账号
     *
     * @param request 请求
     * @return /
     * @throws RainBootException /
     */
    public static String getUserNameByToken(HttpServletRequest request) throws RainBootException {
        String accessToken = request.getHeader("X-Access-Token");
        String username = getUsername(accessToken);
        if (oConvertUtils.isEmpty(username)) {
            throw new RainBootException("未获取到用户");
        }
        return username;
    }

    /**
     * 从session中获取变量
     *
     * @param key /
     * @return /
     */
    public static String getSessionData(String key) {
        //${myVar}%
        //得到${} 后面的值
        String moshi = "";
        if (key.contains("}")) {
            moshi = key.substring(key.indexOf("}") + 1);
        }
        String returnValue = null;
        if (key.contains("#{")) {
            key = key.substring(2, key.indexOf("}"));
        }
        if (oConvertUtils.isNotEmpty(key)) {
            HttpSession session = SpringContextUtils.getHttpServletRequest().getSession();
            returnValue = (String) session.getAttribute(key);
        }
        //结果加上${} 后面的值
        if (returnValue != null) {
            returnValue = returnValue + moshi;
        }
        return returnValue;
    }

    /**
     * 从当前用户中获取变量
     *
     * @param key  变量key
     * @param user 用后
     * @return 返回变量
     */
    //TODO 急待改造
    public static String getUserSystemData(String key, SysUserCacheInfo user) {
        if (user == null) {
            user = RainDataAuthorUtils.loadUserInfo();
        }
        //#{sys_user_code}%

        // 获取登录用户信息
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        String moshi = "";
        if (key.contains("}")) {
            moshi = key.substring(key.indexOf("}") + 1);
        }
        String returnValue = null;
        //针对特殊标示处理#{sysOrgCode}，判断替换
        if (key.contains("#{")) {
            key = key.substring(2, key.indexOf("}"));
        }
        //替换为系统登录用户帐号
        if (key.equals(DataBaseConstant.SYS_USER_CODE) || key.toLowerCase().equals(DataBaseConstant.SYS_USER_CODE_TABLE)) {
            if (user == null) {
                returnValue = sysUser.getUsername();
            } else {
                returnValue = user.getSysUserCode();
            }
        }
        //替换为系统登录用户真实名字
        else if (key.equals(DataBaseConstant.SYS_USER_NAME) || key.toLowerCase().equals(DataBaseConstant.SYS_USER_NAME_TABLE)) {
            if (user == null) {
                returnValue = sysUser.getRealname();
            } else {
                returnValue = user.getSysUserName();
            }
        }

        //替换为系统用户登录所使用的机构编码
        else if (key.equals(DataBaseConstant.SYS_ORG_CODE) || key.toLowerCase().equals(DataBaseConstant.SYS_ORG_CODE_TABLE)) {
            if (user == null) {
                returnValue = sysUser.getOrgCode();
            } else {
                returnValue = user.getSysOrgCode();
            }
        }
        //替换为系统用户所拥有的所有机构编码
        else if (key.equals(DataBaseConstant.SYS_MULTI_ORG_CODE) || key.toLowerCase().equals(DataBaseConstant.SYS_MULTI_ORG_CODE_TABLE)) {
            if (user == null) {
                //TODO 暂时使用用户登录部门，存在逻辑缺陷，不是用户所拥有的部门
                returnValue = sysUser.getOrgCode();
            } else {
                if (user.isOneDepart()) {
                    returnValue = user.getSysMultiOrgCode().get(0);
                } else {
                    returnValue = Joiner.on(",").join(user.getSysMultiOrgCode());
                }
            }
        }
        //替换为当前系统时间(年月日)
        else if (key.equals(DataBaseConstant.SYS_DATE) || key.toLowerCase().equals(DataBaseConstant.SYS_DATE_TABLE)) {
            returnValue = DateUtils.formatDate();
        }
        //替换为当前系统时间（年月日时分秒）
        else if (key.equals(DataBaseConstant.SYS_TIME) || key.toLowerCase().equals(DataBaseConstant.SYS_TIME_TABLE)) {
            returnValue = DateUtils.now();
        }
        //流程状态默认值（默认未发起）
        else if (key.equals(DataBaseConstant.BPM_STATUS) || key.toLowerCase().equals(DataBaseConstant.BPM_STATUS_TABLE)) {
            returnValue = "1";
        }
        //update-begin-author:taoyan date:20210330 for:多租户ID作为系统变量
        else if (key.equals(DataBaseConstant.TENANT_ID) || key.toLowerCase().equals(DataBaseConstant.TENANT_ID_TABLE)) {
            returnValue = sysUser.getRelTenantIds();
            if (oConvertUtils.isEmpty(returnValue) || (returnValue != null && returnValue.indexOf(",") > 0)) {
                returnValue = SpringContextUtils.getHttpServletRequest().getHeader(CommonConstant.TENANT_ID);
            }
        }
        //update-end-author:taoyan date:20210330 for:多租户ID作为系统变量
        if (returnValue != null) {
            returnValue = returnValue + moshi;
        }
        return returnValue;
    }
}
