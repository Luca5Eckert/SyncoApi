package com.api.synco.module.class_user.domain;


import com.api.synco.module.class_entity.domain.ClassEntityId;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Embeddable composite identifier for class-user associations.
 *
 * <p>This class represents a composite primary key consisting of the user ID
 * and class entity ID, uniquely identifying a class-user relationship.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassUser
 * @see ClassEntityId
 */
@Embeddable
public class ClassUserId implements Serializable {

    /**
     * The user identifier.
     */
    private long userId;

    /**
     * The class entity identifier.
     */
    private ClassEntityId classEntityId;

    /**
     * Default constructor required by JPA.
     */
    public ClassUserId() {
    }

    /**
     * Constructs a new class-user ID.
     *
     * @param userId the user identifier
     * @param classEntityId the class entity identifier
     */
    public ClassUserId(long userId, ClassEntityId classEntityId) {
        this.userId = userId;
        this.classEntityId = classEntityId;
    }

    /**
     * Returns the user identifier.
     *
     * @return the user ID
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Returns the class entity identifier.
     *
     * @return the class entity ID
     */
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
