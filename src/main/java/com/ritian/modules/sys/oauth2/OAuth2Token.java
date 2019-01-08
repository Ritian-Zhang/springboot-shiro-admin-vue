package com.ritian.modules.sys.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 * @author ritian.Zhang
 * @date 2019/01/08
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
