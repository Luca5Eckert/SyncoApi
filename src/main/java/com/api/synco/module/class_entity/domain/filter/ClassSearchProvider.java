package com.api.synco.module.class_entity.domain.filter;

import com.api.synco.infrastructure.persistence.class_entity.specification.ClassSpecifications;
import com.api.synco.infrastructure.persistence.course.specification.CourseSpecifications;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.course.domain.CourseEntity;
import org.springframework.data.jpa.domain.Specification;

public class ClassSearchProvider {

    public static Specification<ClassEntity> of(ClassFilter classFilter){
        return ClassSpecifications.courseIdContains(classFilter.courseIdContains())
                .and(ClassSpecifications.classNumberContains(classFilter.classNumberContains())
                .and(ClassSpecifications.shiftContains(classFilter.shiftContains())));
    }


}
