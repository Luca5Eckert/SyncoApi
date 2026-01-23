# Multi-stage Dockerfile for SyncoApi - Optimized for performance and size

# ================================
# Stage 1: Build Stage
# ================================
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first for better layer caching
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application (skip tests for faster builds - run tests in CI/CD)
RUN ./mvnw clean package -DskipTests -B

# Extract layered JAR for optimal Docker layer caching
RUN mkdir -p target/dependency && \
    java -Djarmode=layertools -jar target/*.jar extract --destination target/dependency

# ================================
# Stage 2: Runtime Stage
# ================================
FROM eclipse-temurin:21-jre-alpine AS runtime

# Add metadata
LABEL maintainer="Luca5Eckert <https://github.com/Luca5Eckert/SyncoApi>"
LABEL description="Synco API - Academic Management Platform Backend"
LABEL version="1.0.0"

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring

# Set working directory
WORKDIR /app

# Copy dependencies from builder (layered approach for better caching)
COPY --from=builder --chown=spring:spring /app/target/dependency/dependencies/ ./
COPY --from=builder --chown=spring:spring /app/target/dependency/spring-boot-loader/ ./
COPY --from=builder --chown=spring:spring /app/target/dependency/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring /app/target/dependency/application/ ./

# Switch to non-root user
USER spring:spring

# Expose port (Spring Boot default)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Set JVM options for container environment
ENV JAVA_OPTS="-XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -XX:+UseG1GC \
    -XX:+OptimizeStringConcat \
    -XX:+UseStringDeduplication \
    -Djava.security.egd=file:/dev/./urandom"

# Run the application using Spring Boot's layered jar approach
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]
