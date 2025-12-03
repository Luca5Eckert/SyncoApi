package com.api.synco.module.class_user.domain;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.user.domain.UserEntity;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Domain entity representing the association between a class and a user.
 *
 * <p>This entity defines the relationship between classes and users, including
 * the type of association (e.g., STUDENT, TEACHER).</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassUserId
 * @see ClassEntity
 * @see UserEntity
 * @see TypeUserClass
 */
@Entity
public class ClassUser {

    @EmbeddedId
    private ClassUserId classUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("classEntityId")
    private ClassEntity classEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private TypeUserClass typeUserClass;

    /**
     * Default constructor required by JPA.
     */
    public ClassUser() {
    }

    /**
     * Constructs a new class-user association.
     *
     * @param classUserId the composite identifier
     * @param classEntity the associated class
     * @param userEntity the associated user
     * @param typeUserClass the type of association
     */
    public ClassUser(ClassUserId classUserId, ClassEntity classEntity, UserEntity userEntity, TypeUserClass typeUserClass) {
        this.classUserId = classUserId;
        this.classEntity = classEntity;
        this.userEntity = userEntity;
        this.typeUserClass = typeUserClass;
    }

    /**
     * Returns the associated class entity.
     *
     * @return the class entity
     */
    public ClassEntity getClassEntity() {
        return classEntity;
    }

    /**
     * Sets the associated class entity.
     *
     * @param classEntity the class entity
     */
    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    /**
     * Returns the associated user entity.
     *
     * @return the user entity
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * Sets the associated user entity.
     *
     * @param userEntity the user entity
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * Returns the type of class-user association.
     *
     * @return the association type
     */
    public TypeUserClass getTypeUserClass() {
        return typeUserClass;
    }

    /**
     * Sets the type of class-user association.
     *
     * @param typeUserClass the association type
     */
    public void setTypeUserClass(TypeUserClass typeUserClass) {
        this.typeUserClass = typeUserClass;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClassUser classUser = (ClassUser) o;
        return Objects.equals(classUserId, classUser.classUserId) && Objects.equals(classEntity, classUser.classEntity) && Objects.equals(userEntity, classUser.userEntity) && typeUserClass == classUser.typeUserClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(classUserId, classEntity, userEntity, typeUserClass);
    }

    /**
     * Updates the association type.
     *
     * @param typeUserClass the new association type
     */
    public void update(TypeUserClass typeUserClass) {
        setTypeUserClass(typeUserClass);
    }
}
