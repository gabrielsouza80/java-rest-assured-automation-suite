package com.gabriel.tests;

import com.gabriel.config.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class TestDataTest {

    @Test
    @DisplayName("Dados de criação de usuário devem conter name e job")
    void deveCarregarDadosDeCriacaoDeUsuario() {
        Map<String, Object> createUserData = TestData.getCreateUserData();

        assertThat(createUserData, notNullValue());
        assertThat(createUserData.get("name"), notNullValue());
        assertThat(createUserData.get("job"), notNullValue());
    }

    @Test
    @DisplayName("Dados de atualização devem conter id, name e job")
    void deveCarregarDadosDeAtualizacaoDeUsuario() {
        Map<String, Object> updateUserData = TestData.getUpdateUserData();

        assertThat(updateUserData, notNullValue());
        assertThat(updateUserData.get("id"), notNullValue());
        assertThat(updateUserData.get("name"), notNullValue());
        assertThat(updateUserData.get("job"), notNullValue());
        assertThat(Integer.parseInt(updateUserData.get("id").toString()), equalTo(2));
    }
}
