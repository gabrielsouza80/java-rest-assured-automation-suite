package com.gabriel.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class TestBase {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://httpbin.org";
    }
}
