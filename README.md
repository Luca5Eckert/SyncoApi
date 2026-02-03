# Synco API

API REST para gestão acadêmica — Centraliza a comunicação institucional e o gerenciamento de dados em ambientes educacionais.

![Java 21](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot 3.3.0](https://img.shields.io/badge/Spring_Boot-3.3.0-6DB33F?logo=spring&logoColor=white)
![Coverage 68%](https://img.shields.io/badge/Coverage-68.3%25-4CAF50)
![Tests 152](https://img.shields.io/badge/Tests-152-2196F3)
![Java Lines of Code 15.9k](https://img.shields.io/badge/Java%20LOC-15.9k-9C27B0)
![Java Types 277](https://img.shields.io/badge/Java%20Types-277-673AB7)
![CI](https://img.shields.io/badge/CI-passing-brightgreen)

---

## Sumário

1. [Visão Geral](#visão-geral)
2. [Estado Atual do Projeto](#estado-atual-do-projeto)
3. [Quickstart (2 minutos)](#quickstart-2-minutos)
4. [URLs úteis](#urls-úteis)
5. [Variáveis de Ambiente](#variáveis-de-ambiente)
6. [API Reference](#api-reference)
7. [Segurança](#segurança)
8. [Testes](#testes)
9. [Docker & CI/CD](#docker--cicd)
10. [Arquitetura](#arquitetura)
11. [Métricas](#métricas)
12. [Contribuição](#contribuição)
13. [Licença](#licença)

---

## Visão Geral

O Synco API é o backend de uma plataforma de gestão acadêmica que visa solucionar problemas de fragmentação na comunicação entre coordenação, professores e alunos. A API oferece uma fonte única e confiável de informações (Single Source of Truth), eliminando a dispersão de dados em canais não oficiais.

### Problema

A comunicação institucional em ambientes acadêmicos frequentemente sofre com:
- Dispersão de informações em múltiplos canais não oficiais
- Falta de rastreabilidade de comunicados
- Ausência de um sistema centralizado para controle de faltas e horários
- Dificuldade na gestão de turmas e matrículas

### Solução

Esta API fornece infraestrutura para:
- Registro e autenticação segura de usuários com diferentes perfis
- Gerenciamento completo de cursos, turmas e matrículas
- Controle de salas e verificação de ambientes
- Registro e consulta de frequência (em desenvolvimento)

---

## Estado Atual do Projeto

### Módulos Implementados

| Módulo | Status | Descrição |
|--------|--------|-----------|
| **Autenticação** | Completo | Registro, login e redefinição de senha com JWT |
| **Usuários** | Completo | CRUD completo com controle de permissões |
| **Cursos** | Completo | Gerenciamento de cursos acadêmicos |
| **Turmas** | Completo | Gestão de turmas por curso com turnos |
| **Matrículas** | Completo | Associação de usuários às turmas (alunos, professores, representantes) |
| **Salas** | Completo | Cadastro e gestão de salas de aula |
| **Períodos** | Completo | Definição de horários (manhã, tarde, noite) |
| **Verificação de Salas** | Em desenvolvimento | Feedback sobre condições das salas |
| **Frequência** | Em desenvolvimento | Registro de presença dos alunos |

### Infraestrutura

- **Docker**: Containerização com multi-stage build otimizado
- **Docker Compose**: Orquestração com MySQL para produção
- **Health Checks**: Monitoramento de disponibilidade
- **OpenAPI/Swagger**: Documentação interativa da API

---

## Quickstart (2 minutos)

### Local (H2, em memória)

1. (Opcional) Defina o segredo JWT local:
   ```bash
   export JWT_SECRET="dev-secret-key-for-testing-only-not-for-production-use-minimum-32-chars"
   ```
   > Se não definir, o valor padrão de desenvolvimento é usado (ver `application.properties`).

2. Suba a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Abra o Swagger para testar rapidamente:
   - http://localhost:8080/swagger-ui/index.html

### Docker Compose (MySQL, produção)

1. Crie um arquivo `.env` (baseie-se em um futuro `.env.example`):
   ```bash
   # TODO: criar .env.example no repositório
   SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/synco_db
   DB_USERNAME=<seu-usuario>
   DB_PASSWORD=<sua-senha>
   MYSQL_ROOT_PASSWORD=<senha-root>
   JWT_SECRET=<segredo-jwt>
   JWT_TOKEN_VALIDITY=86400000
   SERVER_PORT=8080
   ```

2. Suba os serviços:
   ```bash
   docker-compose up -d --build
   ```

3. Acompanhe os logs:
   ```bash
   docker-compose logs -f syncoapi
   ```

4. Parar serviços:
   ```bash
   docker-compose down
   ```

5. Resetar dados (remove volumes):
   ```bash
   docker-compose down -v
   ```

> O `docker-compose.yml` sobe dois serviços: `syncoapi` (Spring Boot) e `mysql` (porta 3307→3306), com volume `mysql-data` para persistência.

---

## URLs úteis

| Recurso | URL |
|---------|-----|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| OpenAPI | http://localhost:8080/v3/api-docs |
| H2 Console (dev) | http://localhost:8080/h2-console |

---

## Variáveis de Ambiente

> Sugestão: adicionar um `.env.example` no repositório (ver TODO).

### Dev (H2)

| Variável | Obrigatória | Descrição |
|---------|:-----------:|-----------|
| JWT_SECRET | Não | Segredo JWT. Se omitida, usa valor padrão definido em `application.properties`. |

### Prod (MySQL / Docker Compose)

| Variável | Obrigatória | Descrição |
|---------|:-----------:|-----------|
| SPRING_PROFILES_ACTIVE | Sim | Deve ser `prod` (já definido no `docker-compose.yml`). |
| SPRING_DATASOURCE_URL | Sim | URL JDBC do MySQL usada pelo profile `prod`. **TODO**: fornecer valor. |
| DB_USERNAME | Sim | Usuário do banco (usado também no container MySQL). |
| DB_PASSWORD | Sim | Senha do banco (usado também no container MySQL). |
| MYSQL_ROOT_PASSWORD | Sim | Senha do usuário root do MySQL. |
| JWT_SECRET | Sim | Segredo para assinatura do token JWT. |
| JWT_TOKEN_VALIDITY | Não | Tempo de validade do token (ms). Padrão: `86400000`. |
| SERVER_PORT | Não | Porta externa exposta (padrão `8080`). |

---

## API Reference

**Legenda da coluna Auth**
- `—` público (sem JWT)
- `✓` requer JWT válido
- `Admin` requer ROLE_ADMIN

> Para payloads completos, consulte o Swagger UI.

### Autenticação `/api/auth`

| Método | Endpoint | Auth | Descrição |
|--------|----------|:----:|-----------|
| POST | `/register` | — | Registra um usuário (name, email, password) |
| POST | `/login` | — | Login e retorno de token JWT |
| PATCH | `/password` | ✓ | Reset de senha do usuário autenticado |

**Exemplo (login e obtenção de token)**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"SenhaForte123!"}'
```
> Copie o valor de `data.token` da resposta para usar nos endpoints protegidos.

### Usuários `/api/users`

| Método | Endpoint | Auth | Descrição |
|--------|----------|:----:|-----------|
| GET | `/` | ✓ | Lista usuários com filtros (name, email, role, createAt, updateAt, page, size) |
| GET | `/{id}` | ✓ | Busca usuário por ID |
| POST | `/` | Admin | Cria usuário (name, email, password, roleUser) |
| PATCH | `/` | ✓ | Atualiza usuário (id, name, email) |
| DELETE | `/` | ✓ | Remove usuário (id) |

> **Ambiguidade**: PATCH/DELETE usam body JSON (não URL). Exemplo de body:
```json
{ "id": 1, "name": "Novo Nome", "email": "novo@email.com" }
```

### Cursos `/api/courses`

| Método | Endpoint | Auth | Descrição |
|--------|----------|:----:|-----------|
| GET | `/` | ✓ | Lista cursos (name, acronym; query param `acroym`, page, size) |
| GET | `/{id}` | ✓ | Busca por ID |
| POST | `/` | Admin | Cria curso |
| PATCH | `/{id}` | Admin | Atualiza curso |
| DELETE | `/{id}` | Admin | Remove curso |

> **Nota**: o filtro de acronym usa o parâmetro `acroym` (sic) conforme controller. **TODO**: alinhar para `acronym` no código e documentação.

### Turmas `/api/classes`

| Método | Endpoint | Auth | Descrição |
|--------|----------|:----:|-----------|
| GET | `/{idCourse}/{numberClass}` | ✓ | Busca turma por curso + número |
| GET | `/{idCourse}/{numberClass}/{shift}/{pageNumber}/{pageSize}` | ✓ | Lista turmas com filtros |
| POST | `/` | ✓ | Cria turma |
| PUT | `/{idCourse}/{numberClass}` | ✓ | Atualiza turma |
| DELETE | `/{idCourse}/{numberClass}` | ✓ | Remove turma |

> **TODO**: confirmar contrato da rota de listagem (`GET /api/classes/...`) — o controller usa path params, mas recebe filtros via query params.

### Matrículas `/api/class-users`

| Método | Endpoint | Auth | Descrição |
|--------|----------|:----:|-----------|
| GET | `/` | ✓ | Lista matrículas com filtros (userId, courseId, numberClass, typeUserClass, page, size) |
| GET | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | ✓ | Busca matrícula específica |
| POST | `/` | Admin | Cria matrícula |
| PATCH | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | Admin | Atualiza matrícula |
| DELETE | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | Admin | Remove matrícula |

### Salas `/api/rooms`

| Método | Endpoint | Auth | Descrição |
|--------|----------|:----:|-----------|
| GET | `/` | ✓ | Lista salas (typeRoom, number, page, size) |
| GET | `/{id}` | ✓ | Busca sala por ID |
| POST | `/` | ✓ | Cria sala |
| PUT | `/{id}` | ✓ | Atualiza sala |
| DELETE | `/{id}` | ✓ | Remove sala |

### Períodos `/api/periods` (em desenvolvimento)

| Método | Endpoint | Auth | Descrição |
|--------|----------|:----:|-----------|
| GET | `/` | ✓ | Lista períodos (teacherId, roomId, classId, typePeriod, page, size) |
| GET | `/{id}` | ✓ | Busca período por ID |
| POST | `/` | ✓ | Cria período |

---

### Exemplo de endpoint protegido

```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer <TOKEN>"
```

### Exemplo de erro padrão (TODO)

A API retorna erros no formato de `CustomApiResponse`:
```json
{
  "timestamp": "2026-02-03T00:00:00Z",
  "status": 400,
  "error": "TODO",
  "message": "TODO",
  "path": "/api/rota",
  "data": null
}
```
> **TODO**: fornecer um exemplo real de erro (400/401) a partir da API.

---

## Segurança

- Autenticação via JWT (`Authorization: Bearer {token}`)
- Tokens expiram em 24h (`jwt.token.validity=86400000`)
- Roles disponíveis: `USER` e `ADMIN`
- **Auth na tabela**: `✓` indica JWT obrigatório; `Admin` indica ROLE_ADMIN
- **Refresh token**: **TODO** — não há endpoint de refresh no contexto atual

---

## Testes

```bash
# Executar testes
mvn test

# Relatório de cobertura
mvn jacoco:report
```

Relatório: `target/site/jacoco/index.html`

| Comando | Descrição |
|---------|-----------|
| `mvn test` | Executa os 152 testes |
| `mvn jacoco:report` | Gera relatório HTML |
| `mvn test -Dtest=*UseCaseTest` | Apenas Use Cases |
| `mvn test -Dtest=*IntegrationTest` | Apenas integração |

---

## Docker & CI/CD

### Docker

- Multi-stage build
- Compose com MySQL e Health Checks

### CI/CD

Pipeline executado em push/PR para `main`:
```
Checkout → Setup JDK 21 → Cache Maven → Run Tests → Coverage Report
```

---

## Arquitetura

O projeto segue **Clean Architecture**:

```
src/main/java/com/api/synco/
├── core/                     # Interfaces centrais
├── infrastructure/           # Componentes transversais
│   ├── api/                  # Respostas padronizadas
│   ├── config/               # Configurações
│   ├── exception/            # Tratamento de exceções
│   ├── persistence/          # Repositórios
│   ├── security/             # JWT e segurança
│   └── service/              # Serviços de infraestrutura
└── module/                   # Módulos de domínio
    ├── authentication/       # Autenticação
    ├── user/                 # Usuários
    ├── course/               # Cursos
    ├── class_entity/         # Turmas
    ├── class_user/           # Matrículas
    ├── period/               # Períodos
    ├── room/                 # Salas
    ├── room_verification/    # Verificação de ambientes
    ├── attendance_user/      # Frequência
    └── permission/           # Permissões
```

### Padrões

| Padrão | Aplicação |
|--------|-----------|
| Clean Architecture | Independência entre camadas |
| Repository Pattern | Abstração de persistência |
| Use Case Pattern | Lógica de negócio encapsulada |
| Value Objects | Validação (Email, Name) |
| Policy Pattern | Permissões desacopladas |

---

## Métricas

<details>
<summary><strong>Resumo de métricas e cobertura (JaCoCo)</strong></summary>

### Cobertura de Código (JaCoCo)

| Métrica | Cobertura | Absoluto |
|---------|:---------:|:--------:|
| Classes | **81,1%** | 167/206 |
| Métodos | **64,4%** | 390/606 |
| Branches | **51,7%** | 139/269 |
| Linhas | **68,3%** | 960/1.406 |

### Indicadores Gerais

| Indicador | Valor |
|-----------|:-----:|
| Total de Testes | 152 |
| Arquivos de Teste (JUnit) | 41 |
| Endpoints | 28 |
| Controllers | 8 |

### Estatísticas do Código

| Indicador | Valor |
|-----------|:-----:|
| Linhas Java (main/test) | 15.9k (11.2k main + 4.7k test) |
| Arquivos Java (main/test) | 277 (236 / 41) |
| Tipos Java declarados (main/test) | 277 (main 236: 154 classes, 60 records, 17 interfaces, 5 enums; test 41 classes) |
| Pacotes Java únicos | 139 |
| Módulos de domínio | 10 |

### Cobertura por Módulo

<details>
<summary><strong>Autenticação</strong> — 100% classes</summary>

| Package | Classes | Métodos | Linhas |
|---------|:-------:|:-------:|:------:|
| `authentication.application.controller` | 100% | 100% | 100% |
| `authentication.domain.use_case` | 100% | 100% | 100% |
| `authentication.domain.service` | 100% | 100% | 100% |
| `authentication.domain.mapper` | 100% | 100% | 100% |

</details>

<details>
<summary><strong>Usuários</strong> — 100% classes</summary>

| Package | Classes | Métodos | Linhas |
|---------|:-------:|:-------:|:------:|
| `user.application.controller` | 100% | 100% | 100% |
| `user.domain.use_case` | 100% | 100% | 98,2% |
| `user.domain.service` | 100% | 100% | 100% |
| `user.domain.validator` | 100% | 100% | 100% |

</details>

<details>
<summary><strong>Cursos</strong> — 100% classes</summary>

| Package | Classes | Métodos | Linhas |
|---------|:-------:|:-------:|:------:|
| `course.application.controller` | 100% | 100% | 100% |
| `course.domain.use_cases` | 100% | 100% | 100% |
| `course.domain.service` | 100% | 100% | 100% |
| `course.domain.mapper` | 100% | 100% | 100% |

</details>

<details>
<summary><strong>Turmas</strong> — 100% use cases</summary>

| Package | Classes | Métodos | Linhas |
|---------|:-------:|:-------:|:------:|
| `class_entity.application.controller` | 100% | 83,3% | 88,9% |
| `class_entity.domain.use_case` | 100% | 100% | 100% |
| `class_entity.domain.service` | 100% | 83,3% | 60,7% |

</details>

<details>
<summary><strong>Matrículas</strong> — 100% use cases</summary>

| Package | Classes | Métodos | Linhas |
|---------|:-------:|:-------:|:------:|
| `class_user.domain.use_cases` | 100% | 100% | 100% |
| `class_user.domain.enumerator` | 100% | 100% | 100% |

</details>

<details>
<summary><strong>Salas</strong> — 98% use cases</summary>

| Package | Classes | Métodos | Linhas |
|---------|:-------:|:-------:|:------:|
| `room.domain.use_case` | 100% | 100% | 98% |
| `room.domain.filter` | 100% | 100% | 100% |
| `room.domain.exception` | 100% | 100% | 100% |

</details>

<details>
<summary><strong>Infraestrutura</strong></summary>

| Package | Classes | Métodos | Linhas |
|---------|:-------:|:-------:|:------:|
| `infrastructure.security` | 100% | 100% | 100% |
| `infrastructure.security.jwt` | 100% | 100% | 77,4% |
| `infrastructure.security.user_details` | 100% | 100% | 100% |
| `infrastructure.api` | 100% | 100% | 100% |
| `infrastructure.config` | 100% | 100% | 100% |

</details>

</details>

---

## Contribuição

### Processo

1. Fork o repositório
2. Clone: `git clone https://github.com/<user>/SyncoApi.git`
3. Branch: `git checkout -b feature/nome`
4. Desenvolva seguindo os padrões
5. Teste: `mvn test`
6. Commit: `git commit -m "feat: descrição"`
7. Push: `git push origin feature/nome`
8. Abra Pull Request

### Padrões de Código

- Java 21 com features modernas
- Clean Architecture
- CamelCase (classes/métodos), lowercase (pacotes)
- Testes unitários obrigatórios para use cases
- Javadoc para APIs públicas

### Conventional Commits

| Tipo | Uso |
|------|-----|
| `feat` | Nova funcionalidade |
| `fix` | Correção de bug |
| `docs` | Documentação |
| `refactor` | Refatoração |
| `test` | Testes |
| `chore` | Manutenção |

---

## Licença

MIT License — Veja [LICENSE](LICENSE) para detalhes.

---

**Mantido por** [Luca Eckert](https://github.com/Luca5Eckert)
