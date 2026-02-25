# Guia: Como Construir Endpoints do Zero com Rest Assured

## Checklist em 6 Passos

Use este checklist **sempre que ler uma documentação de API** e precisar transformar em código.

---

### **PASSO 1: Ler a Documentação**

Exemplo da doc:
```
GET /posts/1
Response: { id, title, body, userId }
Status: 200
```

**Questões a fazer:**
- ✅ Qual é o **verbo HTTP**? (GET, POST, PUT, PATCH, DELETE)
- ✅ Qual é a **rota/endpoint**? (/posts, /posts/{id}, /posts/1/comments)
- ✅ Qual é o **path param**? (ex: {id}, {userId})
- ✅ Qual é o **body/payload**? (POST/PUT/PATCH enviam dados)
- ✅ Qual é o **status esperado**? (200, 201, 404, etc)
- ✅ Quais **campos retorna**? (id, title, body, userId)

---

### **PASSO 2: Montar o Método no Endpoint**

**Template:**
```java
public Response nomeMetodo(paramSeNecessario) {
    return request()
            .when()
            .VERBO("/rota", paramSeNecessario);
}
```

**Exemplos reais:**

**GET simples:**
```java
public Response getPosts() {
    return request()
            .when()
            .get("/posts");
}
```

**GET com path param:**
```java
public Response getPostById(int postId) {
    return request()
            .when()
            .get("/posts/{id}", postId);
}
```

**GET com query param (filtro):**
```java
public Response getPostsByUserId(int userId) {
    return request()
            .queryParam("userId", userId)
            .when()
            .get("/posts");
}
```

**POST com body:**
```java
public Response createPost(Map<String, Object> body) {
    return request()
            .body(body)
            .when()
            .post("/posts");
}
```

**PUT com path param + body:**
```java
public Response updatePost(int postId, Map<String, Object> body) {
    return request()
            .body(body)
            .when()
            .put("/posts/{id}", postId);
}
```

**DELETE com path param:**
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

### **PASSO 3: Montar o Payload (se precisar)**

**Para POST/PUT/PATCH, crie um método no `PostPayload`:**

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

### **PASSO 4: Criar o Teste**

**Template:**
```java
@Test
@DisplayName("GET /posts/1 retorna post válido")
void testeNome() {
    Response response = endpoint.getPostById(1);

    assertThat(response.statusCode(), equalTo(200));
    assertThat(response.path("id"), equalTo(1));
    assertThat(response.path("title"), notNullValue());
}
```

**Padrão de validação:**
```java
// 1. Valida status HTTP
assertThat(response.statusCode(), equalTo(200));

// 2. Valida 1-2 campos chave
assertThat(response.path("id"), equalTo(1));
assertThat(response.path("title"), notNullValue());

// 3. Para lista, valida tamanho + primeiro item
assertThat(response.path("size()"), greaterThan(0));
assertThat(response.path("[0].userId"), equalTo(1));
```

---

### **PASSO 5: Rodar o Teste**

```bash
# Teste específico
mvn -Dtest=PostsTrainingTest test

# Ou com script
./scripts/run-training.ps1 -TestClass PostsTrainingTest
```

**Esperado:**
```
Tests run: X, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

### **PASSO 6: Debugar se Falhar**

**Problema: Status errado**
- Verifique se o verbo está correto (GET vs POST, etc)
- Verifique se a rota está correta

**Problema: Campo não existe**
- Rode o teste e veja o JSON retornado
- Use `.response.prettyPrint()` para visualizar

**Problema: Compilação falha**
- Verifique imports (`import static org.hamcrest...`)
- Verifique se tem `.` ou `;` faltando

---

## Resumo Visual

| Tipo | Verbo | Exemplo | Retorna |
|------|-------|---------|---------|
| Get simples | GET | `/posts` | Lista |
| Get por ID | GET | `/posts/1` | Um item |
| Get filtrado | GET | `/posts?userId=1` | Lista filtrada |
| Get nested | GET | `/posts/1/comments` | Comentários |
| Criar | POST | `/posts` + body | Item criado (201) |
| Atualizar todo | PUT | `/posts/1` + body | Item atualizado (200) |
| Atualizar parte | PATCH | `/posts/1` + body | Item atualizado (200) |
| Remover | DELETE | `/posts/1` | Vazio (200) |
| Erro | GET | `/posts/99999` | Vazio (404) |

---

## Exemplo Completo do Zero

**Você lê na documentação:**
```
POST /comments
Body: { postId, name, body, email }
Status: 201
Response: { postId, id, name, body, email }
```

**Você faz:**

1️⃣ Cria método no endpoint:
```java
public Response createComment(Map<String, Object> body) {
    return request()
            .body(body)
            .when()
            .post("/comments");
}
```

2️⃣ Cria payload:
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

3️⃣ Cria teste:
```java
@Test
@DisplayName("POST /comments cria comentário")
void deveCriarComentario() {
    Map<String, Object> payload = PostPayload.createComment(1, "João", "Ótimo!", "joao@mail.com");
    Response response = endpoint.createComment(payload);

    assertThat(response.statusCode(), equalTo(201));
    assertThat(response.path("postId"), equalTo(1));
    assertThat(response.path("name"), equalTo("João"));
}
```

4️⃣ Roda:
```bash
mvn -Dtest=PostsTrainingTest test
```

✅ **Pronto!**

---

## Atalhos Rest Assured Comuns

```java
// Valor simples
response.path("id")

// Dentro de array (primeiro item)
response.path("[0].title")

// Tamanho do array
response.path("size()")

// Verificar nulo
response.path("campo") != null

// Pretty print (para debugar)
response.prettyPrint()

// Status code
response.statusCode()

// Header
response.header("Content-Type")
```

---

## Próximos Passos

- ✅ Leu este guia? Use em uma nova API
- ✅ Construiu um endpoint? Crie o teste
- ✅ Teste passou? Commita no Git
- ✅ Ficou confuso? Volte ao **Passo 1** do checklist

**Dúvida? Consulte o código real:**
- Endpoint: `src/test/java/com/gabriel/endpoints/PostsEndpoint.java`
- Teste: `src/test/java/com/gabriel/tests/PostsTrainingTest.java`
- Payload: `src/test/java/com/gabriel/payloads/PostPayload.java`
