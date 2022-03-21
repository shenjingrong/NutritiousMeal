package com.nutritious.meal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/17 16:00
 **/
public class ProfileFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ProfileFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("---执行ProfileFilter过滤器---");
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("---结束ProfileFilter过滤器---");
    }

    @Override
    public void destroy() {

    }
}
