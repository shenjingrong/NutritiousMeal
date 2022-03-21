package com.nutritious.meal.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 14:58
 **/
public class MyReal implements Realm {

    private static final Logger log = LoggerFactory.getLogger(MyReal.class);

    public String getName() {
        return "myReal";
    }

    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof MyToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if ("shenjr".equals(authenticationToken.getPrincipal()) && "123".equals(authenticationToken.getCredentials())) {
            log.info("认证成功");
        } else {
            log.error("认证失败");
            throw new AuthenticationException("认证失败");
        }
        return new Profile(String.valueOf(authenticationToken.getPrincipal()), getName());
    }
}
