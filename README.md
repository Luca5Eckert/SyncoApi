# Synco API

API REST para gestão acadêmica, desenvolvida para centralizar a comunicação institucional e o gerenciamento de dados em ambientes educacionais.

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
| **Build** | Maven | 3.6+ |
| **Containerização** | Docker | Multi-stage |

## Arquitetura

O projeto adota Clean Architecture com separação clara de responsabilidades:

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

Cada módulo segue a estrutura:
- **application/**: Controllers e DTOs (Request/Response)
- **domain/**: Entidades, serviços, use cases e regras de negócio

### Padrões Aplicados

- **Clean Architecture**: Independência entre camadas
- **Repository Pattern**: Abstração de persistência
- **Use Case Pattern**: Encapsulamento de lógica de negócio
- **Value Objects**: Objetos imutáveis para validação (Email, Name)
- **Policy Pattern**: Políticas de permissão desacopladas

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

## Endpoints da API

### Autenticação

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/api/auth/register` | Registrar usuário | Não |
| POST | `/api/auth/login` | Autenticar usuário | Não |
| PATCH | `/api/auth/password` | Alterar senha | Sim |

### Usuários

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/users` | Listar usuários | Sim |
| GET | `/api/users/{id}` | Buscar por ID | Sim |
| POST | `/api/users` | Criar usuário | ADMIN |
| PATCH | `/api/users` | Editar usuário | Sim |
| DELETE | `/api/users` | Deletar usuário | ADMIN |

### Cursos

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/courses` | Listar cursos | Sim |
| GET | `/api/courses/{id}` | Buscar por ID | Sim |
| POST | `/api/courses` | Criar curso | ADMIN |
| PATCH | `/api/courses/{id}` | Editar curso | ADMIN |
| DELETE | `/api/courses/{id}` | Deletar curso | ADMIN |

### Turmas

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/classes/{idCourse}/{numberClass}` | Buscar turma | Sim |
| GET | `/api/classes/{idCourse}/{numberClass}/{shift}/{pageNumber}/{pageSize}` | Listar turmas com filtros | Sim |
| POST | `/api/classes` | Criar turma | Sim |
| PUT | `/api/classes/{idCourse}/{numberClass}` | Atualizar turma | Sim |
| DELETE | `/api/classes/{idCourse}/{numberClass}` | Deletar turma | Sim |

### Matrículas (Class-Users)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/class-users` | Listar matrículas | Sim |
| GET | `/api/class-users/courses/{courseId}/classes/{classNumber}/users/{userId}` | Buscar matrícula | Sim |
| POST | `/api/class-users` | Criar matrícula | ADMIN |
| PATCH | `/api/class-users/courses/{courseId}/classes/{classNumber}/users/{userId}` | Atualizar matrícula | ADMIN |
| DELETE | `/api/class-users/courses/{courseId}/classes/{classNumber}/users/{userId}` | Remover matrícula | ADMIN |

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

## Roadmap

### Em Desenvolvimento

- Implementação completa do módulo de Salas
- Implementação do módulo de Períodos
- Sistema de Verificação de Salas
- Módulo de Controle de Frequência

### Próximas Funcionalidades

- **Mural de Avisos**: Comunicados oficiais da coordenação
- **Rate Limiting**: Proteção contra ataques de força bruta
- **Caching**: Redis para otimização de consultas frequentes
- **Auditoria**: Logging de ações sensíveis
- **Métricas**: Integração com Prometheus/Micrometer

## Testes

```bash
# Executar testes
mvn test

# Gerar relatório de cobertura
mvn jacoco:report
```

## Documentação

### Atualização da Documentação OpenAPI

As anotações nos controllers geram automaticamente a documentação via SpringDoc:

- `@Tag`: Agrupamento de endpoints
- `@Operation`: Descrição de operações
- `@ApiResponses`: Documentação de respostas
- `@Parameter`: Documentação de parâmetros

Documentação local disponível em `docs/openapi.yaml`.

## Contribuição

1. Crie uma branch descritiva:
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```

2. Faça commit das alterações:
   ```bash
   git commit -m "feat: adiciona nova funcionalidade"
   ```

3. Abra um Pull Request referenciando a issue correspondente.

## Licença

Este projeto está sob a licença MIT.

## Contato

- **GitHub**: [@Luca5Eckert](https://github.com/Luca5Eckert)
- **Repositório**: [SyncoApi](https://github.com/Luca5Eckert/SyncoApi)

