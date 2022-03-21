package com.nutritious.meal.dto;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 11:09
 **/
public class LoginReq {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
