# Java Rest Assured Automation Suite

Framework de automação de testes de API desenvolvido em Java utilizando Rest Assured, Maven e JUnit 5.  
O objetivo deste projeto é praticar, estruturar e evoluir testes de API seguindo padrões corporativos de modularização, organização e boas práticas.

## Tecnologias
- Java 17+
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

## Como executar
```bash
mvn clean test
```

## Cenários implementados
- `UsersCrudTest`: fluxo completo CRUD usando `REQRES`
    - `GET /users?page=2`
    - `GET /users/2`
    - `GET /users/23` (not found)
    - `POST /users`
    - `PUT /users/2`
    - `DELETE /users/2`

- `UserPayloadTest`: valida montagem de payloads para criação e atualização
- `TestDataTest`: valida leitura dos dados de teste do JSON
- `TestConfigTest`: valida leitura das configurações do projeto

> Observação: o REQRES atual pode exigir API key do dashboard (`api.practice.key` em `src/test/resources/config/config.properties`).
> Sem chave válida, os cenários de `UsersCrudTest` são pulados automaticamente.

## Estratégia de testes (didática)
- Testes de `config/data/payload` rodam sem internet e garantem a base do framework
- Testes de `UsersCrudTest` validam os endpoints reais da API
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
