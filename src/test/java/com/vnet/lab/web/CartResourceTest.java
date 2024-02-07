package com.vnet.lab.web;

import com.vnet.lab.utils.TestContainerResource;
import com.vnet.lab.utils.enums.CartStatus;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.vertx.core.http.HttpHeaders;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.*;


@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class CartResourceTest {

    private static final String INSERT_WRONG_CART_IN_DB =
            "insert into carts values (999, current_timestamp, current_timestamp, 3, 'NEW')";

    private static final String DELETE_WRONG_CART_FROM_DB =
            "delete from carts where id = 999";

    @Inject
    DataSource dataSource;

    @Test
    public void testFindAll(){
        get("/carts").then()
                .statusCode(OK)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testFindAllActiveCarts(){
        get("/carts/active").then()
                .statusCode(OK)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetActiveCartForCustomer(){
        get("/carts/customer/3").then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("size()", is(3))
                .body(containsString("firstName"))
                .body(containsString("John"));
    }

    @Test
    public void testFindById(){
        get("/carts/5").then()
                .contentType(ContentType.JSON)
                .statusCode(OK)
                .body("size()", is(3))
                .body(containsString("status"))
                .body(containsString("CANCELLED"));

        get("/carts/20").then()
                .statusCode(NO_CONTENT);
    }

    @Test
    public void testDelete(){
        get("/carts/3").then()
                .statusCode(OK)
                .body(containsString("John"))
                .body(containsString("NEW"));

        delete("/carts/3").then()
                .statusCode(NO_CONTENT);

        get("/carts/3").then()
                .statusCode(OK)
                .body(containsString("John"))
                .body(containsString("CANCELLED"));
    }

    private void executeSql(String query) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Error has occurred while trying to execute SQL Query: " + e.getMessage());
        }
    }

    @Test
    public void testGetActiveCartFromCustomerWhenThereAre2CartsInDB() {
        executeSql(INSERT_WRONG_CART_IN_DB);

        get("/carts/customer/3").then()
                .statusCode(INTERNAL_SERVER_ERROR)
                .body(containsString("Error"))
                .body(containsString("Many active carts detected!!!"));

        executeSql(DELETE_WRONG_CART_FROM_DB);
    }

    @Test
    public void testCreateCart(){
        var requestParams = Map.of("firstName", "Sam",
                "lastName", "King",
                "email","sam.king@test.com");

        var newCustomerId = given()
                .header(String.valueOf(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON)
                .body(requestParams).post("/customers")
                .then()
                .statusCode(OK)
                .extract()
                .jsonPath()
                .getInt("id");

        var response = post("/carts/customer/" + newCustomerId).then()
                .statusCode(OK)
                .extract()
                .jsonPath()
                .getMap("$");

        assertThat(response.get("id")).isNotNull();
        assertThat(response).containsEntry("status", CartStatus.NEW.name());

        delete("/carts/" + response.get("id")).then()
                .statusCode(NO_CONTENT);
        delete("/customer/" + newCustomerId).then()
                .statusCode(NOT_FOUND);
    }

    @Test
    public void testFailToCreateACartIfAlreadyExists() {

        var request = Map.of("firstName", "Sam",
                "lastName", "King",
                "email","sam.king@test.com");

        var newCustomerId = given().header(String.valueOf(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON)
                .body(request).post("/customers").then()
                .statusCode(OK)
                .extract()
                .jsonPath()
                .getLong("id");

        var newCartId = post("/carts/customer/" + newCustomerId).then()
                .statusCode(OK)
                .extract()
                .jsonPath()
                .getLong("id");

        post("/carts/customer/" + newCustomerId).then()
                .statusCode(INTERNAL_SERVER_ERROR)
                .body(containsString("There is already an active cart!"));

        assertThat(newCartId).isNotZero();

        delete("/carts/" + newCartId).then().statusCode(NO_CONTENT);
        delete("/customers/" + newCustomerId).then().statusCode(NO_CONTENT);

    }
}
