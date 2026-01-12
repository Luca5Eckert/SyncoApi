package com.api.synco.modules.academic_class.domain.model;

import com.api.synco.modules.academic_class.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.exception.ClassDomainException;
import com.api.synco.module.user.domain.enumerator.RoleUser;

import java.util.Objects;

/**
 * Pure domain model representing an academic class within a course.
 *
 * <p>This is a pure Java domain entity with no framework dependencies
 * (no JPA annotations, no Spring annotations). It encapsulates class-related
 * data including the course reference, total hours, and shift.</p>
 *
 * <p>A class is identified by a composite key consisting of the course ID
 * and class number.</p>
 *
 * <p>The infrastructure layer contains the corresponding JPA entity that
 * maps this domain model to the database.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see AcademicClassId
 * @see Shift
 */
public class AcademicClass {

    private AcademicClassId id;

    private long courseId;

    private int totalHours;

    private Shift shift;

    /**
     * Default constructor.
     */
    public AcademicClass() {
    }

    /**
     * Constructs a new academic class (without ID).
     *
     * @param courseId the associated course ID
     * @param totalHours the total hours
     * @param shift the class shift
     */
    public AcademicClass(long courseId, int totalHours, Shift shift) {
        this.courseId = courseId;
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
     * Constructs a new academic class (with ID).
     *
     * @param id the composite identifier
     * @param courseId the associated course ID
     * @param totalHours the total hours
     * @param shift the class shift
     */
    public AcademicClass(AcademicClassId id, long courseId, int totalHours, Shift shift) {
        this.id = id;
        this.courseId = courseId;
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
    public AcademicClassId getId() {
        return id;
    }

    /**
     * Returns the associated course ID.
     *
     * @return the course ID
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets the associated course ID with validation.
     *
     * @param courseId the course ID
     * @throws ClassDomainException if the course ID is invalid
     */
    public void setCourseId(long courseId) {
        if (courseId <= 0){
            throw new ClassDomainException("The course ID must be positive");
        }
        this.courseId = courseId;
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
        AcademicClass that = (AcademicClass) o;
        return totalHours == that.totalHours && Objects.equals(id, that.id) && courseId == that.courseId && shift == that.shift;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, totalHours, shift);
    }
}
