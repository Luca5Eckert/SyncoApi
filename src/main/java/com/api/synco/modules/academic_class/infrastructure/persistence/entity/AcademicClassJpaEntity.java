package com.api.synco.modules.academic_class.infrastructure.persistence.entity;

import com.api.synco.module.course.domain.CourseEntity;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * JPA entity representing an academic class in the database.
 *
 * <p>This entity is the infrastructure layer representation of the
 * AcademicClass domain model. It contains all JPA annotations and
 * handles the mapping to the database.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Table(name = "academic_class")
public class AcademicClassJpaEntity {

    @EmbeddedId
    private AcademicClassIdJpa id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    private int totalHours;

    @Enumerated(EnumType.STRING)
    private com.api.synco.modules.academic_class.domain.enumerator.Shift shift;

    /**
     * Default constructor required by JPA.
     */
    public AcademicClassJpaEntity() {
    }

    public AcademicClassJpaEntity(AcademicClassIdJpa id, CourseEntity course, int totalHours, 
            com.api.synco.modules.academic_class.domain.enumerator.Shift shift) {
        this.id = id;
        this.course = course;
        this.totalHours = totalHours;
        this.shift = shift;
    }

    public AcademicClassIdJpa getId() {
        return id;
    }

    public void setId(AcademicClassIdJpa id) {
        this.id = id;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public com.api.synco.modules.academic_class.domain.enumerator.Shift getShift() {
        return shift;
    }

    public void setShift(com.api.synco.modules.academic_class.domain.enumerator.Shift shift) {
        this.shift = shift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClassJpaEntity that = (AcademicClassJpaEntity) o;
        return totalHours == that.totalHours && 
               Objects.equals(id, that.id) && 
               Objects.equals(course, that.course) && 
               shift == that.shift;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, totalHours, shift);
    }
}
