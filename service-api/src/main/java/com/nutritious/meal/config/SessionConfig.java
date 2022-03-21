package com.nutritious.meal.config;

import com.nutritious.meal.dto.QomolangmaSecurityConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.annotation.Resource;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/18 16:37
 **/
@Configuration
@EnableConfigurationProperties({ServerProperties.class, QomolangmaSecurityConfigurationProperties.class})
public class SessionConfig {

    @Resource
    private ServerProperties serverProperties;
    @Resource
    private QomolangmaSecurityConfigurationProperties properties;

    /**
     * 同时支持cookie解析和请求头解析session
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(HttpSessionIdResolver.class)
    public HttpSessionIdResolver httpSessionIdResolver() {
        HttpSessionIdResolverComposite resolvers = new HttpSessionIdResolverComposite();
        CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
        cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer());
        resolvers.addHttpSessionIdResolver(cookieHttpSessionIdResolver);
        resolvers.addHttpSessionIdResolver(new HeaderHttpSessionIdResolver(properties.getHeaderName()));
        return resolvers;
    }

    @Bean
    DefaultCookieSerializer cookieSerializer() {
        Session.Cookie cookie = serverProperties.getServlet().getSession().getCookie();
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(cookie::getName).to(cookieSerializer::setCookieName);
        map.from(cookie::getDomain).to(cookieSerializer::setDomainName);
        map.from(cookie::getPath).to(cookieSerializer::setCookiePath);
        map.from(cookie::getHttpOnly).to(cookieSerializer::setUseHttpOnlyCookie);
        map.from(cookie::getSecure).to(cookieSerializer::setUseSecureCookie);
        map.from(cookie::getMaxAge).to((maxAge) -> cookieSerializer.setCookieMaxAge((int) maxAge.getSeconds()));
        return cookieSerializer;
    }
}
