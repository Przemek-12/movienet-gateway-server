package com.gateway.infrastructure.zuulfilters;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.google.common.net.HttpHeaders;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class TokenRequestFilter extends ZuulFilter {

    @Value("${oauth2.gateway.client-id}")
    private String gatewayClientId;

    @Value("${oauth2.gateway.client-secret}")
    private String gatewayClientSecret;

    @Override
    public boolean shouldFilter() {
        return ZuulUtils.isTokenRequest(RequestContext.getCurrentContext());
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        context.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, generateBasicAuth());
        return null;
    }

    private String generateBasicAuth() {
        String basicAuthToEncode = gatewayClientId + ":" + gatewayClientSecret;
        return new StringBuilder()
                .append("Basic ")
                .append(Base64.getEncoder().encodeToString(basicAuthToEncode.getBytes()))
                .toString();
    }

}
