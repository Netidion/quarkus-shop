package com.vnet.lab.utils;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class TestContainerResource implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer<?> DATABASE = new PostgreSQLContainer<>("postgres:13");
    @Override
    public Map<String, String> start() {
        DATABASE.start();
        Map<String, String> configMag = new HashMap<>();
        configMag.put("quarkus.datasource.jdbc.url", DATABASE.getJdbcUrl());
        configMag.put("quarkus.datasource.username", DATABASE.getUsername());
        configMag.put("quarkus.datasource.password", DATABASE.getPassword());
        return configMag;
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}
