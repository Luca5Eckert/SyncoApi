package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case for retrieving a single user by their identifier.
 *
 * <p>This use case handles the retrieval of user data from the database
 * based on the user's unique identifier.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepository
 */
@Component
public class UserGetUseCase {

    private final UserRepository userRepository;

    /**
     * Constructs a new user get use case.
     *
     * @param userRepository the repository for user persistence
     */
    public UserGetUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the user retrieval use case.
     *
     * <p>This method is marked as read-only for transaction optimization.</p>
     *
     * @param id the unique identifier of the user to retrieve
     * @return the user entity
     * @throws UserNotFoundDomainException if no user exists with the given ID
     */
    @Transactional(readOnly = true)
    public UserEntity execute(long id) {
        return userRepository.findById(id).orElseThrow( () -> new UserNotFoundDomainException(id) );
    }

}
