package com.api.synco.core.persistence.user.repository;

import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import com.api.synco.module.user.infrastructure.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserRepositoryImpl.class)
class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private UserEntity user;
    private Email email;

    @BeforeEach
    public void setup(){
        email = new Email("john@example.com");
        user = new UserEntity(new Name("John Doe"), email, "encodedPassword", RoleUser.USER);
    }

    @DisplayName("Should save user successfully")
    @Test
    void shouldSave() {
        // act
        userRepository.save(user);
        entityManager.flush();

        // assert
        var savedUser = entityManager.find(UserEntity.class, user.getId());
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getName().value()).isEqualTo("John Doe");
        assertThat(savedUser.getEmail().address()).isEqualTo("john@example.com");
        assertThat(savedUser.getRole()).isEqualTo(RoleUser.USER);
    }

    @DisplayName("Should return true when email exists")
    @Test
    void shouldReturnTrueWhenExistsByEmail() {
        // arrange
        entityManager.persist(user);
        entityManager.flush();

        // act
        boolean exists = userRepository.existsByEmail(email);

        // assert
        assertThat(exists).isTrue();
    }

    @DisplayName("Should return false when email does not exist")
    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        // act
        boolean exists = userRepository.existsByEmail(new Email("nonexistent@example.com"));

        // assert
        assertThat(exists).isFalse();
    }

    @DisplayName("Should return user when found by ID")
    @Test
    void shouldReturnUserWhenFoundById() {
        // arrange
        entityManager.persist(user);
        entityManager.flush();
        long id = user.getId();

        // act
        var foundUser = userRepository.findById(id);

        // assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(id);
        assertThat(foundUser.get().getEmail().address()).isEqualTo("john@example.com");
    }

    @DisplayName("Should return true when user exists by ID")
    @Test
    void shouldReturnTrueWhenExistsById() {
        // arrange
        entityManager.persist(user);
        entityManager.flush();
        long id = user.getId();

        // act
        boolean exists = userRepository.existsById(id);

        // assert
        assertThat(exists).isTrue();
    }

    @DisplayName("Should delete user when found by ID")
    @Test
    void shouldDeleteWhenFoundById() {
        // arrange
        entityManager.persist(user);
        entityManager.flush();
        long id = user.getId();

        // act
        userRepository.deleteById(id);
        entityManager.flush();

        // assert
        var deletedUser = entityManager.find(UserEntity.class, id);
        assertThat(deletedUser).isNull();
    }


    @DisplayName("Should return user when found by email")
    @Test
    void shouldReturnUserWhenFoundByEmail() {
        // arrange
        entityManager.persist(user);
        entityManager.flush();

        // act
        var foundUser = userRepository.findByEmail(email);

        // assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail().address()).isEqualTo("john@example.com");
        assertThat(foundUser.get().getName().value()).isEqualTo("John Doe");
    }

}