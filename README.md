<div align="center">

# 🎓 Synco API

**API REST para gestão acadêmica** — Centralizando a comunicação institucional e o gerenciamento de dados em ambientes educacionais.

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Coverage](https://img.shields.io/badge/Coverage-68.3%25-brightgreen?style=for-the-badge&logo=codecov&logoColor=white)](docs/coverage)
[![Tests](https://img.shields.io/badge/Tests-152-blue?style=for-the-badge&logo=junit5&logoColor=white)](docs/tests)
[![CI](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)](https://github.com/Luca5Eckert/SyncoApi/actions)

</div>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Métricas de Qualidade](#-métricas-de-qualidade)
- [Cobertura de Testes](#-cobertura-de-testes)
- [Stack Tecnológica](#-stack-tecnológica)
- [Arquitetura](#-arquitetura)
- [Execução](#-execução)
- [Endpoints da API](#-endpoints-da-api)
- [CI/CD](#-cicd)
- [Como Contribuir](#-como-contribuir)
- [Contato](#-contato)

---

## 🎯 Sobre o Projeto

**Synco API** é o backend de uma plataforma de gestão acadêmica projetada para solucionar problemas de fragmentação na comunicação entre coordenação, professores e alunos. A API oferece uma **fonte única e confiável de informações** (*Single Source of Truth*), eliminando a dispersão de dados em canais não oficiais.

### ✨ Diferenciais

| Feature | Descrição |
|:-------:|-----------|
| 🏗️ **Clean Architecture** | Separação clara de responsabilidades, facilitando manutenção e evolução |
| 🔒 **Segurança Robusta** | Autenticação JWT com políticas de permissão granulares |
| 📚 **Documentação Completa** | Swagger/OpenAPI integrado para fácil integração |
| 🐳 **Infraestrutura Moderna** | Docker com multi-stage build e CI/CD automatizado |
| ✅ **Testes Abrangentes** | 152 testes com 68% de cobertura de linhas |

### 🚀 Funcionalidades Principais

| Módulo | Status | Descrição |
|--------|:------:|-----------|
| **Autenticação** | ✅ | Registro, login e redefinição de senha com JWT |
| **Gestão de Usuários** | ✅ | CRUD completo com controle de permissões |
| **Gestão de Cursos** | ✅ | Gerenciamento de cursos acadêmicos |
| **Gestão de Turmas** | ✅ | Organização de turmas por curso com turnos |
| **Matrículas** | ✅ | Associação de usuários às turmas |
| **Gestão de Salas** | 🔄 | Cadastro e gerenciamento de ambientes físicos |
| **Controle de Frequência** | 🔄 | Registro de presença dos alunos |

---

## 📊 Métricas de Qualidade

<div align="center">

### 🏆 Resumo Geral de Cobertura (JaCoCo)

| Métrica | Cobertura | Detalhes |
|:-------:|:---------:|:--------:|
| **📦 Classes** | **81,1%** | 167 de 206 |
| **⚙️ Métodos** | **64,4%** | 390 de 606 |
| **🌿 Branches** | **51,7%** | 139 de 269 |
| **📝 Linhas** | **68,3%** | 960 de 1.406 |

</div>

### 📈 Indicadores de Qualidade

| Indicador | Valor | Descrição |
|-----------|:-----:|-----------|
| 🧪 **Total de Testes** | **152** | Métodos de teste implementados |
| 📁 **Arquivos de Teste** | **35** | Classes de teste |
| 🔌 **Endpoints Ativos** | **28** | Distribuídos em 8 controllers |
| 🏛️ **Classes Testadas** | **81,1%** | 167 de 206 classes |
| 📏 **Linhas Cobertas** | **68,3%** | 960 de 1.406 linhas |
| ⚡ **CI Success Rate** | **~87%** | GitHub Actions |

---

## 🧪 Cobertura de Testes

### 📦 Cobertura por Módulo

<details>
<summary><b>🔐 Módulo de Autenticação — 100% Cobertura de Classes</b></summary>

| Package | Classes | Métodos | Branches | Linhas |
|---------|:-------:|:-------:|:--------:|:------:|
| `authentication.application.controller` | 100% (1/1) | 100% (4/4) | - | 100% (10/10) |
| `authentication.application.dto.login` | 100% (2/2) | 100% (2/2) | - | 100% (2/2) |
| `authentication.application.dto.register` | 100% (2/2) | 100% (2/2) | - | 100% (2/2) |
| `authentication.domain.use_case` | 100% (3/3) | 100% (6/6) | 100% (10/10) | 100% (33/33) |
| `authentication.domain.service` | 100% (1/1) | 100% (4/4) | - | 100% (9/9) |
| `authentication.domain.mapper` | 100% (1/1) | 100% (3/3) | - | 100% (7/7) |

</details>

<details>
<summary><b>👤 Módulo de Usuários — 100% Cobertura de Classes</b></summary>

| Package | Classes | Métodos | Branches | Linhas |
|---------|:-------:|:-------:|:--------:|:------:|
| `user.application.controller` | 100% (1/1) | 100% (6/6) | - | 100% (19/19) |
| `user.domain.use_case` | 100% (5/5) | 100% (12/12) | 92,9% (13/14) | 98,2% (55/56) |
| `user.domain.service` | 100% (1/1) | 100% (6/6) | - | 100% (18/18) |
| `user.domain` | 100% (1/1) | 93,8% (15/16) | 62,5% (10/16) | 96,2% (25/26) |
| `user.domain.validator` | 100% (1/1) | 100% (2/2) | 100% (2/2) | 100% (6/6) |
| `user.domain.vo` | 100% (2/2) | 100% (5/5) | 60% (6/10) | 100% (10/10) |

</details>

<details>
<summary><b>📚 Módulo de Cursos — 100% Cobertura de Classes</b></summary>

| Package | Classes | Métodos | Branches | Linhas |
|---------|:-------:|:-------:|:--------:|:------:|
| `course.application.controller` | 100% (1/1) | 100% (6/6) | - | 100% (17/17) |
| `course.domain.use_cases` | 100% (5/5) | 100% (10/10) | 100% (10/10) | 100% (46/46) |
| `course.domain.service` | 100% (1/1) | 100% (6/6) | - | 100% (18/18) |
| `course.domain.mapper` | 100% (1/1) | 100% (5/5) | - | 100% (21/21) |
| `course.domain.permission` | 100% (1/1) | 100% (4/4) | - | 100% (4/4) |

</details>

<details>
<summary><b>🏫 Módulo de Turmas — 100% Cobertura de Use Cases</b></summary>

| Package | Classes | Métodos | Branches | Linhas |
|---------|:-------:|:-------:|:--------:|:------:|
| `class_entity.application.controller` | 100% (1/1) | 83,3% (5/6) | - | 88,9% (16/18) |
| `class_entity.domain.use_case` | 100% (5/5) | 100% (10/10) | 100% (8/8) | 100% (52/52) |
| `class_entity.domain.service` | 100% (1/1) | 83,3% (5/6) | - | 60,7% (17/28) |
| `class_entity.domain.permission` | 100% (1/1) | 100% (4/4) | - | 100% (4/4) |

</details>

<details>
<summary><b>📝 Módulo de Matrículas — 100% Cobertura de Use Cases</b></summary>

| Package | Classes | Métodos | Branches | Linhas |
|---------|:-------:|:-------:|:--------:|:------:|
| `class_user.domain.use_cases` | 100% (5/5) | 100% (10/10) | 100% (8/8) | 100% (56/56) |
| `class_user.domain.enumerator` | 100% (1/1) | 100% (2/2) | - | 100% (6/6) |

</details>

<details>
<summary><b>🚪 Módulo de Salas — 98% Cobertura de Use Cases</b></summary>

| Package | Classes | Métodos | Branches | Linhas |
|---------|:-------:|:-------:|:--------:|:------:|
| `room.domain.use_case` | 100% (5/5) | 100% (10/10) | 90% (9/10) | 98% (49/50) |
| `room.domain.filter` | 100% (2/2) | 100% (2/2) | - | 100% (2/2) |
| `room.domain.exception` | 100% (2/2) | 100% (2/2) | - | 100% (2/2) |
| `room.domain.enumerator` | 100% (1/1) | 100% (2/2) | - | 100% (2/2) |

</details>

<details>
<summary><b>🔧 Infraestrutura — Alta Cobertura</b></summary>

| Package | Classes | Métodos | Branches | Linhas |
|---------|:-------:|:-------:|:--------:|:------:|
| `infrastructure.security` | 100% (1/1) | 100% (6/6) | - | 100% (14/14) |
| `infrastructure.security.jwt` | 100% (2/2) | 100% (9/9) | 57,1% (16/28) | 77,4% (48/62) |
| `infrastructure.security.user_details` | 100% (3/3) | 100% (9/9) | - | 100% (20/20) |
| `infrastructure.api` | 100% (1/1) | 100% (4/4) | - | 100% (4/4) |
| `infrastructure.config` | 100% (1/1) | 100% (2/2) | - | 100% (19/19) |
| `infrastructure.persistence.user.repository` | 100% (1/1) | 100% (8/8) | - | 100% (12/12) |
| `infrastructure.persistence.course.repository` | 100% (1/1) | 100% (7/7) | - | 100% (11/11) |

</details>

### 📊 Highlights de Cobertura

```
🏆 Módulos com 100% de Cobertura em Use Cases:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
├── ✅ Authentication Use Cases  → 100% (33/33 linhas)
├── ✅ User Use Cases           → 98.2% (55/56 linhas)  
├── ✅ Course Use Cases         → 100% (46/46 linhas)
├── ✅ Class Use Cases          → 100% (52/52 linhas)
├── ✅ Class User Use Cases     → 100% (56/56 linhas)
└── ✅ Room Use Cases           → 98%  (49/50 linhas)
```

---

## 🛠️ Stack Tecnológica

<div align="center">

| Categoria | Tecnologia | Versão |
|:---------:|:----------:|:------:|
| ![Java](https://img.shields.io/badge/-Java-ED8B00?logo=openjdk&logoColor=white) | **Java** | 21 |
| ![Spring](https://img.shields.io/badge/-Spring_Boot-6DB33F?logo=spring&logoColor=white) | **Spring Boot** | 3.3.0 |
| ![Security](https://img.shields.io/badge/-Spring_Security-6DB33F?logo=springsecurity&logoColor=white) | **Spring Security + JWT** | jjwt 0.11.5 |
| ![JPA](https://img.shields.io/badge/-Spring_Data_JPA-6DB33F?logo=spring&logoColor=white) | **Spring Data JPA** | - |
| ![H2](https://img.shields.io/badge/-H2_Database-1A237E?logo=databricks&logoColor=white) | **H2 Database (Dev)** | Em memória |
| ![MySQL](https://img.shields.io/badge/-MySQL-4479A1?logo=mysql&logoColor=white) | **MySQL (Prod)** | 8.0 |
| ![OpenAPI](https://img.shields.io/badge/-OpenAPI-85EA2D?logo=swagger&logoColor=black) | **SpringDoc OpenAPI** | 2.5.0 |
| ![JaCoCo](https://img.shields.io/badge/-JaCoCo-C71A36?logo=codecov&logoColor=white) | **JaCoCo Coverage** | 0.8.12 |
| ![Docker](https://img.shields.io/badge/-Docker-2496ED?logo=docker&logoColor=white) | **Docker** | Multi-stage |
| ![CI](https://img.shields.io/badge/-GitHub_Actions-2088FF?logo=github-actions&logoColor=white) | **CI/CD** | - |

</div>

---

## 🏗️ Arquitetura

O projeto adota **Clean Architecture** com separação clara de responsabilidades:

```
src/main/java/com/api/synco/
├── 📁 core/                            # Interfaces centrais
├── 📁 infrastructure/                  # Componentes transversais
│   ├── api/                            # Respostas padronizadas
│   ├── config/                         # Configurações (OpenAPI, CORS)
│   ├── exception/                      # Tratamento global de exceções
│   ├── persistence/                    # Implementações de repositórios
│   ├── security/                       # JWT e configuração de segurança
│   └── service/                        # Serviços de infraestrutura
└── 📁 module/                          # Módulos de domínio
    ├── authentication/                 # 🔐 Autenticação (registro, login, senha)
    ├── user/                           # 👤 Gestão de usuários
    ├── course/                         # 📚 Gestão de cursos
    ├── class_entity/                   # 🏫 Gestão de turmas
    ├── class_user/                     # 📝 Matrículas (usuário-turma)
    ├── period/                         # ⏰ Períodos (manhã, tarde, noite)
    ├── room/                           # 🚪 Gestão de salas
    ├── room_verification/              # ✅ Verificação de ambientes
    ├── attendance_user/                # 📋 Controle de frequência
    └── permission/                     # 🔒 Políticas de permissão
```

### 🎯 Padrões Aplicados

| Padrão | Descrição |
|:------:|-----------|
| **Clean Architecture** | Independência entre camadas |
| **Repository Pattern** | Abstração de persistência |
| **Use Case Pattern** | Encapsulamento de lógica de negócio |
| **Value Objects** | Objetos imutáveis para validação (Email, Name) |
| **Policy Pattern** | Políticas de permissão desacopladas |

---

## 🚀 Execução

### 📋 Pré-requisitos

- ☕ Java 21+
- 📦 Maven 3.6+
- 🐳 Docker e Docker Compose (para execução containerizada)

### 🔐 Variáveis de Ambiente

```bash
export DB_USERNAME=sa
export DB_PASSWORD=
export JWT_SECRET=SuaChaveSecretaDeNoMinimo256BitsParaJWT
```

### 💻 Execução Local

```bash
# Clonar repositório
git clone https://github.com/Luca5Eckert/SyncoApi.git
cd SyncoApi

# Compilar
mvn clean package

# Executar
mvn spring-boot:run
```

### 🐳 Execução com Docker

```bash
# Construir e executar
docker-compose up -d

# Verificar logs
docker-compose logs -f syncoapi
```

### 🔗 Acessos

| Recurso | URL |
|:-------:|-----|
| 🌐 **API** | http://localhost:8080 |
| 📖 **Swagger UI** | http://localhost:8080/swagger-ui/index.html |
| 📄 **OpenAPI JSON** | http://localhost:8080/v3/api-docs |
| 💾 **H2 Console (Dev)** | http://localhost:8080/h2-console |

---

## 🔌 Endpoints da API

### 🔐 Autenticação (`/api/auth`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/register` | Registrar usuário | Não |
| POST | `/login` | Autenticar usuário | Não |
| PATCH | `/password` | Alterar senha | Sim |

### 👤 Usuários (`/api/users`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Listar usuários | Sim |
| GET | `/{id}` | Buscar por ID | Sim |
| POST | `/` | Criar usuário | ADMIN |
| PATCH | `/` | Editar usuário | Sim |
| DELETE | `/` | Deletar usuário | ADMIN |

### 📚 Cursos (`/api/courses`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Listar cursos | Sim |
| GET | `/{id}` | Buscar por ID | Sim |
| POST | `/` | Criar curso | ADMIN |
| PATCH | `/{id}` | Editar curso | ADMIN |
| DELETE | `/{id}` | Deletar curso | ADMIN |

### 🏫 Turmas (`/api/classes`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/{idCourse}/{numberClass}` | Buscar turma | Sim |
| GET | `/{idCourse}/{numberClass}/{shift}/{pageNumber}/{pageSize}` | Listar com filtros | Sim |
| POST | `/` | Criar turma | Sim |
| PUT | `/{idCourse}/{numberClass}` | Atualizar turma | Sim |
| DELETE | `/{idCourse}/{numberClass}` | Deletar turma | Sim |

### 📝 Matrículas (`/api/class-users`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Listar matrículas | Sim |
| GET | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | Buscar matrícula | Sim |
| POST | `/` | Criar matrícula | ADMIN |
| PATCH | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | Atualizar | ADMIN |
| DELETE | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | Remover | ADMIN |

### 🚪 Salas (`/api/rooms`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Listar salas | Sim |
| GET | `/{id}` | Buscar por ID | Sim |
| POST | `/` | Criar sala | Sim |
| PUT | `/{id}` | Atualizar sala | Sim |
| DELETE | `/{id}` | Deletar sala | Sim |

---

## 🔒 Segurança

### 🎫 Autenticação JWT

Todas as requisições a endpoints protegidos devem incluir o token JWT no header:

```
Authorization: Bearer {token}
```

Tokens expiram após 24 horas (configurável via `JWT_TOKEN_VALIDITY`).

### 👥 Perfis de Usuário

| Perfil | Permissões |
|--------|------------|
| **USER** | Visualização e edição dos próprios dados |
| **ADMIN** | Gerenciamento completo de usuários, cursos e turmas |

### 🎓 Tipos de Usuário em Turmas

| Tipo | Descrição |
|------|-----------|
| ADMINISTRATOR | Administrador institucional |
| SECRETARY | Secretaria acadêmica |
| TEACHER | Professor da turma |
| REPRESENTATIVE | Representante de turma |
| STUDENT | Aluno matriculado |

### ✅ Validações

- **Email**: Formato válido, máximo 150 caracteres, único no sistema
- **Senha**: Mínimo 8 caracteres (1 maiúscula, 1 minúscula, 1 número, 1 especial)
- **Nome**: Obrigatório, máximo 30 caracteres

---

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Gerar relatório de cobertura JaCoCo
mvn jacoco:report
```

O relatório de cobertura é gerado em `target/site/jacoco/index.html`.

### 📊 Comandos Úteis

| Comando | Descrição |
|---------|-----------|
| `mvn test` | Executa todos os 152 testes |
| `mvn jacoco:report` | Gera relatório HTML de cobertura |
| `mvn test -Dtest=*UseCaseTest` | Executa apenas testes de Use Cases |
| `mvn test -Dtest=*IntegrationTest` | Executa testes de integração |

---

## ⚙️ CI/CD

### 🔄 GitHub Actions

A Synco API utiliza **GitHub Actions** para automação de processos de integração contínua.

```
┌─────────────┐    ┌──────────────┐    ┌───────────────┐    ┌──────────────┐
│   Commit    │ → │   Checkout   │ → │  Setup JDK 21 │ → │  Maven Test  │
└─────────────┘    └──────────────┘    └───────────────┘    └──────────────┘
                                                                    ↓
                                              ┌──────────────────────────────┐
                                              │   ✅ Relatório de Cobertura  │
                                              └──────────────────────────────┘
```

**Triggers:**
- 📤 Push na branch `main`
- 🔀 Pull Requests para a branch `main`

### 🐳 Containerização

| Feature | Descrição |
|---------|-----------|
| **Docker** | Multi-stage build otimizado para produção |
| **Docker Compose** | Orquestração com MySQL |
| **Health Checks** | Monitoramento de disponibilidade integrado |

---

## 🤝 Como Contribuir

### 📝 Guia de Contribuição

1. **Fork** o repositório
2. **Clone** seu fork:
   ```bash
   git clone https://github.com/<seu-usuario>/SyncoApi.git
   ```
3. **Crie uma branch** descritiva:
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```
4. **Desenvolva** seguindo os padrões do projeto
5. **Execute os testes** antes de commitar:
   ```bash
   mvn test
   ```
6. **Faça commit** seguindo o padrão Conventional Commits:
   ```bash
   git commit -m "feat: adiciona nova funcionalidade"
   ```
7. **Push** para seu fork:
   ```bash
   git push origin feature/nova-funcionalidade
   ```
8. Abra um **Pull Request** referenciando a issue correspondente

### 📏 Padrões de Código

- **Linguagem**: Java 21 com features modernas
- **Arquitetura**: Clean Architecture (respeite a separação de camadas)
- **Nomenclatura**: CamelCase para classes/métodos, lowercase para pacotes (ex: `com.api.synco.module`)
- **Testes**: Mínimo de testes unitários para novos use cases
- **Documentação**: Javadoc para classes e métodos públicos

### 💬 Padrões de Commit

Utilizamos [Conventional Commits](https://www.conventionalcommits.org/):

| Tipo | Descrição |
|------|-----------|
| `feat` | Nova funcionalidade |
| `fix` | Correção de bug |
| `docs` | Documentação |
| `style` | Formatação (sem alteração de código) |
| `refactor` | Refatoração |
| `test` | Testes |
| `chore` | Tarefas de manutenção |

### ✅ Regras de Pull Request

- Referencie a issue relacionada
- Descreva claramente as alterações
- Garanta que todos os testes passem
- Aguarde aprovação de pelo menos um reviewer

---

## 📚 Referências Rápidas

| Recurso | Link |
|:-------:|------|
| 📄 **Documentação OpenAPI** | [docs/openapi.yaml](docs/openapi.yaml) |
| 📖 **Exemplos de Uso** | [docs/EXEMPLOS.md](docs/EXEMPLOS.md) |
| 🔧 **Swagger UI (Local)** | http://localhost:8080/swagger-ui/index.html |
| 📊 **Relatório de Cobertura** | `target/site/jacoco/index.html` |
| 🔄 **CI Builds** | [GitHub Actions](https://github.com/Luca5Eckert/SyncoApi/actions) |
| 📦 **Repositório** | [GitHub - SyncoApi](https://github.com/Luca5Eckert/SyncoApi) |

---

## 📞 Contato

### 👨‍💻 Mantenedores

| Nome | GitHub | Papel |
|------|--------|-------|
| Luca Eckert | [@Luca5Eckert](https://github.com/Luca5Eckert) | Desenvolvedor Principal |

### 💬 Canais de Suporte

- **Issues**: [Abrir Issue](https://github.com/Luca5Eckert/SyncoApi/issues) — Para bugs, dúvidas ou sugestões
- **Discussões**: Utilize as Issues para discussões técnicas
- **Pull Requests**: Contribuições são bem-vindas seguindo o guia acima

---

## 📜 Licença

Este projeto está sob a licença **MIT**. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">

**Feito com ❤️ por [Luca Eckert](https://github.com/Luca5Eckert)**

[![GitHub](https://img.shields.io/badge/GitHub-Luca5Eckert-181717?style=for-the-badge&logo=github)](https://github.com/Luca5Eckert)

</div>
