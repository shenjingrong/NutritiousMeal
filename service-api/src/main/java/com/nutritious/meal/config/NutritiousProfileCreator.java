package com.nutritious.meal.config;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.creator.ProfileCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 17:41
 **/
public class NutritiousProfileCreator implements ProfileCreator<UsernamePasswordCredentials, CommonProfile> {

    private static final Logger logger = LoggerFactory.getLogger(NutritiousProfileCreator.class);

    @Override
    public CommonProfile create(UsernamePasswordCredentials usernamePasswordCredentials, WebContext webContext) {
        logger.info("---执行CommonProfile创建过程---");
        CommonProfile commonProfile = new CommonProfile();
        commonProfile.setId(usernamePasswordCredentials.getUsername());
        return commonProfile;
    }
}
