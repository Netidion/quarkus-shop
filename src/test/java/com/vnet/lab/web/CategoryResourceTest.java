package com.vnet.lab.web;

import com.vnet.lab.utils.KeycloakRealmResource;
import com.vnet.lab.utils.TestContainerResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Created by Neti on 3/17/2024
 */
@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
@QuarkusTestResource(KeycloakRealmResource.class)
public class CategoryResourceTest {

    static String ADMIN_BEARER_TOKEN;
    static String USER_BEARER_TOKEN;

    @BeforeAll
    static void init() {
        ADMIN_BEARER_TOKEN = System.getProperty("quarkus-admin-access-token");
        USER_BEARER_TOKEN = System.getProperty("quarkus-test-access-token");
    }

    @Test
    void testFindAllWithAdminRole() {
        given().when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + ADMIN_BEARER_TOKEN)
                .get("/carts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
