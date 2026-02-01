# Synco API

API REST para gestão acadêmica, desenvolvida para centralizar a comunicação institucional e o gerenciamento de dados em ambientes educacionais.

---

## Apresentação do Projeto

**Synco API** é o backend de uma plataforma de gestão acadêmica projetada para solucionar problemas de fragmentação na comunicação entre coordenação, professores e alunos. A API oferece uma **fonte única e confiável de informações** (*Single Source of Truth*), eliminando a dispersão de dados em canais não oficiais.

### Diferenciais

- **Arquitetura Clean**: Separação clara de responsabilidades, facilitando manutenção e evolução.
- **Segurança Robusta**: Autenticação JWT com políticas de permissão granulares.
- **Documentação Completa**: Swagger/OpenAPI integrado para fácil integração.
- **Infraestrutura Moderna**: Docker com multi-stage build e CI/CD automatizado.

### Principais Funcionalidades

| Funcionalidade | Descrição |
|----------------|-----------|
| **Autenticação** | Registro, login e redefinição de senha com JWT |
| **Gestão de Usuários** | CRUD completo com controle de permissões |
| **Gestão de Cursos** | Gerenciamento de cursos acadêmicos |
| **Gestão de Turmas** | Organização de turmas por curso com turnos |
| **Matrículas** | Associação de usuários às turmas |
| **Gestão de Salas** | Cadastro e gerenciamento de ambientes físicos |

---

## Métricas de Sucesso

| Métrica | Valor | Observação |
|---------|-------|------------|
| **Cobertura de testes** | Em expansão | Ferramenta: JaCoCo |
| **Arquivos de teste** | 35 | Testes unitários e de integração |
| **Endpoints ativos** | 28 | Distribuídos em 8 controllers |
| **Builds CI (sucesso)** | ~87% | 13 de 15 execuções recentes |
| **Ferramenta CI** | GitHub Actions | Workflow automatizado |
| **Linguagem/Framework** | Java 21 + Spring Boot 3.3.0 | Stack moderna e atualizada |

---

## Integração e CI/CD

### Integração Contínua (CI)

A Synco API utiliza **GitHub Actions** para automação de processos de integração contínua.

**Workflow Principal (`ci.yml`):**

| Etapa | Descrição |
|-------|-----------|
| **Checkout** | Clona o repositório |
| **Setup JDK 21** | Configura ambiente Java Temurin 21 |
| **Cache Maven** | Otimiza builds com cache de dependências |
| **Run Tests** | Executa testes automatizados via Maven |

**Triggers:**
- Push na branch `main`
- Pull Requests para a branch `main`

### Pipeline de Qualidade

```
┌─────────────┐    ┌──────────────┐    ┌───────────────┐
│   Commit    │ → │  CI Build    │ → │ Testes Auto.  │
└─────────────┘    └──────────────┘    └───────────────┘
                                              ↓
                          ┌──────────────────────────────┐
                          │   Relatório JaCoCo Coverage  │
                          └──────────────────────────────┘
```

### Containerização

- **Docker**: Multi-stage build otimizado para produção
- **Docker Compose**: Orquestração com MySQL
- **Health Checks**: Monitoramento de disponibilidade integrado

---

## Melhorias Recentes

### Implementadas

| Melhoria | Status | Descrição |
|----------|--------|-----------|
| ✅ **CI/CD com GitHub Actions** | Concluído | Automação de testes em cada PR e push |
| ✅ **Cobertura de Testes** | Em expansão | 35 arquivos de testes implementados |
| ✅ **Documentação OpenAPI** | Concluído | Swagger UI integrado com anotações completas |
| ✅ **Cache de Build Maven** | Concluído | Otimização de tempo de CI |
| ✅ **Clean Architecture** | Concluído | Separação clara entre camadas |
| ✅ **Validação de Senhas** | Concluído | Integração com biblioteca Passay |
| ✅ **Containerização Docker** | Concluído | Multi-stage build otimizado |

### Em Andamento / Planejadas

| Melhoria | Prioridade | Descrição |
|----------|------------|-----------|
| 🔄 **Módulo de Salas** | Alta | Cadastro e gestão completa de ambientes |
| 🔄 **Módulo de Períodos** | Alta | Definição de horários (manhã, tarde, noite) |
| 🔄 **Verificação de Salas** | Média | Feedback sobre condições dos ambientes |
| 🔄 **Controle de Frequência** | Média | Registro de presença de alunos |
| 📋 **Rate Limiting** | Planejado | Proteção contra ataques de força bruta |
| 📋 **Caching Redis** | Planejado | Otimização de consultas frequentes |
| 📋 **Auditoria** | Planejado | Logging de ações sensíveis |
| 📋 **Métricas Prometheus** | Planejado | Integração com Micrometer |

---

## Stack Tecnológica

| Categoria | Tecnologia | Versão |
|-----------|------------|--------|
| **Linguagem** | Java | 21 |
| **Framework** | Spring Boot | 3.3.0 |
| **Segurança** | Spring Security + JWT | jjwt 0.11.5 |
| **Persistência** | Spring Data JPA | - |
| **Banco de Dados (Dev)** | H2 Database | Em memória |
| **Banco de Dados (Prod)** | MySQL | 8.0 |
| **Validação** | Bean Validation + Passay | 1.6.6 |
| **Documentação** | SpringDoc OpenAPI | 2.5.0 |
| **Cobertura** | JaCoCo | 0.8.12 |
| **Build** | Maven | 3.6+ |
| **Containerização** | Docker | Multi-stage |
| **CI/CD** | GitHub Actions | - |

---

## Arquitetura

O projeto adota **Clean Architecture** com separação clara de responsabilidades:

```
src/main/java/com/api/synco/
├── core/                            # Interfaces centrais
├── infrastructure/                  # Componentes transversais
│   ├── api/                         # Respostas padronizadas
│   ├── config/                      # Configurações (OpenAPI, CORS)
│   ├── exception/                   # Tratamento global de exceções
│   ├── persistence/                 # Implementações de repositórios
│   ├── security/                    # JWT e configuração de segurança
│   └── service/                     # Serviços de infraestrutura
└── module/                          # Módulos de domínio
    ├── authentication/              # Autenticação (registro, login, senha)
    ├── user/                        # Gestão de usuários
    ├── course/                      # Gestão de cursos
    ├── class_entity/                # Gestão de turmas
    ├── class_user/                  # Matrículas (usuário-turma)
    ├── period/                      # Períodos (manhã, tarde, noite)
    ├── room/                        # Gestão de salas
    ├── room_verification/           # Verificação de ambientes
    ├── attendance_user/             # Controle de frequência
    └── permission/                  # Políticas de permissão
```

### Padrões Aplicados

- **Clean Architecture**: Independência entre camadas
- **Repository Pattern**: Abstração de persistência
- **Use Case Pattern**: Encapsulamento de lógica de negócio
- **Value Objects**: Objetos imutáveis para validação (Email, Name)
- **Policy Pattern**: Políticas de permissão desacopladas

---

## Execução

### Pré-requisitos

- Java 21+
- Maven 3.6+
- Docker e Docker Compose (para execução containerizada)

### Variáveis de Ambiente

```bash
export DB_USERNAME=sa
export DB_PASSWORD=
export JWT_SECRET=SuaChaveSecretaDeNoMinimo256BitsParaJWT
```

### Execução Local

```bash
# Clonar repositório
git clone https://github.com/Luca5Eckert/SyncoApi.git
cd SyncoApi

# Compilar
mvn clean package

# Executar
mvn spring-boot:run
```

### Execução com Docker

```bash
# Construir e executar
docker-compose up -d

# Verificar logs
docker-compose logs -f syncoapi
```

### Acessos

| Recurso | URL |
|---------|-----|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| H2 Console (Dev) | http://localhost:8080/h2-console |

---

## Endpoints da API

### Autenticação (`/api/auth`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/register` | Registrar usuário | Não |
| POST | `/login` | Autenticar usuário | Não |
| PATCH | `/password` | Alterar senha | Sim |

### Usuários (`/api/users`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Listar usuários | Sim |
| GET | `/{id}` | Buscar por ID | Sim |
| POST | `/` | Criar usuário | ADMIN |
| PATCH | `/` | Editar usuário | Sim |
| DELETE | `/` | Deletar usuário | ADMIN |

### Cursos (`/api/courses`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Listar cursos | Sim |
| GET | `/{id}` | Buscar por ID | Sim |
| POST | `/` | Criar curso | ADMIN |
| PATCH | `/{id}` | Editar curso | ADMIN |
| DELETE | `/{id}` | Deletar curso | ADMIN |

### Turmas (`/api/classes`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/{idCourse}/{numberClass}` | Buscar turma | Sim |
| GET | `/{idCourse}/{numberClass}/{shift}/{pageNumber}/{pageSize}` | Listar com filtros | Sim |
| POST | `/` | Criar turma | Sim |
| PUT | `/{idCourse}/{numberClass}` | Atualizar turma | Sim |
| DELETE | `/{idCourse}/{numberClass}` | Deletar turma | Sim |

### Matrículas (`/api/class-users`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Listar matrículas | Sim |
| GET | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | Buscar matrícula | Sim |
| POST | `/` | Criar matrícula | ADMIN |
| PATCH | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | Atualizar | ADMIN |
| DELETE | `/courses/{courseId}/classes/{classNumber}/users/{userId}` | Remover | ADMIN |

### Salas (`/api/rooms`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Listar salas | Sim |
| GET | `/{id}` | Buscar por ID | Sim |
| POST | `/` | Criar sala | Sim |
| PUT | `/{id}` | Atualizar sala | Sim |
| DELETE | `/{id}` | Deletar sala | Sim |

---

## Segurança

### Autenticação JWT

Todas as requisições a endpoints protegidos devem incluir o token JWT no header:

```
Authorization: Bearer {token}
```

Tokens expiram após 24 horas (configurável via `JWT_TOKEN_VALIDITY`).

### Perfis de Usuário

| Perfil | Permissões |
|--------|------------|
| **USER** | Visualização e edição dos próprios dados |
| **ADMIN** | Gerenciamento completo de usuários, cursos e turmas |

### Tipos de Usuário em Turmas

| Tipo | Descrição |
|------|-----------|
| ADMINISTRATOR | Administrador institucional |
| SECRETARY | Secretaria acadêmica |
| TEACHER | Professor da turma |
| REPRESENTATIVE | Representante de turma |
| STUDENT | Aluno matriculado |

### Validações

- **Email**: Formato válido, máximo 150 caracteres, único no sistema
- **Senha**: Mínimo 8 caracteres (1 maiúscula, 1 minúscula, 1 número, 1 especial)
- **Nome**: Obrigatório, máximo 30 caracteres

---

## Testes

```bash
# Executar testes
mvn test

# Gerar relatório de cobertura
mvn jacoco:report
```

O relatório de cobertura é gerado em `target/site/jacoco/index.html`.

---

## Como Contribuir

### Guia de Contribuição

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

### Padrões de Código

- **Linguagem**: Java 21 com features modernas
- **Arquitetura**: Clean Architecture (respeite a separação de camadas)
- **Nomenclatura**: CamelCase para classes/métodos, lowercase para pacotes (ex: `com.api.synco.module`)
- **Testes**: Mínimo de testes unitários para novos use cases
- **Documentação**: Javadoc para classes e métodos públicos

### Padrões de Commit

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

### Regras de Pull Request

- Referencie a issue relacionada
- Descreva claramente as alterações
- Garanta que todos os testes passem
- Aguarde aprovação de pelo menos um reviewer

---

## Referências Rápidas

| Recurso | Link |
|---------|------|
| 📄 **Documentação OpenAPI** | [docs/openapi.yaml](docs/openapi.yaml) |
| 📖 **Exemplos de Uso** | [docs/EXEMPLOS.md](docs/EXEMPLOS.md) |
| 🔧 **Swagger UI (Local)** | http://localhost:8080/swagger-ui/index.html |
| 📊 **Relatório de Cobertura** | `target/site/jacoco/index.html` (após `mvn jacoco:report`) |
| 🔄 **CI Builds** | [GitHub Actions](https://github.com/Luca5Eckert/SyncoApi/actions) |
| 📦 **Repositório** | [GitHub - SyncoApi](https://github.com/Luca5Eckert/SyncoApi) |

---

## Contato e Suporte

### Mantenedores

| Nome | GitHub | Papel |
|------|--------|-------|
| Luca Eckert | [@Luca5Eckert](https://github.com/Luca5Eckert) | Desenvolvedor Principal |

### Canais de Suporte

- **Issues**: [Abrir Issue](https://github.com/Luca5Eckert/SyncoApi/issues) — Para bugs, dúvidas ou sugestões
- **Discussões**: Utilize as Issues para discussões técnicas
- **Pull Requests**: Contribuições são bem-vindas seguindo o guia acima

---

## Licença

Este projeto está sob a licença **MIT**. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.
