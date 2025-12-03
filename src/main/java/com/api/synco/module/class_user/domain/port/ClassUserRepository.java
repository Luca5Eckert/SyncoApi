package com.api.synco.module.class_user.domain.port;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;

public interface ClassUserRepository {
    void save(ClassUser classUser);

    boolean existById(ClassUserId classUserId);

    void deleteById(ClassUserId classUserId);
}
