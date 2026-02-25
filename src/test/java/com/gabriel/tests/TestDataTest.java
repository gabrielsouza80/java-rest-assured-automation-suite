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
    @DisplayName("Dados de criação de post devem conter title, body e userId")
    void deveCarregarDadosDeCriacaoDePost() {
        Map<String, Object> createPostData = TestData.getCreatePostData();

        assertThat(createPostData, notNullValue());
        assertThat(createPostData.get("title"), notNullValue());
        assertThat(createPostData.get("body"), notNullValue());
        assertThat(createPostData.get("userId"), notNullValue());
    }

    @Test
    @DisplayName("Dados de atualização devem conter id, title, body e userId")
    void deveCarregarDadosDeAtualizacaoDePost() {
        Map<String, Object> updatePostData = TestData.getUpdatePostData();

        assertThat(updatePostData, notNullValue());
        assertThat(updatePostData.get("id"), notNullValue());
        assertThat(updatePostData.get("title"), notNullValue());
        assertThat(updatePostData.get("body"), notNullValue());
        assertThat(updatePostData.get("userId"), notNullValue());
        assertThat(Integer.parseInt(updatePostData.get("id").toString()), equalTo(1));
    }

    @Test
    @DisplayName("Dados de patch devem conter id e title")
    void deveCarregarDadosDePatchDePost() {
        Map<String, Object> patchPostData = TestData.getPatchPostData();

        assertThat(patchPostData, notNullValue());
        assertThat(patchPostData.get("id"), notNullValue());
        assertThat(patchPostData.get("title"), notNullValue());
        assertThat(Integer.parseInt(patchPostData.get("id").toString()), equalTo(1));
    }
}
