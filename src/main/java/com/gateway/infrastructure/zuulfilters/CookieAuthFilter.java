package com.gateway.infrastructure.zuulfilters;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class CookieAuthFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        addAuthCookieAsHeader(RequestContext.getCurrentContext());
        return null;
    }

    private void addAuthCookieAsHeader(RequestContext context) {
        if (authCookieShouldBeAddedAsHeader(context.getRequest())) {
            setAuthHeaderIfCookiePresent(context);
        }
    }

    private boolean authCookieShouldBeAddedAsHeader(HttpServletRequest request) {
        return (request.getCookies() != null && request.getHeader(HttpHeaders.AUTHORIZATION) == null);
    }

    private void setAuthHeaderIfCookiePresent(RequestContext context) {
        for (Cookie cookie : context.getRequest().getCookies()) {
            if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
                context.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, "Bearer " + cookie.getValue());
            }
        }
    }

}
