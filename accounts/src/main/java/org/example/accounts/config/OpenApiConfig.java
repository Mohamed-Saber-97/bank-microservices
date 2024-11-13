package org.example.accounts.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservice REST API Documentation",
                description = "Banking Accounts microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Mohamed Saber",
                        email = "mohamed.saber.mohamed.dev@gmail.com",
                        url = "https://github.com/Mohamed-Saber-97"
                )

        )
)
public class OpenApiConfig {
}
