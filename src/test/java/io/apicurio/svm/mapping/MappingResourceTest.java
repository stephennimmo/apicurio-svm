package io.apicurio.svm.mapping;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MappingResourceTest {

    @Test
    public void get() {
        Mapping[] mappings = given()
                .when()
                .get("/api/mappings")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(Mapping[].class);
        Assertions.assertTrue(mappings.length > 0);
    }

    @Test
    public void getBySourceSystemIdAndTargetSystemId() {
        Mapping[] mappings = given()
                .when()
                .get("/api/mappings?sourceSystemId=1&targetSystemId=2")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(Mapping[].class);
        Assertions.assertTrue(mappings.length > 0);
        Arrays.stream(mappings).forEach(mapping -> {
            Assertions.assertEquals(mapping.sourceSystem.systemId, 1);
            Assertions.assertEquals(mapping.targetSystem.systemId, 2);
        });
    }

    /*
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
        system.systemId = 1234;
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

     */

}