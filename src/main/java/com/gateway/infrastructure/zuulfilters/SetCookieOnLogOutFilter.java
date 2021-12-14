package com.gateway.infrastructure.zuulfilters;

import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class SetCookieOnLogOutFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return ZuulUtils.isLogOutRequest(RequestContext.getCurrentContext());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try (final InputStream responseDataStream = context.getResponseDataStream()) {
            context.addZuulResponseHeader(HttpHeaders.SET_COOKIE, prepareLogOutCookie().toString());
        } catch (Exception e) {
            throw new ZuulException(e, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return null;
    }

    private ResponseCookie prepareLogOutCookie() {
        return ResponseCookie
                .from(HttpHeaders.AUTHORIZATION, "")
                .httpOnly(true)
//                .secure(true)
                .path("/")
                .maxAge(86400)
                .domain("localhost")
                .sameSite("Strict")
                .build();
    }

}
