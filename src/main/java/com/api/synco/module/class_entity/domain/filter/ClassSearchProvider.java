package com.api.synco.module.class_entity.domain.filter;

import com.api.synco.module.class_entity.infrastructure.specification.ClassSpecifications;
import com.api.synco.module.class_entity.domain.ClassEntity;
import org.springframework.data.jpa.domain.Specification;

public class ClassSearchProvider {

    public static Specification<ClassEntity> of(ClassFilter classFilter){
        return ClassSpecifications.courseIdContains(classFilter.courseIdContains())
                .and(ClassSpecifications.classNumberContains(classFilter.classNumberContains())
                .and(ClassSpecifications.shiftContains(classFilter.shiftContains())));
    }


}
