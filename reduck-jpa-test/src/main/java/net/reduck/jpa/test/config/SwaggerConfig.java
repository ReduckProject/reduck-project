package net.reduck.jpa.test.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author Reduck
 * @since 2022/11/15 17:49
 */

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger", prefix = "enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "token";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    @Value("${swagger.enable:false}")
    private boolean enable;

    public SwaggerConfig() {
    }

    @Bean
    public Docket createRestApi() {
        new ParameterBuilder();
        return (new Docket(DocumentationType.SWAGGER_2)).enable(this.enable).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).apis(RequestHandlerSelectors.basePackage("com.secsmart")).paths(PathSelectors.any()).build().securityContexts(Lists.newArrayList(new SecurityContext[]{this.securityContext()})).securitySchemes(Lists.newArrayList(new ApiKey[]{this.apiKey()}));
    }

    private ApiKey apiKey() {
        return new ApiKey("token", "token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Lists.newArrayList(new SecurityReference[]{new SecurityReference("token", authorizationScopes)});
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title("公共组件").contact(new Contact("secsmart", "https://www.baidu.com", "secsmart@qq.com")).description("公共组件api文档").version("1.0.1").termsOfServiceUrl("http://192.168.25.199:8888/common/index.html#/login").build();
    }

}