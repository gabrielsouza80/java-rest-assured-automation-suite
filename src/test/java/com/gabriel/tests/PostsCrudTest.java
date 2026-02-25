package com.gabriel.tests;

import com.gabriel.base.BaseTest;
import com.gabriel.config.TestData;
import com.gabriel.endpoints.PostsEndpoint;
import com.gabriel.payloads.PostPayload;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

class PostsCrudTest extends BaseTest {

    private static final int EXISTING_POST_ID = 1;
    private static final int NON_EXISTENT_POST_ID = 99999;
    private final PostsEndpoint postsEndpoint = new PostsEndpoint();

    @Test
    @DisplayName("GET /posts deve retornar lista de posts")
    void deveListarPostsComSucesso() {
        Response response = postsEndpoint.getPosts();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("size()"), greaterThan(0));
    }

    @Test
    @DisplayName("GET /posts/{id} deve retornar um post existente")
    void deveBuscarPostPorIdComSucesso() {
        Response response = postsEndpoint.getPostById(EXISTING_POST_ID);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("id"), equalTo(EXISTING_POST_ID));
        assertThat(response.path("title"), notNullValue());
        assertThat(response.path("userId"), notNullValue());
    }

    @Test
    @DisplayName("GET /posts/{id} deve retornar 404 para post inexistente")
    void deveRetornarNotFoundParaPostInexistente() {
        Response response = postsEndpoint.getPostById(NON_EXISTENT_POST_ID);

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    @DisplayName("POST /posts deve criar novo post")
    void deveCriarNovoPost() {
        Map<String, Object> data = TestData.getCreatePostData();
        Map<String, Object> payload = PostPayload.create(
                data.get("title").toString(),
                data.get("body").toString(),
                Integer.parseInt(data.get("userId").toString())
        );

        Response response = postsEndpoint.createPost(payload);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("title"), equalTo(payload.get("title")));
        assertThat(response.path("body"), equalTo(payload.get("body")));
        assertThat(response.path("userId"), equalTo(payload.get("userId")));
        assertThat(response.path("id"), notNullValue());
    }

    @Test
    @DisplayName("PUT /posts/{id} deve atualizar post existente")
    void deveAtualizarPostExistente() {
        Map<String, Object> data = TestData.getUpdatePostData();
        int postId = Integer.parseInt(data.get("id").toString());

        Map<String, Object> payload = PostPayload.update(
                postId,
                data.get("title").toString(),
                data.get("body").toString(),
                Integer.parseInt(data.get("userId").toString())
        );

        Response response = postsEndpoint.updatePost(postId, payload);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("id"), equalTo(postId));
        assertThat(response.path("title"), equalTo(payload.get("title")));
        assertThat(response.path("body"), equalTo(payload.get("body")));
        assertThat(response.path("userId"), equalTo(payload.get("userId")));
    }

    @Test
    @DisplayName("PATCH /posts/{id} deve atualizar parcialmente um post")
    void deveAtualizarParcialmentePost() {
        Map<String, Object> data = TestData.getPatchPostData();
        int postId = Integer.parseInt(data.get("id").toString());
        Map<String, Object> payload = PostPayload.patch(data.get("title").toString());

        Response response = postsEndpoint.patchPost(postId, payload);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("id"), equalTo(postId));
        assertThat(response.path("title"), equalTo(payload.get("title")));
    }

    @Test
    @DisplayName("DELETE /posts/{id} deve remover post existente")
    void deveRemoverPostExistente() {
        Response response = postsEndpoint.deletePost(EXISTING_POST_ID);

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    @DisplayName("GET /posts?userId=1 deve filtrar posts por usuário")
    void deveFiltrarPostsPorUserId() {
        Response response = postsEndpoint.getPostsByUserId(1);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("size()"), greaterThan(0));
        assertThat(response.path("[0].userId"), equalTo(1));
    }

    @Test
    @DisplayName("GET /posts/1/comments deve listar comentários do post")
    void deveListarComentariosPorPost() {
        Response response = postsEndpoint.getCommentsByPostId(EXISTING_POST_ID);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("size()"), greaterThan(0));
        assertThat(response.path("[0].postId"), equalTo(EXISTING_POST_ID));
    }
}