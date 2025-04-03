package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	    @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .components(new Components())
	                .info(new Info()
	                        .title("Library Management API")
	                        .description("RESTful API for managing a simple library system with books and authors")
	                        .version("1.0")
	                        .contact(new Contact()
	                                .name("Library API Team")
	                                .email("library@example.com"))
	                        .license(new License()
	                                .name("MIT License")
	                                .url("https://opensource.nagesh")));
	    }
}
