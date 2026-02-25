package com.gabriel.tests;

import com.gabriel.base.BaseTest;
import com.gabriel.config.TestConfig;
import com.gabriel.config.TestData;
import com.gabriel.endpoints.UsersEndpoint;
import com.gabriel.payloads.UserPayload;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

class UsersCrudTest extends BaseTest {

    private static final int EXISTING_USER_ID = 2;
    private static final int NON_EXISTENT_USER_ID = 23;
    private final UsersEndpoint usersEndpoint = new UsersEndpoint();

    private void requireReqresApiKey() {
        Assumptions.assumeTrue(
                TestConfig.hasPracticeApiKey(),
                "Configure api.practice.key em src/test/resources/config/config.properties para executar testes REQRES"
        );
    }

    private void skipIfUnauthorizedOrForbidden(Response response) {
        Assumptions.assumeTrue(
                response.statusCode() != 401 && response.statusCode() != 403,
                "REQRES exige API key válida. Configure api.practice.key em src/test/resources/config/config.properties"
        );
    }

    @Test
    @DisplayName("GET /users?page=2 deve retornar lista paginada")
    void deveListarUsuariosPorPaginaComSucesso() {
        requireReqresApiKey();
        Response response = usersEndpoint.getUsersByPage(2);
        skipIfUnauthorizedOrForbidden(response);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("page"), equalTo(2));
        assertThat(response.path("data.size()"), greaterThan(0));
    }

    @Test
    @DisplayName("GET /users/{id} deve retornar um usuário existente")
    void deveBuscarUsuarioPorIdComSucesso() {
        requireReqresApiKey();
        Response response = usersEndpoint.getUserById(EXISTING_USER_ID);
        skipIfUnauthorizedOrForbidden(response);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("data.id"), equalTo(EXISTING_USER_ID));
        assertThat(response.path("data.email"), notNullValue());
    }

    @Test
    @DisplayName("GET /users/{id} deve retornar 404 para usuário inexistente")
    void deveRetornarNotFoundParaUsuarioInexistente() {
        requireReqresApiKey();
        Response response = usersEndpoint.getUserById(NON_EXISTENT_USER_ID);
        skipIfUnauthorizedOrForbidden(response);

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    @DisplayName("POST /users deve criar novo usuário")
    void deveCriarNovoUsuarioComSucesso() {
        requireReqresApiKey();
        Map<String, Object> data = TestData.getCreateUserData();
        Map<String, Object> payload = UserPayload.create(
                data.get("name").toString(),
                data.get("job").toString()
        );

        Response response = usersEndpoint.createUser(payload);
        skipIfUnauthorizedOrForbidden(response);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("name"), equalTo(payload.get("name")));
        assertThat(response.path("id"), notNullValue());
        assertThat(response.path("createdAt"), notNullValue());
    }

    @Test
    @DisplayName("PUT /users/{id} deve atualizar usuário existente")
    void deveAtualizarUsuarioExistenteComSucesso() {
        requireReqresApiKey();
        Map<String, Object> data = TestData.getUpdateUserData();
        int userId = Integer.parseInt(data.get("id").toString());

        Map<String, Object> payload = UserPayload.update(
                data.get("name").toString(),
                data.get("job").toString()
        );

        Response response = usersEndpoint.updateUser(userId, payload);
        skipIfUnauthorizedOrForbidden(response);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("name"), equalTo(payload.get("name")));
        assertThat(response.path("job"), equalTo(payload.get("job")));
        assertThat(response.path("updatedAt"), notNullValue());
    }

    @Test
    @DisplayName("DELETE /users/{id} deve remover usuário existente")
    void deveRemoverUsuarioExistenteComSucesso() {
        requireReqresApiKey();
        Response response = usersEndpoint.deleteUser(EXISTING_USER_ID);
        skipIfUnauthorizedOrForbidden(response);

        assertThat(response.statusCode(), equalTo(204));
    }
}
