package com.gabriel.tests;

// Hamcrest assertion entrypoint.
import static org.hamcrest.MatcherAssert.assertThat;
// Hamcrest matcher for equality checks.
import static org.hamcrest.Matchers.equalTo;
// Hamcrest matcher for non-null checks.
import static org.hamcrest.Matchers.notNullValue;

// Map used to represent JSON-like objects.
import java.util.Map;

// JUnit annotation for readable test names.
import org.junit.jupiter.api.DisplayName;
// JUnit annotation that marks test methods.
import org.junit.jupiter.api.Test;

// Utility class that exposes test datasets.
import com.gabriel.config.TestData;

// Unit tests for reading datasets from TestData.
class TestDataTest {

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("Create post data should contain title, body and userId")
    // Verifies create-post dataset contains required fields.
    void shouldLoadCreatePostData() {
        // Loads dataset used for post creation tests.
        Map<String, Object> createPostData = TestData.getCreatePostData();

        // Ensures dataset exists.
        assertThat(createPostData, notNullValue());
        // Ensures title field is present.
        assertThat(createPostData.get("title"), notNullValue());
        // Ensures body field is present.
        assertThat(createPostData.get("body"), notNullValue());
        // Ensures userId field is present.
        assertThat(createPostData.get("userId"), notNullValue());
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("Update data should contain id, title, body and userId")
    // Verifies update dataset contains required fields and expected id.
    void shouldLoadUpdatePostData() {
        // Loads dataset used for full update tests.
        Map<String, Object> updatePostData = TestData.getUpdatePostData();

        // Ensures dataset exists.
        assertThat(updatePostData, notNullValue());
        // Ensures id field is present.
        assertThat(updatePostData.get("id"), notNullValue());
        // Ensures title field is present.
        assertThat(updatePostData.get("title"), notNullValue());
        // Ensures body field is present.
        assertThat(updatePostData.get("body"), notNullValue());
        // Ensures userId field is present.
        assertThat(updatePostData.get("userId"), notNullValue());
        // Ensures id value equals 1 in this fixture.
        assertThat(Integer.parseInt(updatePostData.get("id").toString()), equalTo(1));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("Patch data should contain id and title")
    // Verifies patch dataset contains required fields and expected id.
    void shouldLoadPatchPostData() {
        // Loads dataset used for partial update tests.
        Map<String, Object> patchPostData = TestData.getPatchPostData();

        // Ensures dataset exists.
        assertThat(patchPostData, notNullValue());
        // Ensures id field is present.
        assertThat(patchPostData.get("id"), notNullValue());
        // Ensures title field is present.
        assertThat(patchPostData.get("title"), notNullValue());
        // Ensures id value equals 1 in this fixture.
        assertThat(Integer.parseInt(patchPostData.get("id").toString()), equalTo(1));
    }
}
