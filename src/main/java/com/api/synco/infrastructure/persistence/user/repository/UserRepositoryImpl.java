package com.api.synco.infrastructure.persistence.user.repository;

import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.filter.PageUser;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.vo.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Infrastructure implementation of the {@link UserRepository} port.
 *
 * <p>This class implements the domain's repository interface, providing
 * the actual persistence logic using Spring Data JPA. It acts as an
 * adapter between the domain layer and the JPA persistence layer.</p>
 *
 * <p>The implementation delegates all operations to the underlying
 * {@link JpaUserRepository} while handling the conversion between
 * domain-specific types and Spring Data types.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepository
 * @see JpaUserRepository
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    /**
     * Constructs a new user repository implementation.
     *
     * @param jpaUserRepository the JPA repository for user persistence
     */
    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(UserEntity user) {
        jpaUserRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByEmail(Email email) {
        return jpaUserRepository.existsByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserEntity> findById(long idUserAutenticated) {
        return jpaUserRepository.findById(idUserAutenticated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsById(long id) {
        return jpaUserRepository.existsById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(long id) {
        jpaUserRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method converts the domain's {@link PageUser} to Spring Data's
     * {@link PageRequest} for pagination support.</p>
     */
    @Override
    public Page<UserEntity> findAll(Specification<UserEntity> userEntitySpecification, PageUser pageUser) {
        PageRequest pageRequest = PageRequest.of(
                pageUser.pageNumber(),
                pageUser.pageSize()
        );

        return jpaUserRepository.findAll(userEntitySpecification, pageRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserEntity> findByEmail(Email email) {
        return jpaUserRepository.findByEmail(email);
    }

}
