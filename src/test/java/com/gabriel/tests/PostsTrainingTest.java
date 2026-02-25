package com.gabriel.tests;

import com.gabriel.base.BaseTest;
import com.gabriel.config.TestData;
import com.gabriel.endpoints.PostsEndpoint;
import com.gabriel.payloads.PostPayload;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostsTrainingTest extends BaseTest {

    private final PostsEndpoint postsEndpoint = new PostsEndpoint();

    private void printTestHeader(String testName) {
        System.out.println("\n=======================================================");
        System.out.println("INICIO DO TESTE: " + testName);
        System.out.println("=======================================================");
    }

    @Test
    @Order(1)
    @DisplayName("Treino 01 - GET /posts/1 retorna post válido")
    void treino01GetPostPorId(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        Response response = postsEndpoint.getPostById(1);
        System.out.println("\nResposta do endpoint GET /posts/1:");
        response.then().log().body();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("id"), equalTo(1));
        assertThat(response.path("title"), notNullValue());
        assertThat(response.path("body"), notNullValue());
        assertThat(response.path("userId"), notNullValue());
    }

    @Test
    @Order(2)
    @DisplayName("Treino 02 - GET /posts?userId=1 retorna lista filtrada")
    void treino02FiltrarPostsPorUserId(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        Response response = postsEndpoint.getPostsByUserId(1);
        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        System.out.println("\nResumo GET /posts?userId=1:");
        System.out.println("Total de posts filtrados: " + posts.size());
        posts.stream().limit(3).forEach(post ->
            System.out.println("- id=" + post.get("id") + " | title=" + post.get("title"))
        );

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("size()"), greaterThan(0));
        assertThat(response.path("[0].userId"), equalTo(1));
    }

    @Test
    @Order(3)
    @DisplayName("Treino 03 - POST /posts cria novo post")
    void treino03CriarNovoPost(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        Map<String, Object> data = TestData.getCreatePostData();
        Map<String, Object> payload = PostPayload.create(
                data.get("title").toString(),
                data.get("body").toString(),
                Integer.parseInt(data.get("userId").toString())
        );

        Response response = postsEndpoint.createPost(payload);
        System.out.println("\nResposta do endpoint POST /posts:");
        response.then().log().body();

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("title"), equalTo(payload.get("title")));
        assertThat(response.path("body"), equalTo(payload.get("body")));
        assertThat(response.path("userId"), equalTo(payload.get("userId")));
        assertThat(response.path("id"), notNullValue());
    }

    @Test
    @Order(4)
    @DisplayName("Treino 04 - PUT /posts/1 atualiza post completo")
    void treino04AtualizarPostComPut(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        Map<String, Object> data = TestData.getUpdatePostData();
        int postId = Integer.parseInt(data.get("id").toString());

        Map<String, Object> payload = PostPayload.update(
                postId,
                data.get("title").toString(),
                data.get("body").toString(),
                Integer.parseInt(data.get("userId").toString())
        );

        Response response = postsEndpoint.updatePost(postId, payload);
        System.out.println("\nResposta do endpoint PUT /posts/1:");
        response.then().log().body();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("id"), equalTo(postId));
        assertThat(response.path("title"), equalTo(payload.get("title")));
        assertThat(response.path("body"), equalTo(payload.get("body")));
        assertThat(response.path("userId"), equalTo(payload.get("userId")));
    }

    @Test
    @Order(5)
    @DisplayName("Treino 05 - PATCH /posts/1 atualiza post parcial")
    void treino05AtualizarPostComPatch(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        String novoTitulo = "foo"
                + " patch";

        Map<String, Object> payload = PostPayload.patch(novoTitulo);

        Response response = postsEndpoint.patchPost(1, payload);
        System.out.println("\nResposta do endpoint PATCH /posts/1:");
        response.then().log().body();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("id"), equalTo(1));
        assertThat(response.path("title"), equalTo(novoTitulo));
    }

    @Test
    @Order(6)
    @DisplayName("Treino 06 - DELETE /posts/1 remove post")
    void treino06RemoverPostComDelete(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        Response response = postsEndpoint.deletePost(1);
        System.out.println("\nResumo DELETE /posts/1:");
        System.out.println("Status HTTP: " + response.statusCode());

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    @Order(7)
    @DisplayName("Treino 07 - GET /posts retorna lista de posts")
    void treino07ListarTodosPosts(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        Response response = postsEndpoint.getPosts();
        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        System.out.println("\nResumo GET /posts:");
        System.out.println("Total de posts: " + posts.size());
        posts.stream().limit(5).forEach(post ->
            System.out.println("- id=" + post.get("id") + " | userId=" + post.get("userId") + " | title=" + post.get("title"))
        );

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("size()"), greaterThan(0));
    }

    @Test
    @Order(8)
    @DisplayName("Treino 08 - GET /posts/1/comments lista comentários do post")
    void treino08ListarComentariosDoPost(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        Response response = postsEndpoint.getCommentsByPostId(1);
        List<Map<String, Object>> comments = response.jsonPath().getList("$");
        System.out.println("\nResumo GET /posts/1/comments:");
        System.out.println("Total de comentários: " + comments.size());
        comments.stream().limit(3).forEach(comment ->
            System.out.println("- id=" + comment.get("id") + " | email=" + comment.get("email"))
        );

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("size()"), greaterThan(0));
        assertThat(response.path("[0].postId"), equalTo(1));
    }

    @Test
    @Order(9)
    @DisplayName("Treino 09 - GET /posts/99999 retorna 404")
    void treino09BuscarPostInexistente(TestInfo testInfo) {
        printTestHeader(testInfo.getDisplayName());
        Response response = postsEndpoint.getPostById(99999);
        String body = response.getBody().asString();
        System.out.println("\nResumo GET /posts/99999:");
        System.out.println("Status HTTP: " + response.statusCode());
        System.out.println("Body retornado: " + (body == null || body.isBlank() ? "<vazio>" : body));

        assertThat(response.statusCode(), equalTo(404));
    }
}