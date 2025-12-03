package com.api.synco.infrastructure.persistence.class_user.repository;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClassUserRepositoryAdapter implements ClassUserRepository {

    private final ClassUserRepositoryJpa classUserRepositoryJpa;

    public ClassUserRepositoryAdapter(ClassUserRepositoryJpa classUserRepositoryJpa) {
        this.classUserRepositoryJpa = classUserRepositoryJpa;
    }

    @Override
    public void save(ClassUser classUser) {
        classUserRepositoryJpa.save(classUser);
    }

}
