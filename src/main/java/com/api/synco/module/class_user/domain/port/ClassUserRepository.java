package com.api.synco.module.class_user.domain.port;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;

import java.util.Optional;

public interface ClassUserRepository {
    void save(ClassUser classUser);

    boolean existById(ClassUserId classUserId);

    void deleteById(ClassUserId classUserId);

    Optional<ClassUser> findById(ClassUserId classUserId);
}
