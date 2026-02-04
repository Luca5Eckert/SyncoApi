package com.api.synco.module.course.domain.filter;

import com.api.synco.module.course.infrastructure.specification.CourseSpecifications;
import com.api.synco.module.course.domain.CourseEntity;
import org.springframework.data.jpa.domain.Specification;

public class CourseSearchProvider {


    public static Specification<CourseEntity> of(CourseFilter courseFilter){
        return CourseSpecifications.nameContains(courseFilter.name())
                .and(CourseSpecifications.acronymContains(courseFilter.acronym()));
    }


}
