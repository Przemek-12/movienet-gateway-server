package com.gateway.infrastructure.security.filters;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.gateway.infrastructure.security.tools.MutableServletRequest;

public class CookieAuthFilter extends BasicAuthenticationFilter {

    public CookieAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        Optional<MutableServletRequest> mutableServletRequestOpt = addAuthCookieAsHeader(request);
        if (mutableServletRequestOpt.isPresent()) {

            System.out.println(mutableServletRequestOpt.get().getHeaderNames());
            chain.doFilter(mutableServletRequestOpt.get(), response);
            return;
        }
        chain.doFilter(request, response);
    }

    private Optional<MutableServletRequest> addAuthCookieAsHeader(HttpServletRequest request) {
        if (authCookieShouldBeAddedAsHeader(request)) {
            return setAuthHeaderIfCookiePresent(request);
        }
        return Optional.empty();
    }

    private boolean authCookieShouldBeAddedAsHeader(HttpServletRequest request) {
        return (request.getCookies() != null && request.getHeader(HttpHeaders.AUTHORIZATION) == null);
    }

    private Optional<MutableServletRequest> setAuthHeaderIfCookiePresent(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
                return Optional.of(getMutableRequestWithAuthHeaderAdded(cookie, request));
            }
        }
        return Optional.empty();
    }

    private MutableServletRequest getMutableRequestWithAuthHeaderAdded(Cookie cookie, HttpServletRequest request) {
        MutableServletRequest mutableServletRequest = new MutableServletRequest(request);
        mutableServletRequest.putHeader(HttpHeaders.AUTHORIZATION, "Bearer " + cookie.getValue());
        return mutableServletRequest;
    }
}
