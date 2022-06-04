package com.rainy.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;

/**
 *
 * Created by renguangli at 2022/01/11 6:54 下午
 * @author renguangli
 * @since JDK1.8
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rainy"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(defaultApi2().getGroupName());
            swaggerResource.setLocation("/v2/api-docs");

            SwaggerResource swaggerResource2 = new SwaggerResource();
            swaggerResource2.setName("dataway");
            swaggerResource2.setLocation("/interface-ui/api/docs/swagger2.json");
            return Arrays.asList(swaggerResource, swaggerResource2);
        };
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("renguangli", "https://guangli.ren", "renguangli@bonc.com.cn");
        return new ApiInfoBuilder()
                .title(appName)
                .description(appName)
                .termsOfServiceUrl("https://guangli.ren")
                .contact(contact)
                .version("1.0")
                .build();
    }

}
