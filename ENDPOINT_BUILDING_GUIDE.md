# Guide: How to Build Endpoints from Scratch with Rest Assured

## 6-Step Checklist

Use this checklist **whenever you read API documentation** and need to turn it into code.

---

### **STEP 1: Read the Documentation**

Doc example:
```
GET /posts/1
Response: { id, title, body, userId }
Status: 200
```

**Questions to ask:**
- ✅ What is the **HTTP verb**? (GET, POST, PUT, PATCH, DELETE)
- ✅ What is the **route/endpoint**? (/posts, /posts/{id}, /posts/1/comments)
- ✅ What is the **path param**? (ex: {id}, {userId})
- ✅ What is the **body/payload**? (POST/PUT/PATCH send data)
- ✅ What is the **expected status**? (200, 201, 404, etc)
- ✅ Which **fields are returned**? (id, title, body, userId)

---

### **STEP 2: Build the Endpoint Method**

**Template:**
```java
public Response methodName(requiredParam) {
    return request()
            .when()
            .VERB("/route", requiredParam);
}
```

**Real examples:**

**Simple GET:**
```java
public Response getPosts() {
    return request()
            .when()
            .get("/posts");
}
```

**GET with path param:**
```java
public Response getPostById(int postId) {
    return request()
            .when()
            .get("/posts/{id}", postId);
}
```

**GET with query param (filter):**
```java
public Response getPostsByUserId(int userId) {
    return request()
            .queryParam("userId", userId)
            .when()
            .get("/posts");
}
```

**POST with body:**
```java
public Response createPost(Map<String, Object> body) {
    return request()
            .body(body)
            .when()
            .post("/posts");
}
```

**PUT with path param + body:**
```java
public Response updatePost(int postId, Map<String, Object> body) {
    return request()
            .body(body)
            .when()
            .put("/posts/{id}", postId);
}
```

**DELETE with path param:**
```java
public Response deletePost(int postId) {
    return request()
            .when()
            .delete("/posts/{id}", postId);
}
```

**GET nested route:**
```java
public Response getCommentsByPostId(int postId) {
    return request()
            .when()
            .get("/posts/{id}/comments", postId);
}
```

---

### **STEP 3: Build the Payload (if needed)**

**For POST/PUT/PATCH, create a method in `PostPayload`:**

```java
public static Map<String, Object> create(String title, String body, int userId) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("title", title);
    payload.put("body", body);
    payload.put("userId", userId);
    return payload;
}
```

---

### **STEP 4: Create the Test**

**Template:**
```java
@Test
@DisplayName("GET /posts/1 returns a valid post")
void testName() {
    Response response = endpoint.getPostById(1);

    assertThat(response.statusCode(), equalTo(200));
    assertThat(response.path("id"), equalTo(1));
    assertThat(response.path("title"), notNullValue());
}
```

**Validation pattern:**
```java
// 1. Validate HTTP status
assertThat(response.statusCode(), equalTo(200));

// 2. Validate 1-2 key fields
assertThat(response.path("id"), equalTo(1));
assertThat(response.path("title"), notNullValue());

// 3. For list responses, validate size + first item
assertThat(response.path("size()"), greaterThan(0));
assertThat(response.path("[0].userId"), equalTo(1));
```

---

### **STEP 5: Run the Test**

```bash
# Specific test class
mvn -Dtest=PostsTrainingTest test

# Or with script
./scripts/run-training.ps1 -TestClass PostsTrainingTest
```

**Expected result:**
```
Tests run: X, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

### **STEP 6: Debug if It Fails**

**Problem: Wrong status**
- Check if the HTTP verb is correct (GET vs POST, etc)
- Check if the route is correct

**Problem: Missing field**
- Run the test and inspect returned JSON
- Use `.response.prettyPrint()` to visualize

**Problem: Compilation failure**
- Check imports (`import static org.hamcrest...`)
- Check if any `.` or `;` is missing

---

## Visual Summary

| Type | Verb | Example | Returns |
|------|------|---------|---------|
| Simple Get | GET | `/posts` | List |
| Get by ID | GET | `/posts/1` | Single item |
| Filtered Get | GET | `/posts?userId=1` | Filtered list |
| Nested Get | GET | `/posts/1/comments` | Comments |
| Create | POST | `/posts` + body | Created item (201) |
| Full update | PUT | `/posts/1` + body | Updated item (200) |
| Partial update | PATCH | `/posts/1` + body | Updated item (200) |
| Remove | DELETE | `/posts/1` | Empty (200) |
| Error | GET | `/posts/99999` | Empty (404) |

---

## Complete Example from Scratch

**You read in docs:**
```
POST /comments
Body: { postId, name, body, email }
Status: 201
Response: { postId, id, name, body, email }
```

**You implement:**

1️⃣ Create endpoint method:
```java
public Response createComment(Map<String, Object> body) {
    return request()
            .body(body)
            .when()
            .post("/comments");
}
```

2️⃣ Create payload:
```java
public static Map<String, Object> createComment(int postId, String name, String body, String email) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("postId", postId);
    payload.put("name", name);
    payload.put("body", body);
    payload.put("email", email);
    return payload;
}
```

3️⃣ Create test:
```java
@Test
@DisplayName("POST /comments creates comment")
void shouldCreateComment() {
    Map<String, Object> payload = PostPayload.createComment(1, "John", "Great!", "john@mail.com");
    Response response = endpoint.createComment(payload);

    assertThat(response.statusCode(), equalTo(201));
    assertThat(response.path("postId"), equalTo(1));
    assertThat(response.path("name"), equalTo("John"));
}
```

4️⃣ Run:
```bash
mvn -Dtest=PostsTrainingTest test
```

✅ **Done!**

---

## Common Rest Assured Shortcuts

```java
// Simple value
response.path("id")

// Inside array (first item)
response.path("[0].title")

// Array size
response.path("size()")

// Null check
response.path("field") != null

// Pretty print (for debugging)
response.prettyPrint()

// Status code
response.statusCode()

// Header
response.header("Content-Type")
```

---

## Next Steps

- ✅ Read this guide? Use it with a new API
- ✅ Built an endpoint? Create the test
- ✅ Test passed? Commit to Git
- ✅ Still confused? Go back to **Step 1**

**Need help? Check the real code:**
- Endpoint: `src/test/java/com/gabriel/endpoints/PostsEndpoint.java`
- Test: `src/test/java/com/gabriel/tests/PostsTrainingTest.java`
- Payload: `src/test/java/com/gabriel/payloads/PostPayload.java`
