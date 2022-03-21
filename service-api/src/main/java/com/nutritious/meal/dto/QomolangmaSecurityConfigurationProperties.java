package com.nutritious.meal.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/21 9:15
 **/
@Data
@ConfigurationProperties(prefix = "qomolangma.security")
public class QomolangmaSecurityConfigurationProperties {

    private boolean enabled;
    private String headerName = "auth-token";
    private String clients;
    private Cas cas;


    @Data
    public class Cas {
        private String loginUrl;
        private String prefixUrl;
        private String callbackUrl;
        private String restUrl;
        private String protocol;
        private long timeTolerance = 1000L;
        private boolean acceptAnyProxy;
        private boolean gateway;
        private boolean renew;
    }

}
