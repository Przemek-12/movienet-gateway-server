package com.gateway.infrastructure.security;

//@Configuration
//@EnableWebSecurity
public class GatewaySecurityConfig {

//    @Configuration
//    @Order(1)
//    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        @Value("${zuul.routes.auth-service.url}")
//        private String authServiceUrl;
//
//        private final String CHECK_TOKEN_ENDPOINT = "oauth/check_token";
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http
//                    .antMatcher("/**")
//                    .cors()
//                    .and()
//                    .csrf().disable()
//                    .httpBasic()
//                    .disable()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .addFilterBefore(
//                            new CookieAuthFilter(super.authenticationManagerBean(),
//                                    authServiceUrl + CHECK_TOKEN_ENDPOINT),
//                            CookieAuthFilter.class)
//                    .authorizeRequests()
//                    .anyRequest()
//                    .permitAll()
//                    .and()
//                    .exceptionHandling()
//                    // allow the framework to send some sort of "to access this resource you must
////                    authenticate first" notification from application server to web client. 
//                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//        }
//
//        @Override
//        public void configure(WebSecurity web) throws Exception {
//            web.ignoring()
//                    .antMatchers(
//                            "/v2/api-docs",
//                            "/swagger-resources",
//                            "/swagger-resources/**",
//                            "/configuration/ui",
//                            "/configuration/security",
//                            "/swagger-ui.html",
//                            "/webjars/**",
//                            "/swagger-ui/**",
//                            "/video-service/v2/api-docs",
//                            "/video-stream-service/v2/api-docs",
//                            "/user-service/v2/api-docs",
//                            "/auth-service/v2/api-docs",
//                            "/error",
//                            "/auth-service/auth/register",
//                            "/auth-service/oauth/token");
//        }
//
//    }

}
