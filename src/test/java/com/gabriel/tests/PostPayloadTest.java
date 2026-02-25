package com.gabriel.tests;

import com.gabriel.payloads.PostPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class PostPayloadTest {

    @Test
    @DisplayName("Payload de criação deve montar title, body e userId")
    void deveMontarPayloadDeCriacaoCorretamente() {
        Map<String, Object> payload = PostPayload.create("foo", "bar", 1);

        assertThat(payload, notNullValue());
        assertThat(payload.get("title"), equalTo("foo"));
        assertThat(payload.get("body"), equalTo("bar"));
        assertThat(payload.get("userId"), equalTo(1));
    }

    @Test
    @DisplayName("Payload de atualização deve montar id, title, body e userId")
    void deveMontarPayloadDeAtualizacaoCorretamente() {
        Map<String, Object> payload = PostPayload.update(1, "foo", "bar", 1);

        assertThat(payload, notNullValue());
        assertThat(payload.get("id"), equalTo(1));
        assertThat(payload.get("title"), equalTo("foo"));
        assertThat(payload.get("body"), equalTo("bar"));
        assertThat(payload.get("userId"), equalTo(1));
    }

    @Test
    @DisplayName("Payload de patch deve montar apenas title")
    void deveMontarPayloadDePatchCorretamente() {
        Map<String, Object> payload = PostPayload.patch("foo");

        assertThat(payload, notNullValue());
        assertThat(payload.get("title"), equalTo("foo"));
    }
}