package com.gateway.infrastructure.zuulfilters;

import com.gateway.infrastructure.security.SecurityUtils;
import com.netflix.zuul.context.RequestContext;

public class ZuulUtils {

    public static boolean isTokenRequest(RequestContext requestContext) {
        return requestContext.get(SecurityUtils.REQUEST_URI_PROPERTY).equals(SecurityUtils.OAUTH_TOKEN_ENDPOINT);
    }

    private ZuulUtils() {
    }
}
