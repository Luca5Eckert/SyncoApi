package com.api.synco.modules.class_user.domain.port;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.filter.ClassUserFilter;
import com.api.synco.module.class_user.domain.filter.PageClassUser;

import java.util.List;
import java.util.Optional;

/**
 * Domain port interface for class-user persistence operations.
 *
 * <p>This interface defines the contract for class-user data access operations.
 * It follows the hexagonal architecture pattern and is completely free of
 * framework dependencies (no Spring Data or JPA imports).</p>
 *
 * <p>The implementation of this port resides in the infrastructure layer and
 * handles the translation between domain objects and persistence entities.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassUser
 * @see ClassUserId
 */
public interface ClassUserRepositoryPort {

    /**
     * Saves a class-user entity to the data store.
     *
     * <p>This method handles both insert and update operations.</p>
     *
     * @param classUser the class-user entity to save
     */
    void save(ClassUser classUser);

    /**
     * Checks if a class-user association exists with the specified identifier.
     *
     * @param classUserId the composite identifier to check
     * @return {@code true} if the association exists, {@code false} otherwise
     */
    boolean existById(ClassUserId classUserId);

    /**
     * Deletes a class-user association by its composite identifier.
     *
     * @param classUserId the composite identifier of the association to delete
     */
    void deleteById(ClassUserId classUserId);

    /**
     * Finds a class-user association by its composite identifier.
     *
     * @param classUserId the composite identifier to search for
     * @return an {@link Optional} containing the class-user if found, or empty if not found
     */
    Optional<ClassUser> findById(ClassUserId classUserId);

    /**
     * Finds all class-user associations matching the given filter with pagination.
     *
     * <p>This method accepts pure POJO filter objects without framework dependencies.
     * The infrastructure adapter is responsible for translating these to
     * framework-specific queries (e.g., JPA Specifications).</p>
     *
     * @param filter the domain filter containing search criteria
     * @param page the pagination parameters
     * @return a list of class-users matching the criteria
     */
    List<ClassUser> findAll(ClassUserFilter filter, PageClassUser page);

    /**
     * Counts the total number of class-user associations matching the filter.
     *
     * @param filter the domain filter containing search criteria
     * @return the total count of matching records
     */
    long count(ClassUserFilter filter);
}
