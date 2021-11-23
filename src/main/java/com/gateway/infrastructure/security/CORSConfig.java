package com.gateway.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig {

    @Configuration
    public class CrossOriginConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods("DELETE", "GET", "OPTIONS", "POST", "PUT")
                    .allowedOrigins("http://localhost:3000");
        }
    }

}
