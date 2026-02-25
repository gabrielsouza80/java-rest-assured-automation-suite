package com.gabriel.base;

import com.gabriel.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    @BeforeAll
    static void globalSetup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(TestConfig.getContentType())
                .build();
    }
}
