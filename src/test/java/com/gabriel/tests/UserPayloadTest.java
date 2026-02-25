package com.gabriel.tests;

import com.gabriel.payloads.UserPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class UserPayloadTest {

    @Test
    @DisplayName("Payload de criação deve montar name e job corretamente")
    void deveMontarPayloadDeCriacaoCorretamente() {
        Map<String, Object> payload = UserPayload.create("Gabriel", "QA");

        assertThat(payload, notNullValue());
        assertThat(payload.get("name"), equalTo("Gabriel"));
        assertThat(payload.get("job"), equalTo("QA"));
    }

    @Test
    @DisplayName("Payload de atualização deve montar name e job corretamente")
    void deveMontarPayloadDeAtualizacaoCorretamente() {
        Map<String, Object> payload = UserPayload.update("Gabriel Senior", "Senior QA");

        assertThat(payload, notNullValue());
        assertThat(payload.get("name"), equalTo("Gabriel Senior"));
        assertThat(payload.get("job"), equalTo("Senior QA"));
    }
}
