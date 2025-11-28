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

@Component
public class UserGetAllUseCase {

    private final UserRepository userRepository;
    private final UserSearchProvider userCreateSearch;

    public UserGetAllUseCase(UserRepository userRepository, UserSearchProvider userCreateSearch) {
        this.userRepository = userRepository;
        this.userCreateSearch = userCreateSearch;
    }


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
