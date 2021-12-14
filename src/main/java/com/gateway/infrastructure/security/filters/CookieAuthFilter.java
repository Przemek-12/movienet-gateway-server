package com.gateway.infrastructure.security.filters;

public class CookieAuthFilter
//extends BasicAuthenticationFilter 
{

//    private final String checkTokenUrl;
//
//    public CookieAuthFilter(AuthenticationManager authenticationManager, String checkTokenUrl) {
//        super(authenticationManager);
//        this.checkTokenUrl = checkTokenUrl;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//            FilterChain chain)
//            throws IOException, ServletException {
//        Optional<MutableServletRequest> mutableServletRequestOpt = addAuthCookieAsHeader(request);
//        if (mutableServletRequestOpt.isPresent()) {
//            request = mutableServletRequestOpt.get();
//        }
//
//        try {
//            if (getCheckTokenStatus(request.getHeader(HttpHeaders.AUTHORIZATION)) != HttpStatus.OK.value()) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized. Invalid token.");
//            }
//        } catch (Exception e) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized. Invalid token.");
//            return;
//        }
//
//        chain.doFilter(request, response);
//
//    }
//
//    private int getCheckTokenStatus(String token) throws UnirestException {
//        return Unirest
//                .get(checkTokenUrl)
//                .queryString("token", token)
//                .asString()
//                .getStatus();
//    }
//
//    private Optional<MutableServletRequest> addAuthCookieAsHeader(HttpServletRequest request) {
//        if (authCookieShouldBeAddedAsHeader(request)) {
//            return setAuthHeaderIfCookiePresent(request);
//        }
//        return Optional.empty();
//    }
//
//    private boolean authCookieShouldBeAddedAsHeader(HttpServletRequest request) {
//        return (request.getCookies() != null && request.getHeader(HttpHeaders.AUTHORIZATION) == null);
//    }
//
//    private Optional<MutableServletRequest> setAuthHeaderIfCookiePresent(HttpServletRequest request) {
//        for (Cookie cookie : request.getCookies()) {
//            if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
//                return Optional.of(getMutableRequestWithAuthHeaderAdded(cookie, request));
//            }
//        }
//        return Optional.empty();
//    }
//
//    private MutableServletRequest getMutableRequestWithAuthHeaderAdded(Cookie cookie, HttpServletRequest request) {
//        MutableServletRequest mutableServletRequest = new MutableServletRequest(request);
//        mutableServletRequest.putHeader(HttpHeaders.AUTHORIZATION, "Bearer " + cookie.getValue());
//        return mutableServletRequest;
//    }
}
