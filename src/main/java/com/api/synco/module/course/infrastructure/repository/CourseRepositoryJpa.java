package com.api.synco.module.course.infrastructure.repository;

import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.user.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepositoryJpa extends JpaRepository<CourseEntity, Long>, JpaSpecificationExecutor<CourseEntity> {

    boolean existsByNameOrAcronym(String name, String acronym);

}
