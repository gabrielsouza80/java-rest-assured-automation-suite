package com.gabriel.tests;

// Hamcrest assert entrypoint.
import static org.hamcrest.MatcherAssert.assertThat;
// Hamcrest matcher for exact equality assertions.
import static org.hamcrest.Matchers.equalTo;
// Hamcrest matcher for numeric comparisons (greater than).
import static org.hamcrest.Matchers.greaterThan;
// Hamcrest matcher to ensure value is not null.
import static org.hamcrest.Matchers.notNullValue;

// List collection for arrays returned from API.
import java.util.List;
// Map collection for JSON-like key/value objects.
import java.util.Map;

// JUnit annotation to provide readable test names.
import org.junit.jupiter.api.DisplayName;
// JUnit built-in test method ordering strategy.
import org.junit.jupiter.api.MethodOrderer;
// JUnit annotation to define test execution order.
import org.junit.jupiter.api.Order;
// Marks a method as a test case.
import org.junit.jupiter.api.Test;
// JUnit object with runtime metadata about the current test.
import org.junit.jupiter.api.TestInfo;
// Configures how tests in this class are ordered.
import org.junit.jupiter.api.TestMethodOrder;

// Base class that centralizes common test setup/configuration.
import com.gabriel.base.BaseTest;
// Utility class to load test data from JSON/config files.
import com.gabriel.config.TestData;
// Class that encapsulates HTTP calls related to /posts endpoints.
import com.gabriel.endpoints.PostsEndpoint;
// Factory/helper for building request payload maps for posts.
import com.gabriel.payloads.PostPayload;

// Rest Assured response object used to inspect status/body.
import io.restassured.response.Response;

// Run tests by @Order value instead of random/default order.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Test class dedicated to training scenarios for /posts endpoints.
class PostsTrainingTest extends BaseTest {

    // Endpoint client reused by all tests in this class.
    private final PostsEndpoint postsEndpoint = new PostsEndpoint();

    // Helper method that prints a visual header before each test execution.
    private void printTestHeader(String testName) {
        // Separator line for readability in console logs.
        System.out.println("\n=======================================================");
        // Displays the current test name from @DisplayName.
        System.out.println("TEST START: " + testName);
        // Closing separator line.
        System.out.println("=======================================================");
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes first in this class.
    @Order(1)
    // Human-readable name for reports and console output.
    @DisplayName("Training 01 - GET /posts/1 returns a valid post")
    // Validates GET by id returns an existing post with expected fields.
    void training01GetPostById(TestInfo testInfo) {
        // Prints standardized header using the display name.
        printTestHeader(testInfo.getDisplayName());
        // Sends GET /posts/1.
        Response response = postsEndpoint.getPostById(1);
        // Prints context label in console.
        System.out.println("\nGET /posts/1 endpoint response:");
        // Logs full response body for debugging/learning.
        response.then().log().body();

        // Asserts successful HTTP status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts returned id matches requested id.
        assertThat(response.path("id"), equalTo(1));
        // Asserts title field exists and is not null.
        assertThat(response.path("title"), notNullValue());
        // Asserts body field exists and is not null.
        assertThat(response.path("body"), notNullValue());
        // Asserts userId field exists and is not null.
        assertThat(response.path("userId"), notNullValue());
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes second in this class.
    @Order(2)
    // Human-readable scenario name.
    @DisplayName("Training 02 - GET /posts?userId=1 returns filtered list")
    // Validates filtering posts by userId returns expected list.
    void training02FilterPostsByUserId(TestInfo testInfo) {
        // Prints test header.
        printTestHeader(testInfo.getDisplayName());
        // Sends GET /posts?userId=1.
        Response response = postsEndpoint.getPostsByUserId(1);
        // Converts JSON array body into a list of maps.
        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        // Prints section title for this response summary.
        System.out.println("\nGET /posts?userId=1 summary:");
        // Prints number of filtered posts.
        System.out.println("Total filtered posts: " + posts.size());
        // Prints up to 3 sample items to keep output concise.
        posts.stream().limit(3)
                .forEach(post -> System.out.println("- id=" + post.get("id") + " | title=" + post.get("title")));

        // Asserts successful HTTP status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts the result list is not empty.
        assertThat(response.path("size()"), greaterThan(0));
        // Asserts first item belongs to requested userId.
        assertThat(response.path("[0].userId"), equalTo(1));
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes third in this class.
    @Order(3)
    // Human-readable scenario name.
    @DisplayName("Training 03 - POST /posts creates a new post")
    // Validates creating a post with POST returns expected fields and status.
    void training03CreateNewPost(TestInfo testInfo) {
        // Prints test header.
        printTestHeader(testInfo.getDisplayName());
        // Loads input data for create-post scenario.
        Map<String, Object> data = TestData.getCreatePostData();
        // Builds request payload map with title/body/userId.
        Map<String, Object> payload = PostPayload.create(
                // Converts title to string.
                data.get("title").toString(),
                // Converts body to string.
                data.get("body").toString(),
                // Parses userId as integer.
                Integer.parseInt(data.get("userId").toString()));

        // Sends POST /posts with payload.
        Response response = postsEndpoint.createPost(payload);
        // Prints context label.
        System.out.println("\nPOST /posts endpoint response:");
        // Logs complete response body.
        response.then().log().body();

        // Asserts resource was created.
        assertThat(response.statusCode(), equalTo(201));
        // Asserts response title matches request payload title.
        assertThat(response.path("title"), equalTo(payload.get("title")));
        // Asserts response body matches request payload body.
        assertThat(response.path("body"), equalTo(payload.get("body")));
        // Asserts response userId matches request payload userId.
        assertThat(response.path("userId"), equalTo(payload.get("userId")));
        // Asserts API generated an id.
        assertThat(response.path("id"), notNullValue());
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes fourth in this class.
    @Order(4)
    // Human-readable scenario name.
    @DisplayName("Training 04 - PUT /posts/1 updates full post")
    // Validates full update using PUT on an existing post.
    void training04UpdatePostWithPut(TestInfo testInfo) {
        // Prints test header.
        printTestHeader(testInfo.getDisplayName());
        // Loads input data for update-post scenario.
        Map<String, Object> data = TestData.getUpdatePostData();
        // Reads target post id from test data.
        int postId = Integer.parseInt(data.get("id").toString());

        // Builds full update payload (id + title + body + userId).
        Map<String, Object> payload = PostPayload.update(
                // Target id to update.
                postId,
                // New title.
                data.get("title").toString(),
                // New body.
                data.get("body").toString(),
                // New userId.
                Integer.parseInt(data.get("userId").toString()));

        // Sends PUT /posts/{id} with full payload.
        Response response = postsEndpoint.updatePost(postId, payload);
        // Prints context label.
        System.out.println("\nPUT /posts/1 endpoint response:");
        // Logs complete response body.
        response.then().log().body();

        // Asserts successful HTTP status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts id in response is the updated one.
        assertThat(response.path("id"), equalTo(postId));
        // Asserts title was updated correctly.
        assertThat(response.path("title"), equalTo(payload.get("title")));
        // Asserts body was updated correctly.
        assertThat(response.path("body"), equalTo(payload.get("body")));
        // Asserts userId was updated correctly.
        assertThat(response.path("userId"), equalTo(payload.get("userId")));
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes fifth in this class.
    @Order(5)
    // Human-readable scenario name.
    @DisplayName("Training 05 - PATCH /posts/1 updates post partially")
    // Validates partial update using PATCH on an existing post.
    void training05UpdatePostWithPatch(TestInfo testInfo) {
        // Prints test header.
        printTestHeader(testInfo.getDisplayName());
        // Builds a new title in two concatenated parts (for demo/training).
        String newTitle = "foo"
                + " patch";

        // Builds PATCH payload with only the title field.
        Map<String, Object> payload = PostPayload.patch(newTitle);

        // Sends PATCH /posts/1 with partial payload.
        Response response = postsEndpoint.patchPost(1, payload);
        // Prints context label.
        System.out.println("\nPATCH /posts/1 endpoint response:");
        // Logs complete response body.
        response.then().log().body();

        // Asserts successful HTTP status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts updated resource id is still 1.
        assertThat(response.path("id"), equalTo(1));
        // Asserts title was changed to the new value.
        assertThat(response.path("title"), equalTo(newTitle));
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes sixth in this class.
    @Order(6)
    // Human-readable scenario name.
    @DisplayName("Training 06 - DELETE /posts/1 removes post")
    // Validates delete operation returns successful status.
    void training06RemovePostWithDelete(TestInfo testInfo) {
        // Prints test header.
        printTestHeader(testInfo.getDisplayName());
        // Sends DELETE /posts/1.
        Response response = postsEndpoint.deletePost(1);
        // Prints summary label.
        System.out.println("\nDELETE /posts/1 summary:");
        // Prints HTTP status code for quick visibility.
        System.out.println("Status HTTP: " + response.statusCode());

        // Asserts successful HTTP status.
        assertThat(response.statusCode(), equalTo(200));
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes seventh in this class.
    @Order(7)
    // Human-readable scenario name.
    @DisplayName("Training 07 - GET /posts returns post list")
    // Validates listing all posts returns a non-empty collection.
    void training07ListAllPosts(TestInfo testInfo) {
        // Prints test header.
        printTestHeader(testInfo.getDisplayName());
        // Sends GET /posts.
        Response response = postsEndpoint.getPosts();
        // Parses response JSON array as list of maps.
        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        // Prints summary title.
        System.out.println("\nGET /posts summary:");
        // Prints total count.
        System.out.println("Total posts: " + posts.size());
        // Prints first 5 items to avoid flooding logs.
        posts.stream().limit(5).forEach(post -> System.out.println(
                "- id=" + post.get("id") + " | userId=" + post.get("userId") + " | title=" + post.get("title")));

        // Asserts successful HTTP status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts the list is not empty.
        assertThat(response.path("size()"), greaterThan(0));
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes eighth in this class.
    @Order(8)
    // Human-readable scenario name.
    @DisplayName("Training 08 - GET /posts/1/comments lists post comments")
    // Validates listing comments for a specific post.
    void training08ListPostComments(TestInfo testInfo) {
        // Prints test header.
        printTestHeader(testInfo.getDisplayName());
        // Sends GET /posts/1/comments.
        Response response = postsEndpoint.getCommentsByPostId(1);
        // Parses JSON array response as list of maps.
        List<Map<String, Object>> comments = response.jsonPath().getList("$");
        // Prints summary title.
        System.out.println("\nGET /posts/1/comments summary:");
        // Prints number of returned comments.
        System.out.println("Total comments: " + comments.size());
        // Prints first 3 comments for quick inspection.
        comments.stream().limit(3).forEach(
                comment -> System.out.println("- id=" + comment.get("id") + " | email=" + comment.get("email")));

        // Asserts successful HTTP status.
        assertThat(response.statusCode(), equalTo(200));
        // Asserts comment list is not empty.
        assertThat(response.path("size()"), greaterThan(0));
        // Asserts first comment belongs to post 1.
        assertThat(response.path("[0].postId"), equalTo(1));
    }

    // Marks this method as a JUnit test.
    @Test
    // Executes ninth in this class.
    @Order(9)
    // Human-readable scenario name.
    @DisplayName("Training 09 - GET /posts/99999 returns 404")
    // Validates API behavior when requesting a non-existing post.
    void training09GetNonExistingPost(TestInfo testInfo) {
        // Prints test header.
        printTestHeader(testInfo.getDisplayName());
        // Sends GET /posts/99999 (expected not found).
        Response response = postsEndpoint.getPostById(99999);
        // Captures raw response body as string.
        String body = response.getBody().asString();
        // Prints summary title.
        System.out.println("\nGET /posts/99999 summary:");
        // Prints HTTP status code.
        System.out.println("Status HTTP: " + response.statusCode());
        // Prints body or <empty> when null/blank.
        System.out.println("Returned body: " + (body == null || body.isBlank() ? "<empty>" : body));

        // Asserts endpoint returns 404 for non-existing resource.
        assertThat(response.statusCode(), equalTo(404));
    }
}