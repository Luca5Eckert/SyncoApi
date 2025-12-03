package com.api.synco.module.user.domain.port;

import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.filter.PageUser;
import com.api.synco.module.user.domain.vo.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

/**
 * Repository port interface for user persistence operations.
 *
 * <p>This interface defines the contract for user data access operations.
 * It follows the hexagonal architecture pattern, allowing the domain layer
 * to remain independent of specific persistence implementations.</p>
 *
 * <p>Implementations of this interface handle the actual data storage,
 * whether it be a relational database, NoSQL store, or other persistence mechanism.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserEntity
 */
public interface UserRepository {

    /**
     * Saves a user entity to the data store.
     *
     * <p>This method handles both insert and update operations.</p>
     *
     * @param user the user entity to save
     */
    void save(UserEntity user);

    /**
     * Checks if a user exists with the specified email address.
     *
     * @param email the email address to check
     * @return {@code true} if a user with the email exists, {@code false} otherwise
     */
    boolean existsByEmail(Email email);

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the user's unique identifier
     * @return an {@link Optional} containing the user if found, or empty if not found
     */
    Optional<UserEntity> findById(long id);

    /**
     * Checks if a user exists with the specified identifier.
     *
     * @param id the user's unique identifier
     * @return {@code true} if the user exists, {@code false} otherwise
     */
    boolean existsById(long id);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to delete
     */
    void deleteById(long id);

    /**
     * Finds all users matching the given specification with pagination.
     *
     * @param userEntitySpecification the JPA specification for filtering users
     * @param pageUser the pagination parameters
     * @return a page of users matching the criteria
     */
    Page<UserEntity> findAll(Specification<UserEntity> userEntitySpecification, PageUser pageUser);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an {@link Optional} containing the user if found, or empty if not found
     */
    Optional<UserEntity> findByEmail(Email email);
}
