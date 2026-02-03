package com.api.synco.module.room.infrastructure.specification;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.filter.RoomFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RoomFilterProvider {

    public Specification<RoomEntity> of(RoomFilter roomFilter) {
        Specification<RoomEntity> specification = Specification.where(null);

        if (roomFilter == null) {
            return specification;
        }

        if (roomFilter.typeRoom() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("typeRoom"), roomFilter.typeRoom()));
        }

        if (roomFilter.number() > 0) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("number"), roomFilter.number()));
        }

        return specification;
    }

}