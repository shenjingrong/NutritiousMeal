package com.nutritious.meal.config;

import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 16:55
 **/
@Component
public class PermissionAuthorizer implements Authorizer<CommonProfile> {

    private static final Logger logger = LoggerFactory.getLogger(PermissionAuthorizer.class);

    @Override
    public boolean isAuthorized(WebContext webContext, List<CommonProfile> list) {
        logger.info("---执行PermissionAuthorizer授权---");
        HttpServletRequest request = ((J2EContext) webContext).getRequest();

        return true;
    }
}
