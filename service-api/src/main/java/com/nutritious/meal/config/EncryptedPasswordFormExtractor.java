package com.nutritious.meal.config;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.extractor.FormExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 18:04
 **/
public class EncryptedPasswordFormExtractor extends FormExtractor {

    public static final Logger logger = LoggerFactory.getLogger(EncryptedPasswordFormExtractor.class);

    public EncryptedPasswordFormExtractor(String usernameParameter, String passwordParameter) {
        super(usernameParameter, passwordParameter);
    }

    public UsernamePasswordCredentials extract(WebContext context) {
        logger.info("---执行EncryptedPasswordFormExtractor---");
        return super.extract(context);
    }
}
