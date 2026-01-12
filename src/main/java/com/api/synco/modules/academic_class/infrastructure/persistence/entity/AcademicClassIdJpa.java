package com.api.synco.modules.academic_class.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * JPA Embeddable composite identifier for academic class entities.
 *
 * <p>This is the infrastructure layer representation of the AcademicClassId
 * domain value object. It contains JPA annotations for database mapping.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
@Embeddable
public class AcademicClassIdJpa implements Serializable {

    private long courseId;

    private int number;

    /**
     * Default constructor required by JPA.
     */
    public AcademicClassIdJpa() {
    }

    public AcademicClassIdJpa(long courseId, int number) {
        this.courseId = courseId;
        this.number = number;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClassIdJpa that = (AcademicClassIdJpa) o;
        return courseId == that.courseId && number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, number);
    }
}
