# Synco API

API REST para gestão acadêmica — Centraliza a comunicação institucional e o gerenciamento de dados em ambientes educacionais.

![Java 21](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot 3.3.0](https://img.shields.io/badge/Spring_Boot-3.3.0-6DB33F?logo=spring&logoColor=white)
![Coverage 68%](https://img.shields.io/badge/Coverage-68.3%25-4CAF50)
![Tests 152](https://img.shields.io/badge/Tests-152-2196F3)
![Java LOC](https://img.shields.io/badge/Java%20LOC-15.9k-9C27B0)
![Java Types](https://img.shields.io/badge/Java%20Types-277-673AB7)
![CI](https://img.shields.io/badge/CI-passing-brightgreen)

---

## Sumário

1. [Visão Geral](#visão-geral)
2. [Métricas de Qualidade](#métricas-de-qualidade)
3. [Tecnologias](#tecnologias)
4. [Arquitetura](#arquitetura)
5. [Instalação e Execução](#instalação-e-execução)
6. [API Reference](#api-reference)
7. [Segurança](#segurança)
8. [Testes](#testes)
9. [CI/CD](#cicd)
10. [Contribuição](#contribuição)
11. [Licença](#licença)

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

## Estado Atual do Projeto

### Módulos Implementados

| Módulo | Status | Descrição |
|--------|--------|-----------|
| **Autenticação** | Completo | Registro, login e redefinição de senha com JWT |
| **Usuários** | Completo | CRUD completo com controle de permissões |
| **Cursos** | Completo | Gerenciamento de cursos acadêmicos |
| **Turmas** | Completo | Gestão de turmas por curso com turnos |
| **Matrículas** | Completo | Associação de usuários às turmas (alunos, professores, representantes) |
| **Salas** | Em desenvolvimento | Cadastro e gestão de salas de aula |
| **Períodos** | Em desenvolvimento | Definição de horários (manhã, tarde, noite) |
| **Verificação de Salas** | Em desenvolvimento | Feedback sobre condições das salas |
| **Frequência** | Em desenvolvimento | Registro de presença dos alunos |

### Infraestrutura

- **Docker**: Containerização com multi-stage build otimizado
- **Docker Compose**: Orquestração com MySQL para produção
- **Health Checks**: Monitoramento de disponibilidade
- **OpenAPI/Swagger**: Documentação interativa da API

---

## Métricas de Qualidade

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
| CI Success Rate | ~87% |

### Estatísticas do Código

| Indicador | Valor |
|-----------|:-----:|
| Linhas Java (main/test) | 15,938 (11,204 main + 4,734 test) |
| Arquivos Java (main/test) | 277 (236 / 41) |
| Tipos Java declarados (main/test) | 277 (main 236: 154 classes, 60 records, 17 interfaces, 5 enums; test 41 classes) |
| Pacotes Java únicos | 139 (main 139; test 19, todos também presentes em main) |
| Módulos de domínio | 10 |

_Fonte: análise estática do repositório._

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

---

## Tecnologias

| Categoria | Tecnologia | Versão |
|-----------|------------|:------:|
| Linguagem | Java | 21 |
| Framework | Spring Boot | 3.3.0 |
| Segurança | Spring Security + JWT | 0.11.5 |
| ORM | Spring Data JPA | — |
| Banco (Dev) | H2 Database | — |
| Banco (Prod) | MySQL | 8.0 |
| Validação | Bean Validation + Passay | 1.6.6 |
| Documentação | SpringDoc OpenAPI | 2.5.0 |
| Cobertura | JaCoCo | 0.8.12 |
| Container | Docker | — |
| CI/CD | GitHub Actions | — |

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

## Instalação e Execução

### Pré-requisitos

- Java 21+
- Maven 3.6+
- Docker (opcional)

### Variáveis de Ambiente

```bash
export DB_USERNAME=sa
export DB_PASSWORD=
export JWT_SECRET=SuaChaveSecretaDeNoMinimo256BitsParaJWT
```

### Execução Local

```bash
git clone https://github.com/Luca5Eckert/SyncoApi.git
cd SyncoApi
mvn clean package
mvn spring-boot:run
```

### Execução com Docker

```bash
docker-compose up -d
docker-compose logs -f syncoapi
```

### URLs

| Recurso | URL |
|---------|-----|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| OpenAPI | http://localhost:8080/v3/api-docs |
| H2 Console | http://localhost:8080/h2-console |

---

## API Reference

### Autenticação `/api/auth`

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|:----:|
| POST | `/register` | Registrar usuário | — |
| POST | `/login` | Login | — |
| PATCH | `/password` | Alterar senha | ✓ |

### Usuários `/api/users`

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|:----:|
| GET | `/` | Listar | ✓ |
| GET | `/{id}` | Buscar por ID | ✓ |
| POST | `/` | Criar | Admin |
| PATCH | `/` | Editar | ✓ |
| DELETE | `/` | Deletar | Admin |

### Cursos `/api/courses`

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|:----:|
| GET | `/` | Listar | ✓ |
| GET | `/{id}` | Buscar por ID | ✓ |
| POST | `/` | Criar | Admin |
| PATCH | `/{id}` | Editar | Admin |
| DELETE | `/{id}` | Deletar | Admin |

### Turmas `/api/classes`

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|:----:|
| GET | `/{idCourse}/{numberClass}` | Buscar turma | ✓ |
| GET | `/{idCourse}/{numberClass}/{shift}/{page}/{size}` | Listar filtrado | ✓ |
| POST | `/` | Criar | ✓ |
| PUT | `/{idCourse}/{numberClass}` | Atualizar | ✓ |
| DELETE | `/{idCourse}/{numberClass}` | Deletar | ✓ |

### Matrículas `/api/class-users`

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|:----:|
| GET | `/` | Listar | ✓ |
| GET | `/courses/{id}/classes/{num}/users/{uid}` | Buscar | ✓ |
| POST | `/` | Criar | Admin |
| PATCH | `/courses/{id}/classes/{num}/users/{uid}` | Atualizar | Admin |
| DELETE | `/courses/{id}/classes/{num}/users/{uid}` | Remover | Admin |

### Salas `/api/rooms`

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|:----:|
| GET | `/` | Listar | ✓ |
| GET | `/{id}` | Buscar por ID | ✓ |
| POST | `/` | Criar | ✓ |
| PUT | `/{id}` | Atualizar | ✓ |
| DELETE | `/{id}` | Deletar | ✓ |

---

## Segurança

### Autenticação JWT

```
Authorization: Bearer {token}
```

Tokens expiram em 24h.

### Perfis

| Perfil | Permissões |
|--------|------------|
| USER | Visualização e edição própria |
| ADMIN | Gerenciamento completo |

### Tipos de Usuário em Turmas

| Tipo | Descrição |
|------|-----------|
| ADMINISTRATOR | Administrador institucional |
| SECRETARY | Secretaria acadêmica |
| TEACHER | Professor |
| REPRESENTATIVE | Representante |
| STUDENT | Aluno |

### Validações

- **Email**: Formato válido, máximo 150 caracteres, único
- **Senha**: Mínimo 8 caracteres (maiúscula, minúscula, número, especial)
- **Nome**: Obrigatório, máximo 30 caracteres

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

## CI/CD

### GitHub Actions

Pipeline executado em push/PR para `main`:

```
Checkout → Setup JDK 21 → Cache Maven → Run Tests → Coverage Report
```

### Docker

- **Multi-stage build** otimizado
- **Docker Compose** com MySQL
- **Health Checks** integrado

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

## Recursos

| Recurso | Link |
|---------|------|
| OpenAPI Spec | [docs/openapi.yaml](docs/openapi.yaml) |
| Exemplos | [docs/EXEMPLOS.md](docs/EXEMPLOS.md) |
| CI Builds | [GitHub Actions](https://github.com/Luca5Eckert/SyncoApi/actions) |

---

## Licença

MIT License — Veja [LICENSE](LICENSE) para detalhes.

---

**Mantido por** [Luca Eckert](https://github.com/Luca5Eckert)
