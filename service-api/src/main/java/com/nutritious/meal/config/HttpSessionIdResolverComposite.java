package com.nutritious.meal.config;

import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/18 16:38
 **/
public class HttpSessionIdResolverComposite implements HttpSessionIdResolver {
    private final List<HttpSessionIdResolver> httpSessionIdResolvers = new ArrayList<>();

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        for (HttpSessionIdResolver httpSessionIdResolver : httpSessionIdResolvers) {
            List<String> sessionIds = httpSessionIdResolver.resolveSessionIds(request);
            if (sessionIds != null && !sessionIds.isEmpty()) {
                return sessionIds;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        for (HttpSessionIdResolver httpSessionIdResolver : httpSessionIdResolvers) {
            httpSessionIdResolver.setSessionId(request, response, sessionId);
        }
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        for (HttpSessionIdResolver httpSessionIdResolver : httpSessionIdResolvers) {
            httpSessionIdResolver.expireSession(request, response);
        }
    }

    public void addHttpSessionIdResolver(HttpSessionIdResolver httpSessionIdResolver) {
        this.httpSessionIdResolvers.add(httpSessionIdResolver);
    }

    public void addHttpSessionIdResolvers(List<HttpSessionIdResolver> httpSessionIdResolvers) {
        this.httpSessionIdResolvers.addAll(httpSessionIdResolvers);
    }
}
