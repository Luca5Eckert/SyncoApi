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

@Entity
public class ClassEntity {

    @EmbeddedId
    private ClassEntityId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private CourseEntity course;

    private int totalHours;

    private Shift shift;

    public ClassEntity() {
    }

    public ClassEntity(CourseEntity course, int totalHours, Shift shift) {
        this.course = course;
        this.totalHours = totalHours;
        this.shift = shift;
    }


    public void update(int totalHours, Shift shift) {
        setTotalHours(totalHours);
        setShift(shift);
    }

    public ClassEntity(ClassEntityId id, CourseEntity course, int totalHours, Shift shift) {
        this.id = id;
        this.course = course;
        this.totalHours = totalHours;
        this.shift = shift;
    }

    public static boolean havePermissionToModify(RoleUser role) {
        return switch (role){
            case ADMIN -> true;
            case USER -> false;
        };
    }

    public ClassEntityId getId() {
        return id;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        if (course == null){
            throw new ClassDomainException("The course can't be null");
        }
        this.course = course;
    }


    public int getTotalHours() {
        return totalHours;
    }
    public void setTotalHours(int totalHours) {
        if (totalHours < 0) {
            throw new ClassDomainException("The total hours cannot be negative.");
        }
        if (totalHours > 10000){
            throw new ClassDomainException("The total hours exceeds the maximum allowed (10000).");
        }
        this.totalHours = totalHours;
    }

    public Shift getShift() {
        return shift;
    }

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
