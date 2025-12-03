package com.api.synco.module.class_entity.domain;

import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.exception.ClassDomainException;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * Domain entity representing a class within a course.
 *
 * <p>This entity encapsulates class-related data including the associated course,
 * total hours, and shift (morning, afternoon, or evening).</p>
 *
 * <p>A class is identified by a composite key consisting of the course ID
 * and class number.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassEntityId
 * @see CourseEntity
 * @see Shift
 */
@Entity
public class ClassEntity {

    /**
     * The composite identifier for the class.
     */
    @EmbeddedId
    private ClassEntityId id;

    /**
     * The course that this class belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private CourseEntity course;

    /**
     * The total hours for this class.
     */
    private int totalHours;

    /**
     * The shift when this class is scheduled.
     */
    private Shift shift;

    /**
     * Default constructor required by JPA.
     */
    public ClassEntity() {
    }

    /**
     * Constructs a new class entity (without ID).
     *
     * @param course the associated course
     * @param totalHours the total hours
     * @param shift the class shift
     */
    public ClassEntity(CourseEntity course, int totalHours, Shift shift) {
        this.course = course;
        this.totalHours = totalHours;
        this.shift = shift;
    }


    /**
     * Updates the class entity with new values.
     *
     * @param totalHours the new total hours
     * @param shift the new shift
     */
    public void update(int totalHours, Shift shift) {
        setTotalHours(totalHours);
        setShift(shift);
    }

    /**
     * Constructs a new class entity (with ID).
     *
     * @param id the composite identifier
     * @param course the associated course
     * @param totalHours the total hours
     * @param shift the class shift
     */
    public ClassEntity(ClassEntityId id, CourseEntity course, int totalHours, Shift shift) {
        this.id = id;
        this.course = course;
        this.totalHours = totalHours;
        this.shift = shift;
    }

    /**
     * Checks if a user with the given role has permission to modify classes.
     *
     * @param role the user's role
     * @return {@code true} if the user can modify classes, {@code false} otherwise
     */
    public static boolean havePermissionToModify(RoleUser role) {
        return switch (role){
            case ADMIN -> true;
            case USER -> false;
        };
    }

    /**
     * Returns the class identifier.
     *
     * @return the composite class ID
     */
    public ClassEntityId getId() {
        return id;
    }

    /**
     * Returns the associated course.
     *
     * @return the course entity
     */
    public CourseEntity getCourse() {
        return course;
    }

    /**
     * Sets the associated course with validation.
     *
     * @param course the course entity
     * @throws ClassDomainException if the course is null
     */
    public void setCourse(CourseEntity course) {
        if (course == null){
            throw new ClassDomainException("The course can't be null");
        }
        this.course = course;
    }


    /**
     * Returns the total hours.
     *
     * @return the total hours
     */
    public int getTotalHours() {
        return totalHours;
    }

    /**
     * Sets the total hours with validation.
     *
     * @param totalHours the total hours
     * @throws ClassDomainException if hours are negative or exceed 10000
     */
    public void setTotalHours(int totalHours) {
        if (totalHours < 0) {
            throw new ClassDomainException("The total hours cannot be negative.");
        }
        if (totalHours > 10000){
            throw new ClassDomainException("The total hours exceeds the maximum allowed (10000).");
        }
        this.totalHours = totalHours;
    }

    /**
     * Returns the class shift.
     *
     * @return the shift
     */
    public Shift getShift() {
        return shift;
    }

    /**
     * Sets the class shift with validation.
     *
     * @param shift the shift
     * @throws ClassDomainException if the shift is null
     */
    public void setShift(Shift shift) {
        if(shift == null){
            throw new ClassDomainException("The shift can't be null");
        }
        this.shift = shift;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClassEntity that = (ClassEntity) o;
        return totalHours == that.totalHours && Objects.equals(id, that.id) && Objects.equals(course, that.course) && shift == that.shift;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, totalHours, shift);
    }
}
