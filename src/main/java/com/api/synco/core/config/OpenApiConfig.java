package com.api.synco.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI/Swagger documentation.
 *
 * <p>This class configures the OpenAPI specification for the Synco API, including
 * API metadata, contact information, license details, and JWT authentication scheme.</p>
 *
 * <p>The generated documentation is accessible via Swagger UI at {@code /swagger-ui.html}
 * and the OpenAPI specification at {@code /v3/api-docs}.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see io.swagger.v3.oas.models.OpenAPI
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates and configures the OpenAPI specification bean.
     *
     * <p>This method configures:</p>
     * <ul>
     *   <li>API title, version, and description</li>
     *   <li>Contact information for API support</li>
     *   <li>MIT License details</li>
     *   <li>JWT Bearer authentication scheme</li>
     * </ul>
     *
     * @return a configured {@link OpenAPI} instance for Swagger documentation
     */
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
