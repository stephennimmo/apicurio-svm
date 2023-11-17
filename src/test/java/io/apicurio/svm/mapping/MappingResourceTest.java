package io.apicurio.svm.mapping;

import io.apicurio.svm.Role;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MappingResourceTest {

    @Test
    @TestSecurity(user = "user1", roles = {Role.ROLE_SVM_MAPPING_READ})
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
    @TestSecurity(user = "user1", roles = {Role.ROLE_SVM_MAPPING_READ})
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

}