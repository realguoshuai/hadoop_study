package com.enjoyor.mtdap.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description
 * Created with guoshuai
 * date 2019/10/9 11:26
 */

@Configuration("swagger2Config")
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
    }

    @Bean
    @RefreshScope
    public ApiInfo apiInfo() {
        Contact contact = new Contact("Enjoyor SmartTraffic", "", "");
        return new ApiInfoBuilder().title("Enjoyor SmartTraffic RESTful API").contact(contact).version("1.0")
                .build();
    }
}
