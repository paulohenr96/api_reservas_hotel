package com.paulo.hotel.swagger;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.paulo.hotel.controller")) // Pacote onde estão os controllers da API
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }


    private List<springfox.documentation.spi.service.contexts.SecurityContext> securityContexts() {
        return java.util.Collections.singletonList(
            springfox.documentation.spi.service.contexts.SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build()
        );
    }
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return java.util.Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }
  
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("API de Hotel")
            .description("Documentação da API de Hotel")
            .version("1.0.0")
            .build();
    }
}