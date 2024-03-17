package com.vnet.lab.health;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import jakarta.inject.Provider;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Created by Neti on 3/17/2024
 */
@Slf4j
@Liveness
@ApplicationScoped
public class KeycloakConnectionHealthCheck implements HealthCheck {

    @ConfigProperty(name = "mp.jwt.verify.publickey.location", defaultValue = "false")
    Provider<String> keycloakUrl;

    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Keycloak connection health check");

        try {
            keycloakConnectionVerification();
            responseBuilder.up();
        } catch (IllegalStateException illegalStateException) {
            responseBuilder.down().withData("Error", illegalStateException.getMessage());
        }
        return responseBuilder.build();
    }

    private void keycloakConnectionVerification() {

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(3000))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(keycloakUrl.get()))
                .build();

        HttpResponse<String> response = null;

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException ioException) {
            log.error("IOException", ioException);
        } catch (InterruptedException interruptedException) {
            log.error("InterruptedException", interruptedException);
            Thread.currentThread().interrupt();
        }

        if (request == null) {
            throw new IllegalStateException("Cannot contact keycloak!");
        }
    }
}
