package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserGetUseCase {

    private final UserRepository userRepository;

    public UserGetUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserEntity execute(long id) {
        return userRepository.findById(id).orElseThrow( () -> new UserNotFoundDomainException(id) );
    }

}
