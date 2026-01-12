package com.api.synco.modules.class_user.infrastructure.config;

import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.modules.class_user.domain.port.ClassUserRepositoryPort;
import com.api.synco.modules.class_user.domain.usecase.UpdateClassUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for class-user module beans.
 *
 * <p>This configuration class handles the dependency injection for
 * domain layer components that cannot be annotated with Spring annotations
 * (to maintain domain purity).</p>
 *
 * <p>The use cases are instantiated here with their dependencies injected,
 * allowing the domain layer to remain free of framework annotations.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
public class ClassUserModuleConfig {

    /**
     * Creates the UpdateClassUserUseCase bean.
     *
     * @param classUserRepository the class-user repository port
     * @param userRepository the user repository port
     * @param permissionService the permission service
     * @return the configured use case instance
     */
    @Bean
    public UpdateClassUserUseCaseImpl updateClassUserUseCaseImpl(
            ClassUserRepositoryPort classUserRepository,
            UserRepository userRepository,
            PermissionService permissionService
    ) {
        return new UpdateClassUserUseCaseImpl(classUserRepository, userRepository, permissionService);
    }
}
