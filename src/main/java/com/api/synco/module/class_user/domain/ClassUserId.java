package com.api.synco.module.class_user.domain;


import com.api.synco.module.class_entity.domain.ClassEntityId;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClassUserId implements Serializable {

    private long userId;
    private ClassEntityId classEntityId;

    public ClassUserId() {
    }

    public ClassUserId(long userId, ClassEntityId classEntityId) {
        this.userId = userId;
        this.classEntityId = classEntityId;
    }

    public long getUserId() {
        return userId;
    }

    public ClassEntityId getClassEntityId() {
        return classEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClassUserId that = (ClassUserId) o;
        return userId == that.userId && Objects.equals(classEntityId, that.classEntityId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, classEntityId);
    }
}
