package com.gabriel.payloads;

import java.util.HashMap;
import java.util.Map;

public final class UserPayload {

    private UserPayload() {
    }

    public static Map<String, Object> create(String name, String job) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("job", job);
        return payload;
    }

    public static Map<String, Object> update(String name, String job) {
        return create(name, job);
    }
}
