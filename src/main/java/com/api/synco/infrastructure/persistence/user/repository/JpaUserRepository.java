package com.api.synco.infrastructure.persistence.user.repository;

import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository interface for {@link UserEntity} persistence operations.
 *
 * <p>This interface extends Spring Data JPA's {@link JpaRepository} and
 * {@link JpaSpecificationExecutor} to provide CRUD operations and
 * dynamic query capabilities for user entities.</p>
 *
 * <p>The repository supports:</p>
 * <ul>
 *   <li>Standard CRUD operations (inherited from JpaRepository)</li>
 *   <li>Specification-based queries (inherited from JpaSpecificationExecutor)</li>
 *   <li>Custom queries for email-based lookups</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 * @see UserEntity
 */
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    /**
     * Checks if a user exists with the specified email address.
     *
     * @param email the email address to check
     * @return {@code true} if a user with the email exists, {@code false} otherwise
     */
    boolean existsByEmail(Email email);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an {@link Optional} containing the user if found, or empty if not found
     */
    Optional<UserEntity> findByEmail(Email email);
}
