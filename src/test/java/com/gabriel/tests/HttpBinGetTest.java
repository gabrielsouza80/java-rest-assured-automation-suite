package com.gabriel.tests;

import com.gabriel.base.TestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

class HttpBinGetTest extends TestBase {

    @Test
    void deveRetornar200NoEndpointGet() {
        given()
            .when()
                .get("/get")
            .then()
                .statusCode(200)
                .body("url", containsString("httpbin.org/get"))
                .body("headers.Host", equalTo("httpbin.org"));
    }
}
