package com.api.synco.infrastructure.persistence.class_entity.repository;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.course.domain.CourseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClassRepositoryAdapter implements ClassRepository {

    private final ClassRepositoryJpa classRepositoryJpa;

    public ClassRepositoryAdapter(ClassRepositoryJpa classRepositoryJpa) {
        this.classRepositoryJpa = classRepositoryJpa;
    }


    @Override
    public void save(ClassEntity classEntity) {
        classRepositoryJpa.save(classEntity);
    }

    @Override
    public int getNextNumberOfCourse(CourseEntity course) {
        return classRepositoryJpa.getLastNumberOfCourse(course).orElse(1);
    }

    @Override
    public Optional<ClassEntity> findById(ClassEntityId idClass) {
        return classRepositoryJpa.findById(idClass);
    }
}
