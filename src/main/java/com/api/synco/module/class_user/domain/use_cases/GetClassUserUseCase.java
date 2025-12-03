package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetClassUserUseCase {

    private final ClassUserRepository classUserRepository;

    public GetClassUserUseCase(ClassUserRepository classUserRepository) {
        this.classUserRepository = classUserRepository;
    }

    @Transactional(readOnly = true)
    public ClassUser execute(ClassUserId classUserId) {
        return classUserRepository.findById(classUserId)
                .orElseThrow(ClassUserNotFoundException::new);
    }

}