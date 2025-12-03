package com.api.synco.module.class_user.domain.port;

import com.api.synco.module.class_user.domain.ClassUser;

public interface ClassUserRepository {
    void save(ClassUser classUser);
}
