package com.api.synco.module.class_entity.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Embeddable composite identifier for class entities.
 *
 * <p>This class represents a composite primary key consisting of the course ID
 * and class number, uniquely identifying a class within the system.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassEntity
 */
@Embeddable
public class ClassEntityId {

    /**
     * The course identifier.
     */
    private final long courseId;

    /**
     * The class number within the course.
     */
    private final int number;

    /**
     * Default constructor required by JPA.
     * Sets values to -1 to indicate an uninitialized state.
     */
    public ClassEntityId() {
        this.courseId = -1;
        this.number = -1;
    }

    /**
     * Constructs a new class entity ID.
     *
     * @param courseId the course identifier
     * @param number the class number
     */
    public ClassEntityId(long courseId, int number) {
        this.courseId = courseId;
        this.number = number;
    }

    /**
     * Returns the course identifier.
     *
     * @return the course ID
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Returns the class number.
     *
     * @return the class number
     */
    public int getNumber() {
        return number;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ClassEntityId that = (ClassEntityId) o;

        return courseId == that.courseId && number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, number);
    }
}
