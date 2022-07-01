package cn.soft.common.config.mqtoken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 存放token到上下文供队列调用
 */
public class TransmitUserTokenFilter implements Filter {

    private static final String X_ACCESS_TOKEN = "X-Access-Token";

    public TransmitUserTokenFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    /**
     * 初始化用户信息
     * @param request 请求
     */
    private void initUserInfo(HttpServletRequest request) {
        String token = request.getHeader(X_ACCESS_TOKEN);
        if (token != null) {
            // 将token放入上下文
            UserTokenContext.setToken(token);
        }
    }

    @Override
    public void destroy() {

    }
}
