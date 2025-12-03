package com.api.synco.module.course.domain;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.course.domain.exception.acronym.CourseAcronymBlankException;
import com.api.synco.module.course.domain.exception.name.CourseNameBlankException;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import jakarta.persistence.*;

import java.util.List;

/**
 * Domain entity representing a course in the academic management system.
 *
 * <p>This entity encapsulates course-related data including the course name,
 * acronym, description, and associated class entities.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassEntity
 */
@Entity
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String acronym;

    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ClassEntity> classEntities;

    /**
     * Default constructor required by JPA.
     */
    public CourseEntity() {
    }

    /**
     * Constructs a new course with the specified attributes including ID.
     *
     * @param id the course's unique identifier
     * @param name the course name
     * @param acronym the course acronym
     * @param description the course description
     */
    public CourseEntity(long id, String name, String acronym, String description) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.description = description;
    }

    /**
     * Constructs a new course with the specified attributes (without ID).
     *
     * @param name the course name
     * @param acronym the course acronym
     * @param description the course description
     */
    public CourseEntity(String name, String acronym, String description) {
        this.name = name;
        this.acronym = acronym;
        this.description = description;
    }

    /**
     * Returns the course's unique identifier.
     *
     * @return the course ID
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the course name.
     *
     * @return the course name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name with validation.
     *
     * @param name the new course name
     * @throws CourseNameBlankException if the name is null or blank
     */
    public void setName(String name) {
        if(name == null || name.isBlank()){
            throw new CourseNameBlankException();
        }
        this.name = name;
    }

    /**
     * Returns the course acronym.
     *
     * @return the course acronym
     */
    public String getAcronym() {
        return acronym;
    }

    /**
     * Sets the course acronym with validation.
     *
     * @param acronym the new course acronym
     * @throws CourseAcronymBlankException if the acronym is null or blank
     */
    public void setAcronym(String acronym) {
        if(acronym == null || acronym.isBlank()){
            throw new CourseAcronymBlankException();
        }
        this.acronym = acronym;
    }

    /**
     * Sets the course ID.
     *
     * @param id the new course ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the course description.
     *
     * @return the course description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the course description.
     *
     * @param description the new course description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the list of class entities associated with this course.
     *
     * @return the list of class entities
     */
    public List<ClassEntity> getClassEntities() {
        return classEntities;
    }

    /**
     * Sets the list of class entities for this course.
     *
     * @param classEntities the list of class entities
     */
    public void setClassEntities(List<ClassEntity> classEntities) {
        this.classEntities = classEntities;
    }

    /**
     * Checks if a user with the given role has permission to modify courses.
     *
     * @param roleUser the user's role
     * @return {@code true} if the user can modify courses, {@code false} otherwise
     */
    public static boolean havePermissionToModify(RoleUser roleUser){
        return switch (roleUser){
            case ADMIN -> true;
            default -> false;
        };
    }


}
