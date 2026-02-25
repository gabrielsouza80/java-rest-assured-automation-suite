package com.gabriel.tests;

import com.gabriel.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class TestConfigTest {

    @Test
    @DisplayName("Configuração de content-type deve ser application/json")
    void deveCarregarContentTypeCorretamente() {
        assertThat(TestConfig.getContentType(), equalTo("application/json"));
    }

    @Test
    @DisplayName("Base URL de prática deve apontar para JSONPlaceholder")
    void deveCarregarBaseUrlDePraticaCorretamente() {
        assertThat(TestConfig.getPracticeBaseUrl(), notNullValue());
        assertThat(TestConfig.getPracticeBaseUrl(), containsString("jsonplaceholder.typicode.com"));
    }
}
