package com.gabriel.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class TestData {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final JsonNode ROOT = loadRoot();

    private TestData() {
    }

    private static JsonNode loadRoot() {
        try (InputStream inputStream = openTestDataStream()) {
            return OBJECT_MAPPER.readTree(inputStream);
        } catch (IOException error) {
            throw new IllegalStateException("Falha ao carregar dados de teste.", error);
        }
    }

    private static InputStream openTestDataStream() throws IOException {
        InputStream classpathStream = TestData.class.getClassLoader().getResourceAsStream("data/tests-data.json");
        if (classpathStream != null) {
            return classpathStream;
        }

        Path fallbackPath = Path.of("src", "test", "resources", "data", "tests-data.json");
        if (Files.exists(fallbackPath)) {
            return Files.newInputStream(fallbackPath);
        }

        throw new IllegalStateException("Arquivo data/tests-data.json não encontrado.");
    }

    public static Map<String, Object> getCreatePostData() {
        return OBJECT_MAPPER.convertValue(ROOT.path("posts").path("create"), new TypeReference<>() {
        });
    }

    public static Map<String, Object> getUpdatePostData() {
        return OBJECT_MAPPER.convertValue(ROOT.path("posts").path("update"), new TypeReference<>() {
        });
    }

    public static Map<String, Object> getPatchPostData() {
        return OBJECT_MAPPER.convertValue(ROOT.path("posts").path("patch"), new TypeReference<>() {
        });
    }
}
