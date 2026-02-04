package com.api.synco.module.class_user.domain.filter;

import com.api.synco.module.class_user.infrastructure.specification.ClassUserSpecifications;
import com.api.synco.module.class_user.domain.ClassUser;
import org.springframework.data.jpa.domain.Specification;

public class ClassUserSearchProvider {


    public static Specification<ClassUser> of(ClassUserFilter classUserFilter){
        return ClassUserSpecifications.typeUserClassContains(classUserFilter.typeUserClass())
                .and(ClassUserSpecifications.classUserIdContains(classUserFilter.classUserId()))
                .and(ClassUserSpecifications.userIdContains(classUserFilter.userId()));
    }


}
