package com.gateway.infrastructure.zuulfilters;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class SetCookiesOnLoginFilter extends ZuulFilter {

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
        return ZuulUtils.isTokenRequest(RequestContext.getCurrentContext());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try (final InputStream responseDataStream = context.getResponseDataStream()) {
            String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
            JSONObject response = new JSONObject(responseData);
            String authCookie = prepareAuthCookie(response.getString("access_token")).toString();
            context.addZuulResponseHeader(HttpHeaders.SET_COOKIE, authCookie);
            context.addZuulResponseHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            context.addZuulResponseHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            context.setResponseBody(responseData);
        } catch (Exception e) {
            throw new ZuulException(e, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return null;
    }

    private ResponseCookie prepareAuthCookie(String accessToken) {
        return ResponseCookie
                .from(HttpHeaders.AUTHORIZATION, accessToken)
                .httpOnly(true)
//                .secure(true)
                .path("/")
                .maxAge(86400)
                .domain("localhost")
                .build();
    }
}
