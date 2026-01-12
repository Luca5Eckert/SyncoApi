package com.api.synco.modules.class_user.infrastructure.persistence.repository;

import com.api.synco.infrastructure.persistence.class_user.repository.ClassUserRepositoryJpa;
import com.api.synco.infrastructure.persistence.class_user.specification.ClassUserSpecifications;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.filter.ClassUserFilter;
import com.api.synco.module.class_user.domain.filter.PageClassUser;
import com.api.synco.modules.class_user.domain.port.ClassUserRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter implementing the ClassUserRepositoryPort.
 *
 * <p>This class is responsible for:</p>
 * <ul>
 *   <li>Implementing the domain port interface</li>
 *   <li>Converting POJO domain filters to Spring Data Specifications</li>
 *   <li>Delegating persistence operations to Spring Data JPA</li>
 * </ul>
 *
 * <p>This adapter pattern ensures that the domain layer remains pure and
 * unaware of the underlying persistence mechanism (Spring Data JPA).</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassUserRepositoryPort
 * @see ClassUserRepositoryJpa
 * @see ClassUserSpecifications
 */
@Repository
public class ClassUserRepositoryImpl implements ClassUserRepositoryPort {

    private final ClassUserRepositoryJpa classUserRepositoryJpa;

    /**
     * Constructs a new ClassUserRepositoryImpl.
     *
     * @param classUserRepositoryJpa the Spring Data JPA repository
     */
    public ClassUserRepositoryImpl(ClassUserRepositoryJpa classUserRepositoryJpa) {
        this.classUserRepositoryJpa = classUserRepositoryJpa;
    }

    @Override
    public void save(ClassUser classUser) {
        classUserRepositoryJpa.save(classUser);
    }

    @Override
    public boolean existById(ClassUserId classUserId) {
        return classUserRepositoryJpa.existsById(classUserId);
    }

    @Override
    public void deleteById(ClassUserId classUserId) {
        classUserRepositoryJpa.deleteById(classUserId);
    }

    @Override
    public Optional<ClassUser> findById(ClassUserId classUserId) {
        return classUserRepositoryJpa.findById(classUserId);
    }

    /**
     * Finds all class-users matching the filter with pagination.
     *
     * <p>This method converts the pure POJO filter into a Spring Data
     * Specification and executes the query using the JPA repository.</p>
     *
     * @param filter the domain filter containing search criteria
     * @param page the pagination parameters
     * @return a list of class-users matching the criteria
     */
    @Override
    public List<ClassUser> findAll(ClassUserFilter filter, PageClassUser page) {
        Specification<ClassUser> specification = buildSpecification(filter);
        PageRequest pageRequest = PageRequest.of(page.pageNumber(), page.pageSize());

        Page<ClassUser> result = classUserRepositoryJpa.findAll(specification, pageRequest);
        return result.getContent();
    }

    @Override
    public long count(ClassUserFilter filter) {
        Specification<ClassUser> specification = buildSpecification(filter);
        return classUserRepositoryJpa.count(specification);
    }

    /**
     * Builds a Spring Data Specification from the domain filter.
     *
     * <p>This method translates the pure Java filter POJO into the framework-specific
     * Specification objects. This conversion happens only in the infrastructure layer,
     * keeping the domain layer pure.</p>
     *
     * @param filter the domain filter to convert
     * @return the Spring Data Specification
     */
    private Specification<ClassUser> buildSpecification(ClassUserFilter filter) {
        Specification<ClassUser> spec = Specification.where(null);

        if (filter.typeUserClass() != null) {
            spec = spec.and(ClassUserSpecifications.typeUserClassContains(filter.typeUserClass()));
        }

        if (filter.classUserId() != null) {
            spec = spec.and(ClassUserSpecifications.classUserIdContains(filter.classUserId()));
        }

        if (filter.userId() > 0) {
            spec = spec.and(ClassUserSpecifications.userIdContains(filter.userId()));
        }

        return spec;
    }
}
