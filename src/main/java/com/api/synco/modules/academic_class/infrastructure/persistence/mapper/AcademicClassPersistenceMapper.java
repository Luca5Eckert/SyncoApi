package com.api.synco.modules.academic_class.infrastructure.persistence.mapper;

import com.api.synco.modules.academic_class.domain.model.AcademicClass;
import com.api.synco.modules.academic_class.domain.model.AcademicClassId;
import com.api.synco.modules.academic_class.infrastructure.persistence.entity.AcademicClassIdJpa;
import com.api.synco.modules.academic_class.infrastructure.persistence.entity.AcademicClassJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between domain models and JPA entities.
 *
 * <p>This mapper handles the transformation between the pure domain
 * layer models (no JPA annotations) and the infrastructure layer
 * JPA entities (with JPA annotations).</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class AcademicClassPersistenceMapper {

    /**
     * Converts a JPA entity to a domain model.
     *
     * @param entity the JPA entity
     * @return the domain model
     */
    public AcademicClass toDomain(AcademicClassJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        AcademicClassId domainId = toDomainId(entity.getId());

        return new AcademicClass(
                domainId,
                entity.getCourse() != null ? entity.getCourse().getId() : 0,
                entity.getTotalHours(),
                entity.getShift()
        );
    }

    /**
     * Converts a JPA ID to a domain ID.
     *
     * @param jpaId the JPA ID
     * @return the domain ID
     */
    public AcademicClassId toDomainId(AcademicClassIdJpa jpaId) {
        if (jpaId == null) {
            return null;
        }
        return new AcademicClassId(jpaId.getCourseId(), jpaId.getNumber());
    }

    /**
     * Converts a domain ID to a JPA ID.
     *
     * @param domainId the domain ID
     * @return the JPA ID
     */
    public AcademicClassIdJpa toJpaId(AcademicClassId domainId) {
        if (domainId == null) {
            return null;
        }
        return new AcademicClassIdJpa(domainId.getCourseId(), domainId.getNumber());
    }
}
