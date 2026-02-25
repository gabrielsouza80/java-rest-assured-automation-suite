package com.gabriel.payloads;

import java.util.HashMap;
import java.util.Map;

public final class PostPayload {

    private PostPayload() {
    }

    public static Map<String, Object> create(String title, String body, int userId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", title);
        payload.put("body", body);
        payload.put("userId", userId);
        return payload;
    }

    public static Map<String, Object> update(int id, String title, String body, int userId) {
        Map<String, Object> payload = create(title, body, userId);
        payload.put("id", id);
        return payload;
    }

    public static Map<String, Object> patch(String title) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", title);
        return payload;
    }
}