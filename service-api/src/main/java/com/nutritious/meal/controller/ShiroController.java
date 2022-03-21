package com.nutritious.meal.controller;

import com.nutritious.meal.config.MyToken;
import com.nutritious.meal.dto.LoginReq;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 11:00
 **/
@RestController
@RequestMapping("/user")
public class ShiroController {

    private static final Logger log = LoggerFactory.getLogger(ShiroController.class);

    @PostMapping("login")
    public Object login(@RequestBody LoginReq loginReq) {
//        Subject currentUser = currentUser(loginReq);
//        //print their identifying principal (in this case, a username):
//        log.info( "User [" + currentUser.getPrincipal() + "] logged in successfully." );
//        if ( currentUser.hasRole( "schwartz" ) ) {
//            log.info("May the Schwartz be with you!" );
//        } else {
//            log.info( "Hello, mere mortal." );
//        }
//        if ( currentUser.isPermitted( "lightsaber:wield" ) ) {
//            log.info("You may use a lightsaber ring.  Use it wisely.");
//        } else {
//            log.info("Sorry, lightsaber rings are for schwartz masters only.");
//        }
//        if ( currentUser.isPermitted( "winnebago:drive:eagle5" ) ) {
//            log.info("You are permitted to 'drive' the 'winnebago' with license plate (id) 'eagle5'.  " +
//                    "Here are the keys - have fun!");
//        } else {
//            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
//        }
        return "login success";
    }

    @PostMapping("logout")
    public String logout(@RequestBody LoginReq loginReq) {
        Subject currentUser = currentUser(loginReq);
        currentUser.logout();
        return "OK";
    }

    private Subject currentUser(LoginReq loginReq) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        if (!currentUser.isAuthenticated()) {
//            UsernamePasswordToken token = new UsernamePasswordToken(loginReq.getUsername(), loginReq.getPassword());
//            token.setRememberMe(true);
            MyToken token = new MyToken(loginReq.getUsername(), loginReq.getPassword());
            try {
                currentUser.login(token);
                //if no exception, that's it, we're done!
            } catch (UnknownAccountException uae) {
                //username wasn't in the system, show them an error message?
                log.error("用户不存在");
                throw new RuntimeException("用户不存在");
            } catch (IncorrectCredentialsException ice) {
                //password didn't match, try again?
                log.error("密码不正确");
                throw new RuntimeException("密码不正确");
            } catch (LockedAccountException lae) {
                //account for that username is locked - can't login.  Show them a message?
                log.error("用户已被锁定");
                throw new RuntimeException("用户已被锁定");
            } catch (AuthenticationException ae) {
                //unexpected condition - error?
                log.error("登录异常");
                throw new RuntimeException("登录异常");
            }
        }
        return currentUser;
    }
}
