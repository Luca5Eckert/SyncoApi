package com.api.synco.infrastructure.persistence.class_user.repository;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassUserRepositoryJpa extends JpaRepository<ClassUser, ClassUserId>, JpaSpecificationExecutor<ClassUser> {
}
