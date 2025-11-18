package com.api.synco.module.class_entity.domain.port;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.course.domain.CourseEntity;

import java.util.Optional;

public interface ClassRepository {

    void save(ClassEntity classEntity);

    int getNextNumberOfCourse(CourseEntity course);

    Optional<ClassEntity> findById(long idClass);
}
