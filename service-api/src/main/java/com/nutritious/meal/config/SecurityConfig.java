package com.nutritious.meal.config;

import com.nutritious.meal.dto.QomolangmaSecurityConfigurationProperties;
import io.buji.pac4j.context.ShiroSessionStore;
import io.buji.pac4j.engine.ShiroSecurityLogic;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.profile.ShiroProfileManager;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;
import org.pac4j.core.engine.decision.AlwaysUseSessionProfileStorageDecision;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.creator.ProfileCreator;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.http.client.direct.DirectFormClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.Resource;
import javax.servlet.DispatcherType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 10:34
 **/
@Configuration
@EnableConfigurationProperties({QomolangmaSecurityConfigurationProperties.class})
@ConditionalOnProperty(prefix = "qomolangma.security", name = "enabled", havingValue = "true")
public class SecurityConfig {

    public static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Resource
    private QomolangmaSecurityConfigurationProperties properties;

    @ConditionalOnMissingBean(SessionManager.class)
    @ConditionalOnClass({ShiroFilterFactoryBean.class, Subject.class, SessionManager.class})
    @Bean
    public SessionManager sessionManager() {
        return new ServletContainerSessionManager();
    }

    @ConditionalOnMissingBean({SubjectFactory.class})
    @ConditionalOnClass({ShiroFilterFactoryBean.class, Subject.class})
    @Bean
    public SubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }

    @ConditionalOnMissingBean(Realm.class)
    @ConditionalOnClass({ShiroFilterFactoryBean.class, Subject.class})
    @Bean
    public Realm realm() {
        return new Pac4jRealm();
    }

    @ConditionalOnMissingBean(SecurityManager.class)
    @ConditionalOnBean(SessionManager.class)
    @ConditionalOnClass({ShiroFilterFactoryBean.class, Subject.class})
    @Bean
    public SecurityManager securityManager(SessionManager sessionManager, SubjectFactory subjectFactory, Realm realm) {
        logger.info("My First Apache Shiro Application");

        //1.
//        Factory<SecurityManager> factory = new DefaultWebSecurityManager();

        //2.
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setRealm(realm);

        //3.
        SecurityUtils.setSecurityManager(securityManager);

        return securityManager;
    }

    @Bean("nutritiousShiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, Config config,
                                                         ObjectProvider<ProfileFilter> clientObjectProvider) {
        logger.info("clientObjectProvider: {}", clientObjectProvider);
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setConfig(config);
        securityFilter.setClients(properties.getClients());
//        securityFilter.setClients("CasClient,DirectFormClient");
        securityFilter.setAuthorizers("");
        securityFilter.setMatchers("");
        ShiroSecurityLogic<Object, J2EContext> shiroSecurityLogic = new ShiroSecurityLogic<>();
        shiroSecurityLogic.setProfileStorageDecision(new AlwaysUseSessionProfileStorageDecision());
        securityFilter.setSecurityLogic(shiroSecurityLogic);
        shiroFilterFactoryBean.getFilters().put("securityFilter", securityFilter);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/**", "securityFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new DelegatingFilterProxy("nutritiousShiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
        filterRegistration.setOrder(5);
        return filterRegistration;
    }

    @Bean
    @ConditionalOnMissingBean(Config.class)
    public Config config(ObjectProvider<Client> clientObjectProvider,
                         ObjectProvider<Authorizer<CommonProfile>> namedAuthorizerObjectProvider) {
        Clients clients = new Clients("", clientObjectProvider.stream().collect(Collectors.toList()));
        Config config = new Config(clients);
        config.setSessionStore(shiroSessionStore());
        config.setProfileManagerFactory(ShiroProfileManager::new);

        namedAuthorizerObjectProvider.stream().forEach(authorizer -> config.addAuthorizer(authorizer.getClass().getSimpleName(), authorizer));
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(ShiroSessionStore.class)
    public ShiroSessionStore shiroSessionStore() {
        return new ShiroSessionStore();
    }

    @Bean
    public FilterRegistrationBean<ProfileFilter> profileFilterFilterRegistrationBean() {
        FilterRegistrationBean<ProfileFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new ProfileFilter());
        bean.setUrlPatterns(Collections.singleton("/*"));
        bean.setName("profileFilterFilter");
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean(DirectFormClient.class)
    public DirectFormClient directFormClient(ObjectProvider<CredentialsExtractor> credentialsExtractorObjectProvider,
                                             ObjectProvider<ProfileCreator<UsernamePasswordCredentials, CommonProfile>> profileCreatorObjectProvider,
                                             ObjectProvider<Authenticator<UsernamePasswordCredentials>> authenticatorObjectProvider) {
        DirectFormClient directFormClient = new DirectFormClient();
        credentialsExtractorObjectProvider.ifAvailable(directFormClient::setCredentialsExtractor);
        authenticatorObjectProvider.ifAvailable(directFormClient::setAuthenticator);
        profileCreatorObjectProvider.ifAvailable(directFormClient::setProfileCreator);
        return directFormClient;
    }

    @Bean
    public EncryptedPasswordFormExtractor encryptedPasswordFormExtractor() {
        return new EncryptedPasswordFormExtractor("username", "password");
    }

    @Bean
    public NutritiousAuthenticator nutritiousAuthenticator() {
        return new NutritiousAuthenticator();
    }

    @Bean
    public NutritiousProfileCreator nutritiousProfileCreator() {
        return new NutritiousProfileCreator();
    }

    @Bean
    @ConditionalOnMissingBean({org.pac4j.cas.client.CasClient.class, org.pac4j.cas.config.CasConfiguration.class})
    public org.pac4j.cas.client.CasClient casClient(org.pac4j.cas.config.CasConfiguration casConfiguration,
                                                    ObjectProvider<ProfileCreator<TokenCredentials, CommonProfile>> profileCreatorObjectProvider) {
        org.pac4j.cas.client.CasClient casClient = new org.pac4j.cas.client.CasClient(casConfiguration);
        ProfileCreator<TokenCredentials, CommonProfile> profileCreator = profileCreatorObjectProvider.getIfAvailable();
        if (profileCreator != null) {
            casClient.setProfileCreator(profileCreator);
        }
        casClient.setCallbackUrl(properties.getCas().getCallbackUrl());
        return casClient;
    }

    @Bean
    @ConditionalOnMissingBean(org.pac4j.cas.config.CasConfiguration.class)
    public org.pac4j.cas.config.CasConfiguration casConfiguration() {
        org.pac4j.cas.config.CasConfiguration configuration = new org.pac4j.cas.config.CasConfiguration();
        QomolangmaSecurityConfigurationProperties.Cas cas = properties.getCas();
        configuration.setLoginUrl(cas.getLoginUrl());
        configuration.setPrefixUrl(cas.getPrefixUrl());
        configuration.setRestUrl(cas.getRestUrl());
        if (CommonHelper.isNotBlank(cas.getProtocol())) {
            configuration.setProtocol(org.pac4j.cas.config.CasProtocol.valueOf(cas.getProtocol()));
        }
        configuration.setTimeTolerance(cas.getTimeTolerance());
        configuration.setAcceptAnyProxy(cas.isAcceptAnyProxy());
        configuration.setGateway(cas.isGateway());
        configuration.setRenew(cas.isRenew());
        return configuration;
    }
}
