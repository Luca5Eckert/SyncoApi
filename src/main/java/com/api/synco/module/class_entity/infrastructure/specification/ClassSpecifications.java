package com.api.synco.module.class_entity.infrastructure.specification;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import org.springframework.data.jpa.domain.Specification;

public class ClassSpecifications {

    public static Specification<ClassEntity> courseIdContains(long courseId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id").get("courseId"), courseId);
    }

    public static Specification<ClassEntity> classNumberContains(int classNumber) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id").get("number"), classNumber);
    }


    public static Specification<ClassEntity> shiftContains(Shift shift) {
        return (root, query, criteriaBuilder) -> {
            if (shift == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("shift"), shift);
        };
    }

}