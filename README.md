# Synco Api

O Synco Api é uma API REST que atua como o backend de uma plataforma de gestão acadêmica, focada em fortalecer a comunicação e centralizar dados em ambientes de aprendizagem.

## 🎯 O Problema
A comunicação entre coordenação e alunos é frequentemente fragmentada. Avisos se perdem em grupos de WhatsApp, e-mails não são lidos e informações cruciais (como horários de laboratório ou controle de faltas) não possuem um local oficial. Isso gera ruído e insegurança para os estudantes.

## 💡 A Solução
Esta API cria um ponto central de informação (Single Source of Truth) onde todas as interações são registradas e disponibilizadas de forma organizada. Ela supre a necessidade de um canal de comunicação robusto e confiável.

## 🚀 Funcionalidades Planejadas
* **Gestão de Faltas:** Permite ao professor registrar e ao aluno consultar suas ausências.
* **Feedback de Ambiente:** Um canal para o representante de turma registrar a qualidade do ambiente de aula.
* **Mural de Avisos:** Um ponto central para comunicados oficiais da coordenação.
* **Repositório de Informações:** Local para consulta de horários, intervalos e calendários.

## 📋 Visão Geral

Esta API fornece endpoints para:
- 🔐 Autenticação e registro de usuários
- 👥 Gerenciamento completo de usuários (CRUD)
- 📚 Gerenciamento de cursos (CRUD)
- 🔒 Controle de acesso baseado em roles (USER, ADMIN)
- 🔑 Autenticação via JWT (JSON Web Token)

## 🛠️ Tecnologias e Dependências

### Stack Principal
- **Java**: 21
- **Spring Boot**: 3.3.0
- **Spring Security**: Autenticação e autorização
- **Spring Data JPA**: Persistência de dados
- **H2 Database**: Banco de dados em memória (desenvolvimento)
- **MySQL**: Suporte para banco de dados em produção
- **Lombok**: Redução de código boilerplate
- **Bean Validation**: Validação de dados

### Bibliotecas Adicionais
- **JWT (jjwt)**: 0.11.5 - Geração e validação de tokens
- **Commons Validator**: 1.8.0 - Validação de email
- **Passay**: 1.6.6 - Validação de senha
- **SpringDoc OpenAPI**: 2.5.0 - Documentação Swagger/OpenAPI

### Build
- **Maven**: Gerenciamento de dependências e build

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura em camadas com separação de responsabilidades (Clean Architecture):

```
src/main/java/com/api/synco/
├── core/                            # Interfaces centrais
├── infrastructure/                  # Infraestrutura transversal
│   ├── api/                         # Respostas padronizadas da API
│   ├── config/                      # Configurações (OpenAPI, etc)
│   ├── exception/                   # Tratamento global de exceções
│   ├── persistence/                 # Implementações de repositórios
│   ├── security/                    # Configuração de segurança e JWT
│   └── service/                     # Serviços de infraestrutura
└── module/                          # Módulos de domínio
    ├── authentication/              # Módulo de autenticação
    │   ├── application/             # Controllers e DTOs
    │   │   ├── controller/          # REST Controllers
    │   │   └── dto/                 # Request/Response objects
    │   └── domain/                  # Lógica de negócio
    │       ├── exception/           # Exceções do domínio
    │       ├── mapper/              # Mappers de entidades
    │       ├── service/             # Services
    │       └── use_case/            # Casos de uso
    ├── course/                      # Módulo de cursos
    │   ├── application/             # Controllers e DTOs
    │   └── domain/                  # Lógica de negócio
    ├── permission/                  # Módulo de permissões
    │   └── domain/                  # Políticas de permissão
    └── user/                        # Módulo de usuários
        ├── application/             # Controllers e DTOs
        └── domain/                  # Lógica de negócio
            ├── enumerator/          # Enums (RoleUser)
            ├── exception/           # Exceções do domínio
            ├── filter/              # Filtros de busca
            ├── mapper/              # Mappers de entidades
            ├── port/                # Interfaces (Repository)
            ├── service/             # Services
            ├── use_case/            # Casos de uso
            ├── validator/           # Validadores customizados
            └── vo/                  # Value Objects (Email, Name)
```

### Padrões Utilizados
- **Clean Architecture**: Separação entre camadas de aplicação, domínio e infraestrutura
- **Repository Pattern**: Abstração da camada de persistência
- **DTO Pattern**: Objetos de transferência de dados
- **Use Case Pattern**: Encapsulamento da lógica de negócio
- **Value Objects**: Objetos de valor imutáveis (Email, Name)
- **Policy Pattern**: Políticas de permissão desacopladas

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+

### Variáveis de Ambiente

Configure as seguintes variáveis antes de executar:

```bash
export DB_USERNAME=sa
export DB_PASSWORD=
export JWT_SECRET=SuaChaveSecretaDeNoMinimo256BitsParaJWT
```

### Instalação e Execução

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/Luca5Eckert/SyncoApi.git
   cd SyncoApi
   ```

2. **Build do projeto**:
   ```bash
   mvn clean package
   ```

3. **Executar a aplicação**:
   ```bash
   mvn spring-boot:run
   ```
   
   Ou execute o JAR gerado:
   ```bash
   java -jar target/syncoapp-0.0.1-SNAPSHOT.jar
   ```

4. **Acessar a aplicação**:
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - H2 Console: http://localhost:8080/h2-console

### Configuração do Banco de Dados

Por padrão, a aplicação usa H2 (em memória) para desenvolvimento:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
```

Para usar MySQL em produção, atualize `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SyncoAppDb
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## 📚 Documentação da API

### Endpoints Principais

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/api/auth/register` | Registrar novo usuário | Não |
| POST | `/api/auth/login` | Autenticar usuário | Não |
| PATCH | `/api/auth/password` | Alterar senha | Sim |
| GET | `/api/users` | Listar usuários | Sim |
| GET | `/api/users/{id}` | Buscar usuário por ID | Sim |
| POST | `/api/users` | Criar usuário | Sim (ADMIN) |
| PATCH | `/api/users` | Editar usuário | Sim |
| DELETE | `/api/users` | Deletar usuário | Sim (ADMIN) |
| GET | `/api/courses` | Listar cursos | Sim |
| GET | `/api/courses/{id}` | Buscar curso por ID | Sim |
| POST | `/api/courses` | Criar curso | Sim (ADMIN) |
| PATCH | `/api/courses/{id}` | Editar curso | Sim (ADMIN) |
| DELETE | `/api/courses/{id}` | Deletar curso | Sim (ADMIN) |

### Swagger/OpenAPI

A documentação interativa está disponível através do Swagger UI:

- **Swagger UI (interface interativa)**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **OpenAPI YAML**: `docs/openapi.yaml` (arquivo local)

### Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 | OK - Requisição bem-sucedida |
| 201 | Created - Recurso criado com sucesso |
| 202 | Accepted - Requisição aceita |
| 400 | Bad Request - Dados inválidos |
| 401 | Unauthorized - Não autenticado |
| 403 | Forbidden - Sem permissão |
| 404 | Not Found - Recurso não encontrado |
| 409 | Conflict - Violação de integridade de dados |

## 🔒 Autenticação e Segurança

### JWT (JSON Web Token)

A API usa JWT para autenticação. Após o login, você recebe um token que deve ser incluído no header `Authorization` de todas as requisições protegidas:

```
Authorization: Bearer {seu_token_jwt}
```

O token expira após 24 horas (configurável via `jwt.token.validity`).

### Roles e Permissões

- **USER**: Pode visualizar informações e editar/deletar apenas seus próprios dados
- **ADMIN**: Pode gerenciar todos os usuários e cursos

### Validações

- **Email**: Deve ser válido e único no sistema (max 150 caracteres)
- **Senha**: Mínimo 8 caracteres, incluindo: 1 maiúscula, 1 minúscula, 1 número, 1 caractere especial
- **Nome**: Obrigatório (max 30 caracteres)

## ⚡ Melhorias Implementadas

### Performance
- ✅ **Índices de banco de dados**: Adicionados índices em colunas frequentemente consultadas (email, role, createAt)
- ✅ **Connection Pool otimizado**: Configuração do HikariCP com parâmetros adequados
- ✅ **Open-in-view desabilitado**: Prevenção de lazy loading não intencional
- ✅ **@Transactional(readOnly=true)**: Otimização em operações de leitura

### Segurança
- ✅ **Validação de entrada no login**: Adicionadas anotações @NotBlank e @Size no DTO de login
- ✅ **Correção de bug de permissão**: Corrigido método canModifyUser() que estava chamando canModifyCourse()
- ✅ **Rotas de segurança atualizadas**: Corrigido o padrão de URLs no SecurityConfig

### Legibilidade e Qualidade de Código
- ✅ **@RestController**: Corrigido UserController que estava usando @Controller
- ✅ **OpenAPI Config**: Atualizado título e descrição para refletir o projeto Synco
- ✅ **Typo corrigido**: Método `delele` renomeado para `delete` no CourseController
- ✅ **@Transactional**: Adicionado em todos os use cases para garantir consistência de dados
- ✅ **Filter chain fix**: Corrigido fluxo do JwtTokenAuthenticationFilter para sempre continuar a chain

## 📋 Plano de Ação para Melhorias Futuras

### Alta Prioridade (Segurança)

1. **Rate Limiting**
   - Implementar limitação de requisições para prevenir ataques de força bruta
   - Sugestão: Usar `bucket4j` ou `resilience4j`
   ```java
   // Exemplo de configuração
   @RateLimiter(name = "login", fallbackMethod = "rateLimitFallback")
   public ResponseEntity<...> login(...) { ... }
   ```

2. **CORS Configuration**
   - Adicionar configuração de CORS para ambientes de produção
   ```java
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration configuration = new CorsConfiguration();
       configuration.setAllowedOrigins(Arrays.asList("https://seu-frontend.com"));
       // ...
   }
   ```

3. **Perfis de Ambiente**
   - Desabilitar H2 Console em produção
   - Criar `application-prod.properties` com configurações seguras

4. **Auditoria de Segurança**
   - Implementar logging de ações sensíveis (login, alteração de senha, deleção)

### Média Prioridade (Performance)

5. **Caching**
   - Implementar cache para consultas frequentes
   - Sugestão: Spring Cache com Redis
   ```java
   @Cacheable("users")
   public UserGetResponse get(long id) { ... }
   ```

6. **Paginação Melhorada**
   - Retornar metadados de paginação (total de páginas, total de itens)
   ```java
   public record PageResponse<T>(
       List<T> content,
       int page,
       int size,
       long totalElements,
       int totalPages
   ) {}
   ```

### Baixa Prioridade (Qualidade)

7. **Internacionalização**
   - Externalizar mensagens de erro para suporte a múltiplos idiomas

8. **Health Checks**
   - Implementar endpoints de health check para monitoramento
   - Usar Spring Actuator

9. **Métricas**
   - Adicionar métricas de performance (Micrometer + Prometheus)

10. **Testes de Integração**
    - Expandir cobertura de testes para controllers

## 🧪 Testes

Para executar os testes:

```bash
mvn test
```

Para gerar relatório de cobertura:
```bash
mvn jacoco:report
```

## 📖 Como Atualizar a Documentação

### Documentação Manual (OpenAPI YAML)

Edite o arquivo `docs/openapi.yaml` manualmente.

### Documentação Gerada (Swagger)

As anotações OpenAPI nos controllers geram automaticamente a documentação. Para atualizar:

1. Adicione/edite anotações nos controllers:
   - `@Tag`: Agrupar endpoints
   - `@Operation`: Descrever operação
   - `@ApiResponses`: Documentar respostas
   - `@Parameter`: Documentar parâmetros

2. Execute a aplicação

3. Acesse http://localhost:8080/v3/api-docs para ver o JSON gerado

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Crie uma branch com nome descritivo:
   ```bash
   git checkout -b feature/minha-feature
   # ou
   git checkout -b docs/atualizar-documentacao
   ```

2. Faça suas alterações e commit:
   ```bash
   git commit -m "feat: adiciona nova funcionalidade"
   ```

3. Abra um Pull Request referenciando a issue:
   ```
   Fixes #25
   ```

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 📞 Contato

- **GitHub**: [@Luca5Eckert](https://github.com/Luca5Eckert)
- **Repositório**: [SyncoApi](https://github.com/Luca5Eckert/SyncoApi)

## 🔗 Links Úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [OpenAPI Specification](https://swagger.io/specification/)

