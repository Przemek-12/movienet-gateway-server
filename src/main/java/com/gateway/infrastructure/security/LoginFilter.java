package com.gateway.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.google.common.net.HttpHeaders;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.nimbusds.jose.util.Base64;

@Component
public class LoginFilter extends ZuulFilter {

    @Value("${oauth2.gateway.client-id}")
    private String gatewayClientId;

    @Value("${oauth2.gateway.client-secret}")
    private String gatewayClientSecret;

    private final String REQUEST_URI_PROPERTY = "requestURI";
    private final String OAUTH_TOKEN_ENDPOINT = "/oauth/token";

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.get(REQUEST_URI_PROPERTY).equals(OAUTH_TOKEN_ENDPOINT);
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
        return new StringBuilder()
                .append("Basic ")
                .append(Base64.encode(gatewayClientId + ":" + gatewayClientSecret))
                .toString();
    }

}