package com.api.synco.modules.academic_class.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Pure domain value object representing the composite identifier for academic classes.
 *
 * <p>This class represents a composite primary key consisting of the course ID
 * and class number, uniquely identifying a class within the system.</p>
 *
 * <p>This is a pure Java class with no framework dependencies.
 * The infrastructure layer contains the corresponding JPA Embeddable class.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see AcademicClass
 */
public class AcademicClassId implements Serializable {

    private final long courseId;

    private final int number;

    /**
     * Default constructor required by JPA.
     * Sets values to -1 to indicate an uninitialized state.
     */
    public AcademicClassId() {
        this.courseId = -1;
        this.number = -1;
    }

    /**
     * Constructs a new class entity ID.
     *
     * @param courseId the course identifier
     * @param number the class number
     */
    public AcademicClassId(long courseId, int number) {
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

        AcademicClassId that = (AcademicClassId) o;

        return courseId == that.courseId && number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, number);
    }
}
