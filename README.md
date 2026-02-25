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

## Estrutura inicial
```text
src
 └── test
      └── java
          └── com
              └── gabriel
                  ├── base
                  └── tests
```
