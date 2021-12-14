package com.gateway.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gateway"))
                .paths(PathSelectors.any())
                .build();
    }

    @Component
    @Primary
    @EnableAutoConfiguration
    public class DocumentationController implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
            List<SwaggerResource> resources = new ArrayList<>();
            resources.add(swaggerResource("video-service", "/video-service/v2/api-docs", "2.0"));
            resources.add(swaggerResource("video-stream-service", "/video-stream-service/v2/api-docs", "2.0"));
            resources.add(swaggerResource("user-service", "/user-service/v2/api-docs", "2.0"));
            resources.add(swaggerResource("auth-service", "/auth-service/v2/api-docs", "2.0"));
            resources.add(swaggerResource("content-service", "/content-service/v2/api-docs", "2.0"));
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }

    }
}
