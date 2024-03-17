package com.vnet.lab.utils;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;

import java.util.Map;

/**
 * Created by Neti on 3/17/2024
 */
public class KeycloakRealmResource implements QuarkusTestResourceLifecycleManager {

    @ClassRule
    public static DockerComposeContainer KEYCLOAK = new DockerComposeContainer();




    @Override
    public Map<String, String> start() {
        return null;
    }

    @Override
    public void stop() {

    }
}
