package com.gateway.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import com.gateway.infrastructure.security.filters.CookieAuthFilter;

@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig {

    @Configuration
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .csrf().disable()
                    .httpBasic()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(new CookieAuthFilter(super.authenticationManagerBean()),
                            CookieAuthFilter.class)
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .exceptionHandling()
                    // allow the framework to send some sort of "to access this resource you must
//                    authenticate first" notification from application server to web client. 
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                    .and()
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers(
                            "/v2/api-docs",
                            "/swagger-resources",
                            "/swagger-resources/**",
                            "/configuration/ui",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/swagger-ui/**",
                            "/video-service/v2/api-docs",
                            "/video-stream-service/v2/api-docs",
                            "/user-service/v2/api-docs",
                            "/auth-service/v2/api-docs",
                            "/error",
                            "/auth-service/auth/register",
                            "/auth-service/oauth/token");
        }

    }


}
