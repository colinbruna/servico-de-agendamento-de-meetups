package com.bootcampjavabrunas.microservicemeetup.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bootcampjavabrunas.microservicemeetup.application.controller") )
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Meetups API")
                .description("RestApi de meetup produzida no bootcamp de Java da Womakerscode")
                .version("1.1")
                .contact(contact())
                .build();
    }

    private Contact contact() {
        return new Contact("Bruna Colin e Silva",
                "https://www.linkedin.com/in/bruna-colin-33955221a/",
                "brunacbaldin@gmail.com");
    }
}