# Java Rest Assured Automation Suite

Framework de automação de testes de API desenvolvido em Java utilizando Rest Assured, Maven e JUnit 5.  
O objetivo deste projeto é praticar, estruturar e evoluir testes de API seguindo padrões corporativos de modularização, organização e boas práticas.

## Tecnologias
- Java 21 (LTS)
- Maven
- Rest Assured
- JUnit 5
- VS Code (ou qualquer IDE compatível)

## Objetivos do Projeto
- Criar testes GET, POST, PUT, DELETE
- Validar payloads JSON
- Implementar configuração global do Rest Assured
- Estruturar o projeto em camadas (base, endpoints, payloads, tests)
- Evoluir para autenticação, testes parametrizados e relatórios

## Pré-requisitos
- JDK 21 instalado e configurado no `JAVA_HOME`
- Maven 3.9+

## Como executar
```bash
mvn clean test
```

## Saída legível (PowerShell)
Se o log bruto do VS Code estiver confuso, rode:

```powershell
./scripts/run-training.ps1
```

Para uma classe específica:

```powershell
./scripts/run-training.ps1 -TestClass PostsCrudTest
```

## Configuração
Arquivo: `src/test/resources/config/config.properties`

```properties
api.content.type=application/json
api.practice.base.url=https://jsonplaceholder.typicode.com
```

- Não é necessária API key para JSONPlaceholder.

## Cenários implementados
- `PostsCrudTest`: fluxo completo usando `JSONPlaceholder`
    - `GET /posts`
    - `GET /posts/1`
    - `GET /posts/99999` (not found)
    - `POST /posts`
    - `PUT /posts/1`
    - `PATCH /posts/1`
    - `DELETE /posts/1`
    - `GET /posts?userId=1` (filtro)
    - `GET /posts/1/comments` (rota aninhada)

- `PostPayloadTest`: valida montagem de payloads para criação e atualização
- `TestDataTest`: valida leitura dos dados de teste do JSON
- `TestConfigTest`: valida leitura das configurações do projeto

> Observação: JSONPlaceholder simula persistência para POST/PUT/PATCH/DELETE.

## Estratégia de testes (didática)
- Testes de `config/data/payload` rodam sem internet e garantem a base do framework
- Testes de `PostsCrudTest` validam os endpoints reais da API
- Assim você aprende primeiro a estrutura, depois o comportamento HTTP

## Estrutura inicial
```text
src
 └── test
      ├── java/com/gabriel
      │   ├── base
      │   ├── config
      │   ├── endpoints
      │   ├── payloads
      │   └── tests
      └── resources
          ├── config
          └── data
```

## Equivalência com Playwright
- `pages` (UI) -> `endpoints` (API)
- `BaseTest` continua existindo para setup global
- `config` e `resources/data` continuam com o mesmo papel
