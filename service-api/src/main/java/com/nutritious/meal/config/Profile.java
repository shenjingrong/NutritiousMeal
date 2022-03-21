package com.nutritious.meal.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 15:11
 **/
public class Profile implements AuthenticationInfo {

    private String userName;
    private String realmName;

    public Profile(String userName, String realmName) {
        this.userName = userName;
        this.realmName = realmName;
    }

    public PrincipalCollection getPrincipals() {
        return new SimplePrincipalCollection(userName, realmName);
    }

    public Object getCredentials() {
        return userName;
    }
}
