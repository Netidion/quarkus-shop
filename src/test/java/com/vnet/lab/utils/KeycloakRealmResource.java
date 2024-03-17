package com.vnet.lab.utils;

import com.vnet.lab.security.TokenService;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neti on 3/17/2024
 */
public class KeycloakRealmResource implements QuarkusTestResourceLifecycleManager {

    @ClassRule
    public static DockerComposeContainer KEYCLOAK = new DockerComposeContainer(
            new File("src/main/docker/keycloak-test.yaml"))
            .withExposedService("keycloak_1", 9080,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30))
            );

    @Override
    public Map<String, String> start() {

        KEYCLOAK.start();
        String jwtIssuerUrl = String.format(
                "http://%s:%s/realms/quarkus-realm",
                KEYCLOAK.getServiceHost("keycloak_1", 9080),
                KEYCLOAK.getServicePort("keycloak_1", 9080)
                );
        TokenService tokenService = new TokenService();
        Map<String, String> config = new HashMap<>();

        try {
            String adminAccessToken = tokenService.getAccessToken("neti", "test", "my-secret");

            String testAccessToken = tokenService.getAccessToken("test", "test", "my-secret");

            config.put("quarkus-admin-access-token", adminAccessToken);
            config.put("quarkus-test-access-token", testAccessToken);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        config.put("mp.jwt.verify.publickey.location", jwtIssuerUrl + "/protocol/openid-connect/certs");
        config.put("mp.jwt.verify.issuer", jwtIssuerUrl);

        return config;
    }

    @Override
    public void stop() {
        KEYCLOAK.stop();
    }
}
