package com.api.synco.module.class_user.domain;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.user.domain.UserEntity;
import jakarta.persistence.*;

import java.util.Objects;

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

    public ClassUser() {
    }

    public ClassUser(ClassUserId classUserId, ClassEntity classEntity, UserEntity userEntity, TypeUserClass typeUserClass) {
        this.classUserId = classUserId;
        this.classEntity = classEntity;
        this.userEntity = userEntity;
        this.typeUserClass = typeUserClass;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public TypeUserClass getTypeUserClass() {
        return typeUserClass;
    }

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

    public void update(TypeUserClass typeUserClass) {
        setTypeUserClass(typeUserClass);
    }
}
