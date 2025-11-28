package com.api.synco.infrastructure.persistence.class_entity.repository;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.filter.ClassFilter;
import com.api.synco.module.class_entity.domain.filter.ClassSearchProvider;
import com.api.synco.module.class_entity.domain.filter.PageClass;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.course.domain.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public boolean existById(ClassEntityId idClass) {
        return classRepositoryJpa.existsById(idClass);
    }

    @Override
    public void deleteById(ClassEntityId idClass) {
        classRepositoryJpa.deleteById(idClass);
    }

    @Override
    public Page<ClassEntity> findAll(Specification<ClassEntity> classEntitySpecification, PageRequest pageRequest) {
       return classRepositoryJpa.findAll(classEntitySpecification, pageRequest);
    }
}
