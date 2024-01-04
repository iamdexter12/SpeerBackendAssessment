package com.speer.sharenotex.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info()
                        .title("ShareNotex API")
                        .description("This API documentation provides information about the ShareNotex API, which is responsible for managing and sharing notes.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Md Sharif")
                                .email("mohdsharif559@gmail.com")
                                .url("https://www.linkedin.com/in/md-sharif-5019ba117/")
                        )
                );
    }
}
