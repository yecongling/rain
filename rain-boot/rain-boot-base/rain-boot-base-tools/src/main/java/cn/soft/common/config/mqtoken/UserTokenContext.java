package cn.soft.common.config.mqtoken;

/**
 * 用户token上下文
 */
public class UserTokenContext {
    private static final ThreadLocal<String> userToken = new ThreadLocal<>();

    public UserTokenContext() {
    }

    /**
     * 获取用户token
     * @return 返回用户token
     */
    public static String getToken() {
        return userToken.get();
    }

    /**
     * 设置用户token
     * @param token token
     */
    public static void setToken(String token) {
        userToken.set(token);
    }
}
