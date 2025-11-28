package com.api.synco.infrastructure.persistence.class_entity.repository;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.course.domain.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRepositoryJpa extends JpaRepository<ClassEntity, ClassEntityId>, JpaSpecificationExecutor<ClassEntity> {

    @Query("""
        SELECT c.id.number
        FROM ClassEntity c
        WHERE c.course = :course
        ORDER BY c.id.number DESC
        LIMIT 1
    """)
    Optional<Integer> getLastNumberOfCourse(CourseEntity course);

}
