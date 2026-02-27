package com.gabriel.tests;

// Hamcrest assertion entrypoint.
import static org.hamcrest.MatcherAssert.assertThat;
// Hamcrest matcher for equality checks.
import static org.hamcrest.Matchers.equalTo;
// Hamcrest matcher to assert value is not null.
import static org.hamcrest.Matchers.notNullValue;

// Map used to represent JSON-like payload objects.
import java.util.Map;

// JUnit annotation for readable test names in reports.
import org.junit.jupiter.api.DisplayName;
// JUnit annotation that marks test methods.
import org.junit.jupiter.api.Test;

// Payload builder utility for post-related request bodies.
import com.gabriel.payloads.PostPayload;

// Unit tests for PostPayload factory methods.
class PostPayloadTest {

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("Create payload should build title, body and userId")
    // Verifies create payload contains title/body/userId with expected values.
    void shouldBuildCreatePayloadCorrectly() {
        // Builds payload for create-post operation.
        Map<String, Object> payload = PostPayload.create("foo", "bar", 1);

        // Ensures payload map was created.
        assertThat(payload, notNullValue());
        // Ensures title field is correctly populated.
        assertThat(payload.get("title"), equalTo("foo"));
        // Ensures body field is correctly populated.
        assertThat(payload.get("body"), equalTo("bar"));
        // Ensures userId field is correctly populated.
        assertThat(payload.get("userId"), equalTo(1));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("Update payload should build id, title, body and userId")
    // Verifies update payload contains id/title/body/userId with expected values.
    void shouldBuildUpdatePayloadCorrectly() {
        // Builds payload for full update (PUT-like) operation.
        Map<String, Object> payload = PostPayload.update(1, "foo", "bar", 1);

        // Ensures payload map was created.
        assertThat(payload, notNullValue());
        // Ensures id field is correctly populated.
        assertThat(payload.get("id"), equalTo(1));
        // Ensures title field is correctly populated.
        assertThat(payload.get("title"), equalTo("foo"));
        // Ensures body field is correctly populated.
        assertThat(payload.get("body"), equalTo("bar"));
        // Ensures userId field is correctly populated.
        assertThat(payload.get("userId"), equalTo(1));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("Patch payload should build only title")
    // Verifies patch payload contains only the partial field (title).
    void shouldBuildPatchPayloadCorrectly() {
        // Builds payload for partial update (PATCH-like) operation.
        Map<String, Object> payload = PostPayload.patch("foo");

        // Ensures payload map was created.
        assertThat(payload, notNullValue());
        // Ensures title field is correctly populated.
        assertThat(payload.get("title"), equalTo("foo"));
    }
}