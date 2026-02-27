package com.gabriel.tests;

// Hamcrest assertion entrypoint.
import static org.hamcrest.MatcherAssert.assertThat;
// Hamcrest equality matcher.
import static org.hamcrest.Matchers.equalTo;
// Hamcrest matcher for numeric comparison.
import static org.hamcrest.Matchers.greaterThan;
// Hamcrest matcher for non-null checks.
import static org.hamcrest.Matchers.notNullValue;

// Map representation of JSON objects.
import java.util.Map;

// JUnit annotation for readable test descriptions.
import org.junit.jupiter.api.DisplayName;
// JUnit test marker annotation.
import org.junit.jupiter.api.Test;

// Base class with shared test configuration.
import com.gabriel.base.BaseTest;
// Utility to load test data from configured sources.
import com.gabriel.config.TestData;
// HTTP client wrapper for /posts endpoints.
import com.gabriel.endpoints.PostsEndpoint;
// Payload factory for create/update/patch operations.
import com.gabriel.payloads.PostPayload;

// Rest Assured response model.
import io.restassured.response.Response;

// End-to-end CRUD tests for /posts endpoints.
class PostsCrudTest extends BaseTest {

    // Known existing post id used in positive scenarios.
    private static final int EXISTING_POST_ID = 1;
    // Id expected to be missing in the API.
    private static final int NON_EXISTENT_POST_ID = 99999;
    // Endpoint object reused across all tests.
    private final PostsEndpoint postsEndpoint = new PostsEndpoint();

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("GET /posts should return a list of posts")
    // Verifies listing posts returns HTTP 200 and non-empty collection.
    void shouldListPostsSuccessfully() {
        // Sends GET /posts.
        Response response = postsEndpoint.getPosts();

        // Asserts successful status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts array size is greater than zero.
        assertThat(response.path("size()"), greaterThan(0));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("GET /posts/{id} should return an existing post")
    // Verifies fetching an existing post returns expected fields.
    void shouldGetPostByIdSuccessfully() {
        // Sends GET /posts/{id} for known existing id.
        Response response = postsEndpoint.getPostById(EXISTING_POST_ID);

        // Asserts successful status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts returned id matches requested id.
        assertThat(response.path("id"), equalTo(EXISTING_POST_ID));
        // Asserts title exists.
        assertThat(response.path("title"), notNullValue());
        // Asserts userId exists.
        assertThat(response.path("userId"), notNullValue());
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("GET /posts/{id} should return 404 for a non-existing post")
    // Verifies API returns 404 for non-existing resource.
    void shouldReturnNotFoundForNonExistingPost() {
        // Sends GET /posts/{id} for expected missing id.
        Response response = postsEndpoint.getPostById(NON_EXISTENT_POST_ID);

        // Asserts not-found status.
        assertThat(response.statusCode(), equalTo(404));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("POST /posts should create a new post")
    // Verifies creating a post returns 201 and echoes payload values.
    void shouldCreateNewPost() {
        // Loads test data for create scenario.
        Map<String, Object> data = TestData.getCreatePostData();
        // Builds create payload from loaded data.
        Map<String, Object> payload = PostPayload.create(
                // Title field.
                data.get("title").toString(),
                // Body field.
                data.get("body").toString(),
                // userId field converted to integer.
                Integer.parseInt(data.get("userId").toString()));

        // Sends POST /posts.
        Response response = postsEndpoint.createPost(payload);

        // Asserts resource created status.
        assertThat(response.statusCode(), equalTo(201));
        // Asserts title is returned as sent.
        assertThat(response.path("title"), equalTo(payload.get("title")));
        // Asserts body is returned as sent.
        assertThat(response.path("body"), equalTo(payload.get("body")));
        // Asserts userId is returned as sent.
        assertThat(response.path("userId"), equalTo(payload.get("userId")));
        // Asserts generated id exists.
        assertThat(response.path("id"), notNullValue());
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("PUT /posts/{id} should update an existing post")
    // Verifies full update with PUT returns expected values.
    void shouldUpdateExistingPost() {
        // Loads test data for update scenario.
        Map<String, Object> data = TestData.getUpdatePostData();
        // Extracts target id.
        int postId = Integer.parseInt(data.get("id").toString());

        // Builds full update payload.
        Map<String, Object> payload = PostPayload.update(
                // Id to update.
                postId,
                // New title.
                data.get("title").toString(),
                // New body.
                data.get("body").toString(),
                // New userId.
                Integer.parseInt(data.get("userId").toString()));

        // Sends PUT /posts/{id}.
        Response response = postsEndpoint.updatePost(postId, payload);

        // Asserts successful status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts id matches updated record.
        assertThat(response.path("id"), equalTo(postId));
        // Asserts title updated correctly.
        assertThat(response.path("title"), equalTo(payload.get("title")));
        // Asserts body updated correctly.
        assertThat(response.path("body"), equalTo(payload.get("body")));
        // Asserts userId updated correctly.
        assertThat(response.path("userId"), equalTo(payload.get("userId")));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("PATCH /posts/{id} should partially update a post")
    // Verifies partial update with PATCH changes only provided fields.
    void shouldPartiallyUpdatePost() {
        // Loads test data for patch scenario.
        Map<String, Object> data = TestData.getPatchPostData();
        // Extracts target id.
        int postId = Integer.parseInt(data.get("id").toString());
        // Builds patch payload containing only title.
        Map<String, Object> payload = PostPayload.patch(data.get("title").toString());

        // Sends PATCH /posts/{id}.
        Response response = postsEndpoint.patchPost(postId, payload);

        // Asserts successful status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts id remains the target one.
        assertThat(response.path("id"), equalTo(postId));
        // Asserts title is updated.
        assertThat(response.path("title"), equalTo(payload.get("title")));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("DELETE /posts/{id} should remove an existing post")
    // Verifies delete operation returns success status.
    void shouldDeleteExistingPost() {
        // Sends DELETE /posts/{id}.
        Response response = postsEndpoint.deletePost(EXISTING_POST_ID);

        // Asserts successful status.
        assertThat(response.statusCode(), equalTo(200));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("GET /posts?userId=1 should filter posts by user")
    // Verifies userId filter returns only related posts.
    void shouldFilterPostsByUserId() {
        // Sends GET /posts?userId=1.
        Response response = postsEndpoint.getPostsByUserId(1);

        // Asserts successful status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts filtered list is not empty.
        assertThat(response.path("size()"), greaterThan(0));
        // Asserts first element belongs to user 1.
        assertThat(response.path("[0].userId"), equalTo(1));
    }

    // Marks this method as a test.
    @Test
    // Human-readable test name.
    @DisplayName("GET /posts/1/comments should list comments for a post")
    // Verifies comments endpoint returns comments linked to the post.
    void shouldListCommentsByPost() {
        // Sends GET /posts/1/comments.
        Response response = postsEndpoint.getCommentsByPostId(EXISTING_POST_ID);

        // Asserts successful status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts comments list is not empty.
        assertThat(response.path("size()"), greaterThan(0));
        // Asserts first comment is associated with post id 1.
        assertThat(response.path("[0].postId"), equalTo(EXISTING_POST_ID));
    }
}