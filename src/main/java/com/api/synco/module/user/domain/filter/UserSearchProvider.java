package com.api.synco.module.user.domain.filter;

import com.api.synco.module.user.infrastructure.specification.UserSpecifications;
import com.api.synco.module.user.domain.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Component for creating JPA Specifications from user filter criteria.
 *
 * <p>This class transforms {@link UserFilter} objects into composable
 * JPA {@link Specification} objects that can be used for dynamic queries.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserFilter
 * @see UserSpecifications
 */
@Component
public class UserSearchProvider {

    /**
     * Creates a JPA Specification from a user filter.
     *
     * <p>The resulting specification combines all filter criteria using
     * AND logic. Null or empty filter values are ignored.</p>
     *
     * @param userFilter the filter criteria to convert
     * @return a composable {@link Specification} for querying users
     */
    public Specification<UserEntity> create(UserFilter userFilter){
        return UserSpecifications.nameContains(userFilter.nameContains())
                .and(UserSpecifications.emailContains(userFilter.emailContains()))
                .and(UserSpecifications.createdBetween(userFilter.createAt(), userFilter.createTo()))
                .and(UserSpecifications.roleEquals(userFilter.role()));
    }

}
