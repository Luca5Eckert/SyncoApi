package com.api.synco.modules.class_user.application.service;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.application.dto.update.UpdateClassUserRequest;
import com.api.synco.module.class_user.application.dto.update.UpdateClassUserResponse;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.mapper.ClassUserMapper;
import com.api.synco.modules.class_user.domain.command.UpdateClassUserCommand;
import com.api.synco.modules.class_user.domain.usecase.UpdateClassUserUseCaseImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for class-user operations.
 *
 * <p>This service acts as the orchestration layer between the web/API layer and
 * the domain layer. It is responsible for:</p>
 * <ul>
 *   <li>Converting Web DTOs to Domain Commands</li>
 *   <li>Calling the appropriate use cases</li>
 *   <li>Converting domain results back to Response DTOs</li>
 *   <li>Managing transaction boundaries</li>
 * </ul>
 *
 * <p>This layer ensures that the domain layer remains pure and free of
 * framework-specific concerns like Web DTOs and Spring annotations.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UpdateClassUserUseCaseImpl
 * @see ClassUserMapper
 */
@Service
public class ClassUserApplicationService {

    private final UpdateClassUserUseCaseImpl updateClassUserUseCase;
    private final ClassUserMapper classUserMapper;

    /**
     * Constructs a new ClassUserApplicationService.
     *
     * @param updateClassUserUseCase the use case for updating class-users
     * @param classUserMapper the mapper for converting between domain and DTOs
     */
    public ClassUserApplicationService(
            UpdateClassUserUseCaseImpl updateClassUserUseCase,
            ClassUserMapper classUserMapper
    ) {
        this.updateClassUserUseCase = updateClassUserUseCase;
        this.classUserMapper = classUserMapper;
    }

    /**
     * Updates a class-user association.
     *
     * <p>This method:</p>
     * <ol>
     *   <li>Constructs the composite ClassUserId from path parameters</li>
     *   <li>Converts the Web DTO to a Domain Command</li>
     *   <li>Calls the use case with the command</li>
     *   <li>Converts the result to a Response DTO</li>
     * </ol>
     *
     * @param updateClassUserRequest the request DTO containing update data
     * @param courseId the course identifier
     * @param classNumber the class number identifier
     * @param userId the user identifier
     * @param authenticatedUserId the ID of the user performing the operation
     * @return the response DTO containing the updated class-user data
     */
    @Transactional
    public UpdateClassUserResponse update(
            UpdateClassUserRequest updateClassUserRequest,
            long courseId,
            int classNumber,
            long userId,
            long authenticatedUserId
    ) {
        // Build composite ID
        ClassEntityId classEntityId = new ClassEntityId(courseId, classNumber);
        ClassUserId classUserId = new ClassUserId(userId, classEntityId);

        // Convert DTO to Command (Web Layer -> Domain Layer)
        UpdateClassUserCommand command = toCommand(updateClassUserRequest);

        // Execute use case with pure domain objects
        ClassUser classUser = updateClassUserUseCase.execute(command, classUserId, authenticatedUserId);

        // Convert domain result to Response DTO (Domain Layer -> Web Layer)
        return classUserMapper.toUpdateResponse(classUser);
    }

    /**
     * Converts a Web DTO to a Domain Command.
     *
     * <p>This private method encapsulates the conversion logic, ensuring that
     * the use case layer never sees framework-specific DTOs.</p>
     *
     * @param request the web request DTO
     * @return the domain command
     */
    private UpdateClassUserCommand toCommand(UpdateClassUserRequest request) {
        return new UpdateClassUserCommand(request.newTypeUserClass());
    }
}
