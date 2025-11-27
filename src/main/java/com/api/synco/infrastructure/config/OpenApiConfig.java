package com.api.synco.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Synco API")
                        .version("1.0.0")
                        .description("API REST para gestão acadêmica com autenticação JWT. " +
                                "Esta API permite criar, listar, atualizar e deletar usuários e cursos, " +
                                "além de realizar autenticação e registro de novos usuários.")
                        .contact(new Contact()
                                .name("Luca5Eckert")
                                .url("https://github.com/Luca5Eckert/SyncoApi"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Insira o token JWT obtido no endpoint /api/auth/login")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }
}
