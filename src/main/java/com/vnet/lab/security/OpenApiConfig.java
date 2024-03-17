package com.vnet.lab.security;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

/**
 * Created by Neti on 3/17/2024
 */

@SecurityScheme(
        securitySchemeName = "jwt",
        description = "JWT authentication with bearer token",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "Bearer[token]"
)
@OpenAPIDefinition(
        info = @Info(
                title = "QuarkusShop API", description = "Sample application developed with Quarkus",
                version = "1.0.0-SNAPSHOT"),
        security = @SecurityRequirement(name = "JWT")
)
public class OpenApiConfig extends Application {
}
