package com.api.synco.infrastructure.persistence.course.specification;

import com.api.synco.module.course.domain.CourseEntity;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecifications {

    public static Specification<CourseEntity> nameContains(String name){
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("name").as(String.class)), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<CourseEntity> acronymContains(String acronym){
        return (root, query, cb) -> {
            if (acronym == null || acronym.isBlank()) return null;
            return cb.like(cb.lower(root.get("acronym").as(String.class)), "%" + acronym.toLowerCase() + "%");
        };
    }
}
