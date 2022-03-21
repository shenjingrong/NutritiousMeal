package com.nutritious.meal.config;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 17:38
 **/
public class NutritiousAuthenticator implements Authenticator<UsernamePasswordCredentials> {

    private static final Logger logger = LoggerFactory.getLogger(NutritiousAuthenticator.class);

    @Override
    public void validate(UsernamePasswordCredentials usernamePasswordCredentials, WebContext webContext) {
        logger.info("---执行NutritiousAuthenticator认证过程---");
        String username = usernamePasswordCredentials.getUsername();
        String password = usernamePasswordCredentials.getPassword();
        if ("shenjr".equals(username) && "123".equals(password)) {
            CommonProfile commonProfile = new CommonProfile();
            commonProfile.setId(username);
        } else {
            throw HttpAction.forbidden(webContext);
        }
    }
}
