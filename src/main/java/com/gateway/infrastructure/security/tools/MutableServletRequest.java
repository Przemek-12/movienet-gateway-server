package com.gateway.infrastructure.security.tools;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import lombok.NonNull;

public class MutableServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders = new HashMap<>();

    public MutableServletRequest(HttpServletRequest request) {
        super(request);
    }

    public void putHeader(@NonNull String name, @NonNull String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        if (customHeaders.containsKey(name)) {
            return customHeaders.get(name);
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> customHeadersNames = new HashSet<>(customHeaders.keySet());
        Enumeration<String> originalHeaders = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (originalHeaders.hasMoreElements()) {
            customHeadersNames.add(originalHeaders.nextElement());
        }
        return Collections.enumeration(customHeadersNames);
    }
}
