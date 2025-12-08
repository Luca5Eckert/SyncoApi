# 🚀 Oportunidades de Aprendizado - SyncoApi

Este documento apresenta sugestões de funcionalidades e tecnologias que podem ser implementadas no projeto SyncoApi para expandir seus conhecimentos no ecossistema Spring e no mundo de desenvolvimento profissional.

## 📊 Análise do Projeto Atual

### ✅ Tecnologias Já Implementadas
- ✅ Spring Boot 3.3.0
- ✅ Spring Security com JWT
- ✅ Spring Data JPA
- ✅ Bean Validation
- ✅ Swagger/OpenAPI
- ✅ Clean Architecture
- ✅ Testes Unitários e de Integração
- ✅ H2 Database (dev) + MySQL (prod)
- ✅ Lombok
- ✅ Exception Handling Global

---

## 🎯 Novas Oportunidades de Aprendizado

### 1. 💬 WebSocket - Chat em Tempo Real

**Objetivo**: Aprender comunicação bidirecional em tempo real.

**Caso de Uso**: Sistema de chat para comunicação entre coordenação, professores e alunos da turma.

**Tecnologias**:
```xml
<!-- Adicionar ao pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

**O que você vai aprender**:
- WebSocket vs HTTP tradicional
- STOMP protocol
- Message brokers
- Comunicação assíncrona
- Broadcast de mensagens
- Autenticação em WebSocket

**Exemplo de implementação**:
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("http://localhost:*", "https://yourdomain.com")
                .withSockJS();
    }
}

@Controller
public class ChatController {
    
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
```

**Módulo sugerido**: `module/chat/`

---

### 2. 📧 Spring Mail - Sistema de Notificações

**Objetivo**: Aprender integração com serviços de email.

**Caso de Uso**: Envio de notificações por email quando há novos avisos, lembretes de faltas, ou confirmação de cadastro.

**Tecnologias**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

**O que você vai aprender**:
- JavaMailSender
- Templates de email com Thymeleaf
- Envio assíncrono de emails
- SMTP configuration
- Anexos e HTML emails
- Retry mechanisms

**Exemplo de implementação**:
```java
@Service
public class EmailService {
    
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }
    
    @Async
    public void sendWelcomeEmail(String to, String name) {
        Context context = new Context();
        context.setVariable("name", name);
        
        String htmlContent = templateEngine.process("welcome-email", context);
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(to);
        helper.setSubject("Bem-vindo ao SyncoApp!");
        helper.setText(htmlContent, true);
        
        mailSender.send(message);
    }
}
```

**Módulo sugerido**: `module/notification/`

---

### 3. ⚡ Redis Cache - Performance e Escalabilidade

**Objetivo**: Aprender cache distribuído e otimização de performance.

**Caso de Uso**: Cache de consultas frequentes (lista de cursos, dados de usuário, etc.).

**Tecnologias**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

**O que você vai aprender**:
- Cache abstraction do Spring
- Redis como cache store
- TTL (Time To Live) strategies
- Cache eviction policies
- Distributed caching
- Cache-aside pattern

**Exemplo de implementação**:
```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .disableCachingNullValues();
            
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build();
    }
}

@Service
public class CourseService {
    
    @Cacheable(value = "courses", key = "#id")
    public CourseGetResponse getCourse(long id) {
        // Consulta ao banco será feita apenas na primeira vez
        return courseRepository.findById(id)...;
    }
    
    @CacheEvict(value = "courses", key = "#id")
    public void updateCourse(long id, CourseUpdateRequest request) {
        // Invalida o cache ao atualizar
    }
}
```

**Configuração**:
```properties
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

---

### 4. 📊 Spring Actuator + Prometheus + Grafana - Monitoramento

**Objetivo**: Aprender observabilidade e monitoramento de aplicações.

**Caso de Uso**: Monitorar saúde da aplicação, métricas de uso, performance.

**Tecnologias**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

**O que você vai aprender**:
- Health checks
- Métricas customizadas
- Prometheus scraping
- Grafana dashboards
- Application insights
- Performance monitoring

**Configuração**:
```properties
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=always
management.metrics.export.prometheus.enabled=true
```

**Exemplo de métrica customizada**:
```java
@Service
public class UserService {
    
    private final Counter userCreatedCounter;
    private final Timer loginTimer;
    
    public UserService(MeterRegistry registry) {
        this.userCreatedCounter = Counter.builder("users.created")
            .description("Total de usuários criados")
            .register(registry);
            
        this.loginTimer = Timer.builder("user.login.duration")
            .description("Tempo de login do usuário")
            .register(registry);
    }
    
    public void createUser(UserCreateRequest request) {
        // lógica...
        userCreatedCounter.increment();
    }
    
    public LoginResponse login(LoginRequest request) {
        return loginTimer.record(() -> {
            // lógica de login
            return authenticate(request);
        });
    }
}
```

**Endpoints disponíveis**:
- `/actuator/health` - Status da aplicação
- `/actuator/metrics` - Métricas disponíveis
- `/actuator/prometheus` - Formato Prometheus

---

### 5. 🔄 Apache Kafka - Event Streaming

**Objetivo**: Aprender arquitetura orientada a eventos e mensageria.

**Caso de Uso**: Sistema de eventos para auditoria (login, alterações, etc.) e notificações assíncronas.

**Tecnologias**:
```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

**O que você vai aprender**:
- Event-driven architecture
- Message brokers
- Producer e Consumer
- Event sourcing
- CQRS pattern
- Async processing

**Exemplo de implementação**:
```java
// Producer
@Service
public class EventProducer {
    
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
    
    public EventProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void publishUserCreated(UserEntity user) {
        UserEvent event = new UserEvent(
            user.getId(),
            "USER_CREATED",
            LocalDateTime.now()
        );
        kafkaTemplate.send("user-events", event);
    }
}

// Consumer
@Service
public class NotificationConsumer {
    
    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void handleUserEvent(UserEvent event) {
        if (event.getType().equals("USER_CREATED")) {
            // Enviar email de boas-vindas
            emailService.sendWelcomeEmail(event.getEmail());
        }
    }
}
```

**Módulo sugerido**: `module/event/`

---

### 6. 🔐 OAuth2 + Social Login

**Objetivo**: Aprender autenticação com provedores externos.

**Caso de Uso**: Login com Google, GitHub, Microsoft (Azure AD) para facilitar acesso dos alunos.

**Tecnologias**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

**O que você vai aprender**:
- OAuth2 flow
- OpenID Connect
- JWT tokens com provedores externos
- Social authentication
- Multi-tenancy
- Authorization servers

**Exemplo de configuração**:
```java
@Configuration
public class OAuth2Config {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .successHandler(customSuccessHandler())
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder()))
            );
        return http.build();
    }
}
```

```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_SECRET
spring.security.oauth2.client.registration.google.scope=profile,email
```

---

### 7. 📤 Upload de Arquivos + AWS S3

**Objetivo**: Aprender gerenciamento de arquivos e integração com cloud storage.

**Caso de Uso**: Upload de materiais didáticos, fotos de perfil, comprovantes de presença.

**Tecnologias**:
```xml
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-s3</artifactId>
    <version>1.12.529</version>
</dependency>
```

**O que você vai aprender**:
- MultipartFile handling
- Stream processing
- AWS SDK
- Pre-signed URLs
- File validation
- Cloud storage integration

**Exemplo de implementação**:
```java
@Service
public class FileStorageService {
    
    private final AmazonS3 s3Client;
    
    @Value("${aws.s3.bucket}")
    private String bucketName;
    
    public String uploadFile(MultipartFile file) {
        String fileName = generateFileName(file);
        
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            
            s3Client.putObject(new PutObjectRequest(
                bucketName,
                fileName,
                file.getInputStream(),
                metadata
            ));
            
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new FileStorageException("Erro ao fazer upload", e);
        }
    }
    
    public String generatePresignedUrl(String fileName) {
        Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1 hora
        return s3Client.generatePresignedUrl(bucketName, fileName, expiration).toString();
    }
}

@RestController
@RequestMapping("/api/files")
public class FileController {
    
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {
        
        // Validações
        if (file.isEmpty()) {
            throw new BadRequestException("Arquivo vazio");
        }
        
        if (file.getSize() > 10 * 1024 * 1024) { // 10MB
            throw new BadRequestException("Arquivo muito grande");
        }
        
        String url = fileStorageService.uploadFile(file);
        return ResponseEntity.ok(new FileUploadResponse(url));
    }
}
```

**Módulo sugerido**: `module/file/`

---

### 8. ⏰ Spring Scheduler - Tarefas Agendadas

**Objetivo**: Aprender agendamento de tarefas automatizadas.

**Caso de Uso**: Limpeza de tokens expirados, envio de relatórios periódicos, backup automático.

**Tecnologias**: Spring Boot (built-in)

**O que você vai aprender**:
- Cron expressions
- Fixed delay vs fixed rate
- Async scheduling
- Distributed scheduling
- Task executors

**Exemplo de implementação**:
```java
@Configuration
@EnableScheduling
public class SchedulingConfig {
}

@Component
public class ScheduledTasks {
    
    private final TokenRepository tokenRepository;
    
    // Executar todos os dias à meia-noite
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanExpiredTokens() {
        log.info("Iniciando limpeza de tokens expirados...");
        tokenRepository.deleteExpiredTokens(LocalDateTime.now());
        log.info("Limpeza concluída");
    }
    
    // Executar a cada 15 minutos
    @Scheduled(fixedRate = 900000)
    public void generateMetricsReport() {
        log.info("Gerando relatório de métricas...");
        metricsService.generateReport();
    }
    
    // Executar 1 hora após o início da aplicação, depois a cada 24 horas
    @Scheduled(initialDelay = 3600000, fixedDelay = 86400000)
    public void dailyBackup() {
        log.info("Iniciando backup diário...");
        backupService.performBackup();
    }
}
```

**Cron Expression Guide**:
```
┌───────────── segundos (0-59)
│ ┌───────────── minuto (0-59)
│ │ ┌───────────── hora (0-23)
│ │ │ ┌───────────── dia do mês (1-31)
│ │ │ │ ┌───────────── mês (1-12)
│ │ │ │ │ ┌───────────── dia da semana (0-6)
│ │ │ │ │ │
* * * * * *
```

---

### 9. 🔍 Elasticsearch - Busca Avançada

**Objetivo**: Aprender search engines e full-text search.

**Caso de Uso**: Busca avançada de cursos, usuários, avisos com filtros e relevância.

**Tecnologias**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

**O que você vai aprender**:
- Full-text search
- Indexing strategies
- Query DSL
- Aggregations
- Fuzzy search
- Search relevance

**Exemplo de implementação**:
```java
@Document(indexName = "courses")
public class CourseDocument {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Text, analyzer = "brazilian")
    private String name;
    
    @Field(type = FieldType.Text, analyzer = "brazilian")
    private String description;
    
    @Field(type = FieldType.Keyword)
    private String courseCode;
    
    @Field(type = FieldType.Integer)
    private Integer workload;
}

public interface CourseSearchRepository extends ElasticsearchRepository<CourseDocument, String> {
    
    List<CourseDocument> findByNameContaining(String name);
    
    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}]}}")
    List<CourseDocument> searchByName(String name);
}

@Service
public class CourseSearchService {
    
    public List<CourseDocument> searchCourses(String query) {
        return elasticsearchTemplate.search(
            Query.builder()
                .multiMatch(m -> m
                    .query(query)
                    .fields("name^3", "description", "courseCode^2")
                    .fuzziness("AUTO")
                )
                .build(),
            CourseDocument.class
        );
    }
}
```

**Módulo sugerido**: `module/search/`

---

### 10. 🔄 Spring Async - Processamento Assíncrono

**Objetivo**: Aprender processamento assíncrono e threads.

**Caso de Uso**: Geração de relatórios pesados, processamento em batch de notas.

**Tecnologias**: Spring Boot (built-in)

**O que você vai aprender**:
- @Async annotation
- CompletableFuture
- Thread pools
- Exception handling em async
- Callback mechanisms

**Exemplo de implementação**:
```java
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }
}

@Service
public class ReportService {
    
    @Async
    public CompletableFuture<Report> generateAttendanceReport(Long classId) {
        log.info("Gerando relatório de presença para turma {}...", classId);
        
        // Processamento pesado
        List<Attendance> attendances = attendanceRepository.findByClassId(classId);
        Report report = processAttendances(attendances);
        
        log.info("Relatório concluído para turma {}", classId);
        return CompletableFuture.completedFuture(report);
    }
    
    @Async
    public void sendBulkNotifications(List<Long> userIds) {
        log.info("Enviando {} notificações...", userIds.size());
        
        userIds.forEach(userId -> {
            try {
                notificationService.send(userId);
                Thread.sleep(100); // Rate limiting
            } catch (Exception e) {
                log.error("Erro ao enviar notificação para {}", userId, e);
            }
        });
        
        log.info("Notificações enviadas!");
    }
}

// Uso
@RestController
public class ReportController {
    
    @GetMapping("/api/reports/attendance/{classId}")
    public DeferredResult<Report> getAttendanceReport(@PathVariable Long classId) {
        DeferredResult<Report> result = new DeferredResult<>(30000L);
        
        reportService.generateAttendanceReport(classId)
            .thenAccept(result::setResult)
            .exceptionally(ex -> {
                result.setErrorResult(ex);
                return null;
            });
        
        return result;
    }
}
```

---

### 11. 🧪 Spring Cloud - Microservices

**Objetivo**: Aprender arquitetura de microserviços.

**Caso de Uso**: Separar a aplicação em serviços independentes (Auth, Courses, Notifications).

**Tecnologias**:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

**O que você vai aprender**:
- Service discovery (Eureka)
- API Gateway
- Load balancing
- Circuit breaker (Resilience4j)
- Distributed configuration
- Inter-service communication

**Arquitetura sugerida**:
```
┌─────────────────┐
│   API Gateway   │
└────────┬────────┘
         │
    ┌────┴─────┬─────────┬──────────┐
    │          │         │          │
┌───▼───┐  ┌──▼───┐  ┌──▼────┐  ┌──▼────────┐
│ Auth  │  │Course│  │ User  │  │Notification│
│Service│  │Service│ │Service│  │  Service   │
└───────┘  └──────┘  └───────┘  └────────────┘
```

---

### 12. 📱 GraphQL API

**Objetivo**: Aprender APIs baseadas em GraphQL como alternativa ao REST.

**Caso de Uso**: API flexível onde o cliente escolhe exatamente quais dados precisa.

**Tecnologias**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-graphql</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

**O que você vai aprender**:
- GraphQL schema
- Queries e Mutations
- Resolvers
- DataLoader (N+1 problem)
- Subscriptions
- GraphQL vs REST

**Exemplo de implementação**:
```graphql
# schema.graphqls
type User {
    id: ID!
    name: String!
    email: String!
    courses: [Course!]!
}

type Course {
    id: ID!
    name: String!
    code: String!
    students: [User!]!
}

type Query {
    user(id: ID!): User
    users(page: Int, size: Int): [User!]!
    course(id: ID!): Course
}

type Mutation {
    createUser(input: CreateUserInput!): User!
    updateUser(id: ID!, input: UpdateUserInput!): User!
}
```

```java
@Controller
public class UserGraphQLController {
    
    @QueryMapping
    public User user(@Argument String id) {
        return userService.findById(Long.parseLong(id));
    }
    
    @QueryMapping
    public List<User> users(@Argument int page, @Argument int size) {
        return userService.findAll(page, size);
    }
    
    @MutationMapping
    public User createUser(@Argument CreateUserInput input) {
        return userService.create(input);
    }
    
    @SchemaMapping(typeName = "User", field = "courses")
    public List<Course> courses(User user) {
        return courseService.findByUserId(user.getId());
    }
}
```

---

### 13. 🔒 Spring Security - Features Avançadas

**Objetivo**: Aprofundar conhecimentos em segurança.

**Funcionalidades sugeridas**:

#### a) Rate Limiting
```java
@Configuration
public class RateLimitConfig {
    
    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.of("login", RateLimiterConfig.custom()
            .limitRefreshPeriod(Duration.ofMinutes(1))
            .limitForPeriod(5)
            .timeoutDuration(Duration.ofSeconds(0))
            .build());
    }
}
```

#### b) Two-Factor Authentication (2FA)
```java
@Service
public class TwoFactorAuthService {
    
    public String generateSecret() {
        return new GoogleAuthenticator().createCredentials().getKey();
    }
    
    public boolean verifyCode(String secret, int code) {
        return new GoogleAuthenticator().authorize(secret, code);
    }
}
```

#### c) Audit Logging
```java
@Aspect
@Component
public class AuditAspect {
    
    @Around("@annotation(Audited)")
    public Object logAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        String username = SecurityContextHolder.getContext()
            .getAuthentication().getName();
        String method = joinPoint.getSignature().getName();
        
        log.info("AUDIT: User {} executed {}", username, method);
        
        Object result = joinPoint.proceed();
        
        auditRepository.save(new AuditLog(username, method, LocalDateTime.now()));
        
        return result;
    }
}
```

---

### 14. 🐳 Docker + Docker Compose

**Objetivo**: Aprender containerização e orquestração.

**Caso de Uso**: Containerizar a aplicação e todos os serviços dependentes.

**Exemplo de Dockerfile**:
```dockerfile
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Exemplo de docker-compose.yml**:
```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_HOST=mysql
      - REDIS_HOST=redis
    depends_on:
      - mysql
      - redis
    networks:
      - synco-network

  mysql:
    image: mysql:8.0
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=syncoapp
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    networks:
      - synco-network

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    networks:
      - synco-network

  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    networks:
      - synco-network

  grafana:
    image: grafana/grafana
    ports:
      - "3000:3000"
    depends_on:
      - prometheus
    networks:
      - synco-network

volumes:
  mysql-data:

networks:
  synco-network:
    driver: bridge
```

---

### 15. 🧪 Testes Avançados

**Objetivo**: Melhorar cobertura e qualidade dos testes.

**Tecnologias sugeridas**:
```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mysql</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.tngtech.archunit</groupId>
    <artifactId>archunit-junit5</artifactId>
    <version>1.0.1</version>
    <scope>test</scope>
</dependency>
```

**O que você vai aprender**:

#### a) Testcontainers - Testes com containers
```java
@Testcontainers
@SpringBootTest
class UserIntegrationTest {
    
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
    
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
    
    @Test
    void shouldCreateUser() {
        // teste com banco real em container
    }
}
```

#### b) REST Assured - Testes de API
```java
@Test
void shouldLoginSuccessfully() {
    given()
        .contentType(ContentType.JSON)
        .body(new LoginRequest("user@email.com", "password"))
    .when()
        .post("/api/auth/login")
    .then()
        .statusCode(200)
        .body("token", notNullValue())
        .body("email", equalTo("user@email.com"));
}
```

#### c) ArchUnit - Testes de arquitetura
```java
@AnalyzeClasses(packages = "com.api.synco")
class ArchitectureTest {
    
    @ArchTest
    static final ArchRule controllers_should_be_in_application_layer =
        classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().resideInAPackage("..application.controller..");
    
    @ArchTest
    static final ArchRule services_should_not_depend_on_controllers =
        noClasses()
            .that().resideInAPackage("..domain.service..")
            .should().dependOnClassesThat()
            .resideInAPackage("..application.controller..");
}
```

---

### 16. 📊 Banco de Dados Avançado

**Objetivo**: Aprofundar conhecimentos em JPA e bancos de dados.

**Funcionalidades sugeridas**:

#### a) Query Optimization com @EntityGraph
```java
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    @EntityGraph(attributePaths = {"courses", "classes"})
    Optional<UserEntity> findWithCoursesById(Long id);
}
```

#### b) Database Migrations com Flyway/Liquibase
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

```sql
-- V1__create_users_table.sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### c) Read/Write Splitting
```java
@Configuration
public class DataSourceConfig {
    
    @Bean
    @ConfigurationProperties("spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @ConfigurationProperties("spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    public DataSource routingDataSource() {
        RoutingDataSource routing = new RoutingDataSource();
        
        Map<Object, Object> sources = new HashMap<>();
        sources.put("write", writeDataSource());
        sources.put("read", readDataSource());
        
        routing.setTargetDataSources(sources);
        routing.setDefaultTargetDataSource(writeDataSource());
        
        return routing;
    }
}
```

---

### 17. 🌐 Internacionalização (i18n)

**Objetivo**: Aprender suporte a múltiplos idiomas.

**Caso de Uso**: Aplicação em português, inglês e espanhol.

**Implementação**:
```java
@Configuration
public class I18nConfig {
    
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.of("pt", "BR"));
        return resolver;
    }
}

@Service
public class MessageService {
    
    private final MessageSource messageSource;
    
    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(
            code, 
            args, 
            LocaleContextHolder.getLocale()
        );
    }
}
```

**Arquivos de mensagens**:
```properties
# messages_pt_BR.properties
user.created=Usuário criado com sucesso
user.notfound=Usuário não encontrado
email.invalid=Email inválido

# messages_en.properties
user.created=User created successfully
user.notfound=User not found
email.invalid=Invalid email

# messages_es.properties
user.created=Usuario creado exitosamente
user.notfound=Usuario no encontrado
email.invalid=Email inválido
```

---

### 18. 🔐 API Versioning

**Objetivo**: Aprender versionamento de APIs.

**Estratégias**:

#### a) URI Versioning
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 { }

@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 { }
```

#### b) Header Versioning
```java
@GetMapping(value = "/api/users", headers = "API-Version=1")
public ResponseEntity<List<UserV1>> getUsersV1() { }

@GetMapping(value = "/api/users", headers = "API-Version=2")
public ResponseEntity<List<UserV2>> getUsersV2() { }
```

---

## 🎓 Roadmap de Aprendizado Sugerido

### Nível 1 - Fundamentos (Já implementado ✅)
1. ✅ Spring Boot Basics
2. ✅ Spring Security + JWT
3. ✅ Spring Data JPA
4. ✅ REST API Design
5. ✅ Exception Handling
6. ✅ Validation

### Nível 2 - Intermediário (3-6 meses)
1. 📧 **Spring Mail** (1 semana) - Mais fácil de começar
2. ⏰ **Scheduler** (1 semana) - Conceitos simples, muito útil
3. 🔄 **Spring Async** (1-2 semanas) - Importante para performance
4. 💬 **WebSocket** (2 semanas) - Divertido e moderno
5. ⚡ **Redis Cache** (2 semanas) - Essencial para produção
6. 📤 **File Upload + S3** (2 semanas) - Muito comum em projetos reais

### Nível 3 - Avançado (6-12 meses)
1. 📊 **Actuator + Prometheus + Grafana** (2-3 semanas)
2. 🔐 **OAuth2 + Social Login** (2-3 semanas)
3. 🔍 **Elasticsearch** (3-4 semanas)
4. 🔄 **Kafka** (3-4 semanas)
5. 🐳 **Docker + Kubernetes** (4 semanas)
6. 🧪 **Testcontainers + Advanced Testing** (2-3 semanas)

### Nível 4 - Expert (1+ ano)
1. 🧪 **Spring Cloud + Microservices** (2-3 meses)
2. 📱 **GraphQL** (1-2 meses)
3. 🌐 **Reactive Programming (WebFlux)** (2 meses)
4. 🔐 **Advanced Security (2FA, Audit, etc)** (1 mês)

---

## 📚 Recursos de Aprendizado

### Documentação Oficial
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Baeldung - Spring Tutorials](https://www.baeldung.com/spring-tutorial)

### Cursos Recomendados
- **Udemy**: "Spring Boot 3, Spring 6 & Hibernate for Beginners"
- **Pluralsight**: "Spring Framework: Spring Boot Fundamentals"
- **YouTube**: Amigoscode, Java Brains, Daily Code Buffer

### Livros
- "Spring in Action" - Craig Walls
- "Cloud Native Java" - Josh Long
- "Pro Spring Boot 2" - Felipe Gutierrez

### Comunidades
- [Stack Overflow - Spring Boot](https://stackoverflow.com/questions/tagged/spring-boot)
- [Spring Community Forum](https://community.spring.io/)
- [r/SpringBoot](https://www.reddit.com/r/SpringBoot/)

---

## 🎯 Conclusão

Este documento apresenta **18 áreas principais** de aprendizado que vão te preparar para o mercado profissional de desenvolvimento Spring Boot. Cada funcionalidade sugerida:

✅ **Resolve um problema real** no contexto do SyncoApi  
✅ **Ensina conceitos importantes** usados em produção  
✅ **Tem demanda no mercado** de trabalho  
✅ **Pode ser implementada incrementalmente**  

**Sugestão de próximos passos**:
1. Escolha 1-2 funcionalidades do Nível 2 para começar
2. Implemente uma feature por vez
3. Faça testes adequados
4. Documente o que aprendeu
5. Compartilhe no GitHub/LinkedIn

**Lembre-se**: O importante não é implementar tudo de uma vez, mas aprender profundamente cada conceito. Qualidade > Quantidade! 🚀

---

**Autor**: Documento gerado para auxiliar no aprendizado do desenvolvedor  
**Data**: Dezembro 2024  
**Versão**: 1.0
