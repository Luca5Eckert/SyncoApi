package com.api.synco.module.user.domain.service;

import com.api.synco.module.user.application.dto.create.UserCreateRequest;
import com.api.synco.module.user.application.dto.create.UserCreateResponse;
import com.api.synco.module.user.application.dto.delete.UserDeleteRequest;
import com.api.synco.module.user.application.dto.edit.UserEditRequest;
import com.api.synco.module.user.application.dto.edit.UserEditResponse;
import com.api.synco.module.user.application.dto.get.UserGetResponse;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.mapper.UserMapper;
import com.api.synco.module.user.domain.use_case.*;
import com.api.synco.module.user.infrastructure.aspects.logging.ToLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Domain service for user management operations.
 *
 * <p>This service coordinates user-related business operations by delegating
 * to appropriate use cases and handling the mapping between domain entities
 * and DTOs.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>User creation with validation</li>
 *   <li>User deletion with permission checks</li>
 *   <li>User profile editing</li>
 *   <li>User retrieval by ID</li>
 *   <li>Paginated user listing with filters</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserMapper
 * @see UserCreateUseCase
 * @see UserDeleteUseCase
 * @see UserEditUseCase
 * @see UserGetUseCase
 * @see UserGetAllUseCase
 */
@Service
public class UserService {

    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserCreateUseCase userCreateUseCase;
    private final UserDeleteUseCase userDeleteUseCase;
    private final UserEditUseCase userEditUseCase;
    private final UserGetUseCase userGetUseCase;
    private final UserGetAllUseCase userGetAllUseCase;

    /**
     * Constructs a new user service with the required dependencies.
     *
     * @param userMapper the mapper for converting between entities and DTOs
     * @param userCreateUseCase the use case for user creation
     * @param userDeleteUseCase the use case for user deletion
     * @param userEditUseCase the use case for user editing
     * @param userGetUseCase the use case for retrieving a single user
     * @param userGetAllUseCase the use case for retrieving multiple users
     */
    public UserService(UserMapper userMapper, UserCreateUseCase userCreateUseCase, UserDeleteUseCase userDeleteUseCase, UserEditUseCase userEditUseCase, UserGetUseCase userGetUseCase, UserGetAllUseCase userGetAllUseCase) {
        this.userMapper = userMapper;
        this.userCreateUseCase = userCreateUseCase;
        this.userDeleteUseCase = userDeleteUseCase;
        this.userEditUseCase = userEditUseCase;
        this.userGetUseCase = userGetUseCase;
        this.userGetAllUseCase = userGetAllUseCase;
    }

    /**
     * Creates a new user in the system.
     *
     * @param userCreateRequest the request containing user data
     * @param userId the ID of the authenticated user performing the creation
     * @return the created user's data
     */
    @ToLog
    public UserCreateResponse create(UserCreateRequest userCreateRequest, long userId) {
        var user = userCreateUseCase.execute(userCreateRequest, userId);
        logger.info("User created successfully, id: {}", user.getId());
        return userMapper.toCreateResponse(user);
    }

    /**
     * Deletes a user from the system.
     *
     * @param userDeleteRequest the request containing the user ID to delete
     * @param idUserAutenticated the ID of the authenticated user performing the deletion
     */
    @ToLog
    public void delete(UserDeleteRequest userDeleteRequest, long idUserAutenticated) {
        userDeleteUseCase.execute(userDeleteRequest, idUserAutenticated);
        logger.info("User deleted successfully, id: {}", userDeleteRequest.id());
    }

    /**
     * Edits an existing user's profile.
     *
     * @param userEditRequest the request containing updated user data
     * @param idUserAutenticated the ID of the authenticated user performing the edit
     * @return the updated user's data
     */
    @ToLog
    public UserEditResponse edit(UserEditRequest userEditRequest, long idUserAutenticated) {
        var user = userEditUseCase.execute(userEditRequest, idUserAutenticated);
        logger.info("User edited successfully, id: {}", userEditRequest.id());

        return userMapper.toEditResponse(user);
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the user's data
     */
    @ToLog
    public UserGetResponse get(long id) {
        var user = userGetUseCase.execute(id);
        logger.info("User retrieved successfully, id: {}", id);

        return userMapper.toGetResponse(user);
    }

    /**
     * Retrieves a paginated list of users with optional filters.
     *
     * @param name filter by name containing this value (optional)
     * @param email filter by email containing this value (optional)
     * @param roleUser filter by user role (optional)
     * @param createAt filter users created from this date onwards (optional)
     * @param updateAt filter users updated up to this date (optional)
     * @param pageNumber the page number to retrieve (0-based)
     * @param pageSize the number of users per page
     * @return a list of users matching the criteria
     */
    @ToLog
    public List<UserGetResponse> getAll(String name, String email, RoleUser roleUser, Instant createAt, Instant updateAt, int pageNumber, int pageSize) {
        var users = userGetAllUseCase.execute(name, email, roleUser, createAt, updateAt, pageNumber, pageSize);
        logger.info("Fetched {} users successfully",users.getTotalElements());

        return users.stream()
                .map(userMapper::toGetResponse)
                .toList();

    }
}
