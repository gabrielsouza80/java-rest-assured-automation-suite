package com.gabriel.endpoints;

import com.gabriel.config.TestConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostsEndpoint {

    private RequestSpecification request() {
        return given()
                .baseUri(TestConfig.getPracticeBaseUrl());
    }

    public Response getPosts() {
        return request()
                .when()
                .get("/posts");
    }

    public Response getPostById(int postId) {
        return request()
                .when()
                .get("/posts/{id}", postId);
    }

    public Response getPostsByUserId(int userId) {
        return request()
                .queryParam("userId", userId)
                .when()
                .get("/posts");
    }

    public Response getCommentsByPostId(int postId) {
        return request()
                .when()
                .get("/posts/{id}/comments", postId);
    }

    public Response createPost(Map<String, Object> body) {
        return request()
                .body(body)
                .when()
                .post("/posts");
    }

    public Response updatePost(int postId, Map<String, Object> body) {
        return request()
                .body(body)
                .when()
                .put("/posts/{id}", postId);
    }

    public Response patchPost(int postId, Map<String, Object> body) {
        return request()
                .body(body)
                .when()
                .patch("/posts/{id}", postId);
    }

    public Response deletePost(int postId) {
        return request()
                .when()
                .delete("/posts/{id}", postId);
    }
}