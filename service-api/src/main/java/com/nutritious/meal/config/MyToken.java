package com.nutritious.meal.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 15:06
 **/
public class MyToken implements AuthenticationToken {

    private String userName;
    private String password;

    public MyToken(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Object getPrincipal() {
        return this.userName;
    }

    public Object getCredentials() {
        return this.password;
    }
}
