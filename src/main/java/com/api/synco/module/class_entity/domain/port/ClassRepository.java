package com.api.synco.module.class_entity.domain.port;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.filter.ClassFilter;
import com.api.synco.module.class_entity.domain.filter.PageClass;
import com.api.synco.module.course.domain.CourseEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ClassRepository {

    void save(ClassEntity classEntity);

    int getNextNumberOfCourse(CourseEntity course);

    Optional<ClassEntity> findById(ClassEntityId idClass);

    boolean existById(ClassEntityId idClass);

    void deleteById(ClassEntityId idClass);

    Page<ClassEntity> findAll(ClassFilter classFilter, PageClass pageClass);
}
