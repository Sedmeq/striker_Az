package org.example.striker_az.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "StrikerAZ API",
                version = "v1",
                description = "Futbol komandaları idarəetmə sistemi",
                contact = @Contact(name = "StrikerAZ Team", email = "sb22022202@gmail.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "JWT token ilə autentifikasiya"
)
public class OpenApiConfig {
}