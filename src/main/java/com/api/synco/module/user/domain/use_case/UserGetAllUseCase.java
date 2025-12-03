package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.filter.PagenableUserProvider;
import com.api.synco.module.user.domain.filter.UserFilter;
import com.api.synco.module.user.domain.filter.UserSearchProvider;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Use case for retrieving a paginated list of users with optional filters.
 *
 * <p>This use case handles the retrieval of multiple users from the database
 * with support for various filter criteria and pagination.</p>
 *
 * <p>Available filters:</p>
 * <ul>
 *   <li>Name substring matching</li>
 *   <li>Email substring matching</li>
 *   <li>Role exact matching</li>
 *   <li>Creation date range</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepository
 * @see UserSearchProvider
 */
@Component
public class UserGetAllUseCase {

    private final UserRepository userRepository;
    private final UserSearchProvider userCreateSearch;

    /**
     * Constructs a new user get all use case.
     *
     * @param userRepository the repository for user persistence
     * @param userCreateSearch the provider for creating search specifications
     */
    public UserGetAllUseCase(UserRepository userRepository, UserSearchProvider userCreateSearch) {
        this.userRepository = userRepository;
        this.userCreateSearch = userCreateSearch;
    }


    /**
     * Executes the paginated user retrieval use case.
     *
     * <p>This method is marked as read-only for transaction optimization.</p>
     *
     * @param name filter by name containing this value (optional)
     * @param email filter by email containing this value (optional)
     * @param roleUser filter by user role (optional)
     * @param createAt filter users created from this date onwards (optional)
     * @param updateAt filter users updated up to this date (optional)
     * @param pageNumber the page number to retrieve (0-based)
     * @param pageSize the number of users per page
     * @return a page of users matching the criteria
     */
    @Transactional(readOnly = true)
    public Page<UserEntity> execute(String name
            , String email
            , RoleUser roleUser
            , Instant createAt
            , Instant updateAt
            , int pageNumber
            , int pageSize) {

        var criteria = UserFilter.builder()
                .setNameContains(name)
                .setEmailContains(email)
                .setRole(roleUser)
                .setCreatedFrom(createAt)
                .setCreatedTo(updateAt)
                .build();

        var searchSpecification = userCreateSearch.create(criteria);

        var searchPaginable = PagenableUserProvider.toInstance(pageNumber, pageSize);

        return userRepository.findAll(searchSpecification, searchPaginable);
    }

}
