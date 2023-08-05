package com.keyrus.of;

import com.keyrus.of.domain.dto.RequestAsset;
import com.keyrus.of.domain.enums.Module;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssetResourceTest {


    private final Logger log = LoggerFactory.getLogger(AssetResourceTest.class);

    private static String FILE_NAME;

    private static final String TENANT_ID = "tn";

    private static final Map<String, Object> HEADERS = Map.of(
            "x-userName", "alice",
            "x-tenantId", TENANT_ID);


    @Test
    @Order(1)
    void shouldUploadAsset() {

        try {
            InputStream image = getClass().getClassLoader().getResourceAsStream("image.png");

            String url = given()
                    .headers(HEADERS)
                    .contentType("multipart/form-data; boundary=q1w2e3r4t5y6u7i8o9")
                    .body(image)
                    .multiPart(new File("src/test/resources/image.png"))
                    .when()
                    .post("/api/asset-command/assets/add-asset/tn/COUNTRY")
                    .then()
                    .statusCode(CREATED.getStatusCode())
                    .extract().body().asString();

            String[] params = url.split("/");
            FILE_NAME = params[6].substring(0,params[6].length()-2);

        }
        catch(Exception e) {
            log.info("Error occurred while uploading image endpoint", e);
        }
    }

    @Test
    @Order(2)
    void shouldCloneAsset() {

        RequestAsset requestAsset = RequestAsset.builder()
                .tenantIdDestination("MyTestDestination")
                .tenantIdSource("MyTest")
                .filesNames(List.of(FILE_NAME))
                .module(Module.COUNTRY)
                .build();

        given()
                .headers(HEADERS)
                .body(requestAsset)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/api/asset-command/assets/clone-asset")
                .then()
                .statusCode(OK.getStatusCode());

    }

}
