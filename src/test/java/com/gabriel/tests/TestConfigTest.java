package com.gabriel.tests;

// Hamcrest assertion entrypoint.
import static org.hamcrest.MatcherAssert.assertThat;
// Hamcrest matcher for substring checks.
import static org.hamcrest.Matchers.containsString;
// Hamcrest matcher for exact equality.
import static org.hamcrest.Matchers.equalTo;
// Hamcrest matcher for non-null checks.
import static org.hamcrest.Matchers.notNullValue;

// JUnit readable name annotation.
import org.junit.jupiter.api.DisplayName;
// JUnit test marker.
import org.junit.jupiter.api.Test;

// Accessor for test configuration properties.
import com.gabriel.config.TestConfig;

// Unit tests for values loaded by TestConfig.
class TestConfigTest {

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("Content type configuration should be application/json")
    // Verifies content type is loaded as application/json.
    void shouldLoadContentTypeCorrectly() {
        // Asserts configured content type is exactly application/json.
        assertThat(TestConfig.getContentType(), equalTo("application/json"));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("Practice base URL should point to JSONPlaceholder")
    // Verifies practice base URL exists and points to JSONPlaceholder.
    void shouldLoadPracticeBaseUrlCorrectly() {
        // Ensures base URL is available.
        assertThat(TestConfig.getPracticeBaseUrl(), notNullValue());
        // Ensures base URL contains expected host.
        assertThat(TestConfig.getPracticeBaseUrl(), containsString("jsonplaceholder.typicode.com"));
    }
}
