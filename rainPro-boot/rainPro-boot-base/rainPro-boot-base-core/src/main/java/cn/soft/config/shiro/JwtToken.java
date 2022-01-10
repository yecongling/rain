package cn.soft.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWT鉴权
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = -7720638390879009932L;

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
