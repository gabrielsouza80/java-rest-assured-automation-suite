package com.gabriel.endpoints;

import com.gabriel.config.TestConfig;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UsersEndpoint {

    private RequestSpecification request() {
        RequestSpecification request = given()
                .baseUri(TestConfig.getPracticeBaseUrl());

        String apiKey = TestConfig.getPracticeApiKey();
        if (apiKey != null && !apiKey.isBlank()) {
            request.header("x-api-key", apiKey);
        }
        return request;
    }

    public Response getUsersByPage(int page) {
        return request()
                .queryParam("page", page)
                .when()
                .get("/users");
    }

    public Response getUserById(int userId) {
        return request()
                .when()
                .get("/users/{id}", userId);
    }

    public Response createUser(Map<String, Object> body) {
        return request()
                .body(body)
                .when()
                .post("/users");
    }

    public Response updateUser(int userId, Map<String, Object> body) {
        return request()
                .body(body)
                .when()
                .put("/users/{id}", userId);
    }

    public Response deleteUser(int userId) {
        return request()
                .when()
                .delete("/users/{id}", userId);
    }
}
