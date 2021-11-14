package com.gateway.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig extends ResourceServerConfigurerAdapter {

    @Configuration
    @Order(1)
    public static class PublicSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/public")
                    .permitAll();

        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/auth-service/auth/register/**")
                    .antMatchers("/auth-service/oauth/token/**");
        }
    }

    @Configuration
    @Order(2)
    public static class ResourceServerSecurityConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
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
                            "/error")
                    .permitAll()
                    .antMatchers("/auth-service/oauth/token").permitAll()
                    .antMatchers("/auth-service/oauth/token/**").permitAll()
                    .antMatchers("/auth-service/oauth/token").permitAll()
                    .antMatchers("/auth-service/oauth/token/**").permitAll()
                    .antMatchers("/oauth/token").permitAll()
                    .antMatchers("/oauth/token/**").permitAll()
                    .antMatchers("/**")
                    .authenticated()
                    .and()
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        }
    }

}
