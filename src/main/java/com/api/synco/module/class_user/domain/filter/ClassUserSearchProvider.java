package com.api.synco.module.class_user.domain.filter;

import com.api.synco.infrastructure.persistence.class_user.specification.ClassUserSpecifications;
import com.api.synco.infrastructure.persistence.course.specification.CourseSpecifications;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.course.domain.CourseEntity;
import org.springframework.data.jpa.domain.Specification;

public class ClassUserSearchProvider {


    public static Specification<ClassUser> of(ClassUserFilter classUserFilter){
        return ClassUserSpecifications.typeUserClassContains(classUserFilter.typeUserClass())
                .and(ClassUserSpecifications.classUserIdContains(classUserFilter.classUserId()))
                .and(ClassUserSpecifications.userIdContains(classUserFilter.userId()));
    }


}
