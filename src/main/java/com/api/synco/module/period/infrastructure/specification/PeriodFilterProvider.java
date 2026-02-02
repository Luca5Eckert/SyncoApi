package com.api.synco.module.period.infrastructure.specification;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.filter.PeriodFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PeriodFilterProvider {

    public Specification<PeriodEntity> of(PeriodFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.teacherId() > 0) {
                predicates.add(cb.equal(root.get("teacher").get("id"), filter.teacherId()));
            }

            if (filter.roomId() > 0) {
                predicates.add(cb.equal(root.get("room").get("id"), filter.roomId()));
            }

            if (filter.classId() > 0) {
                predicates.add(cb.equal(root.get("classEntity").get("id"), filter.classId()));
            }

            if (filter.typePeriod() != null) {
                predicates.add(cb.equal(root.get("typePeriod"), filter.typePeriod()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

    }

}