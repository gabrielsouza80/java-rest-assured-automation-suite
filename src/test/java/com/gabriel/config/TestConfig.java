package com.gabriel.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class TestConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = openConfigStream()) {
            PROPERTIES.load(inputStream);
        } catch (IOException error) {
            throw new IllegalStateException("Falha ao carregar configurações de teste.", error);
        }
    }

    private static InputStream openConfigStream() throws IOException {
        InputStream classpathStream = TestConfig.class.getClassLoader()
                .getResourceAsStream("config/config.properties");
        if (classpathStream != null) {
            return classpathStream;
        }

        Path fallbackPath = Path.of("src", "test", "resources", "config", "config.properties");
        if (Files.exists(fallbackPath)) {
            return Files.newInputStream(fallbackPath);
        }

        throw new IllegalStateException("Arquivo config/config.properties não encontrado.");
    }

    private TestConfig() {
    }

    public static String getContentType() {
        return PROPERTIES.getProperty("api.content.type", "application/json");
    }

    public static String getPracticeBaseUrl() {
        return PROPERTIES.getProperty("api.practice.base.url", "https://jsonplaceholder.typicode.com");
    }
}
