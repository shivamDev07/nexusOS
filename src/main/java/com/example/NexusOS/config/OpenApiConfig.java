package com.example.NexusOS.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI nexusOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("NexusOS API")
                        .description("Enterprise Backend Platform")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Shivam")
                                .email("your-email@example.com"))
                        .license(new License()
                                .name("MIT License")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));
    }
}
