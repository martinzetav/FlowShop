package com.microservice.cart.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "FlowShop Cart Microservice API",
                description = """
                    Microservice responsible for managing shopping carts in the FlowShop eCommerce platform.
                    Provides endpoints to create, update, retrieve, and delete carts and their items.

                    This service is designed to persist each user's cart so that its contents remain available 
                    across different sessions and devices. For example, if a user starts a shopping session on 
                    one computer and later logs in from another, their cart will still be intact.
                    """
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Bearer token required to access protected endpoints. Include it in the HTTP header as: 'Authorization: Bearer {token}'",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {}
