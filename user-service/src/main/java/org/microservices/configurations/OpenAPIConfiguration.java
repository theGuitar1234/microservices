package org.microservices.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Users Service",
        version = "1.0",
        contact = @Contact(
            name = "Author",
            email = "example@gmail.com",
            url = "https://example.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0"
        ),
        termsOfService = "https://example.com",
        description = "A MicroServices Demo"
    )
)
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI ordersOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            ))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}