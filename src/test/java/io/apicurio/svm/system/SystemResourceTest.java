package io.apicurio.svm.system;

import io.apicurio.svm.ErrorResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SystemResourceTest {

    @Test
    public void get() {
        given()
                .when()
                .get("/api/systems")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void postAndGetById() {
        System system = new System();
        system.name = RandomStringUtils.randomAlphabetic(5);
        System saved = given()
                .when()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/api/systems")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().as(System.class);
        Assertions.assertNotNull(saved.systemId);
        Assertions.assertEquals(saved.name, system.name);
        System got = given()
                .when()
                .get("/api/systems/{systemId}", saved.systemId)
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(System.class);
        Assertions.assertEquals(saved, got);
    }

    @Test
    public void failPostNoName() {
        ErrorResponse errorResponse = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new System())
                .post("/api/systems")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .extract().as(ErrorResponse.class);
        Assertions.assertEquals(errorResponse.errors().get(0).path(), "post.system.name");
    }

    @Test
    public void failPostWithSystemId() {
        System system = new System();
        system.systemId = 1234L;
        system.name = RandomStringUtils.randomAlphabetic(5);
        ErrorResponse errorResponse = given()
                .when()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/api/systems")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .extract().as(ErrorResponse.class);
        Assertions.assertEquals(errorResponse.errors().get(0).path(), "post.system.systemId");
    }

}