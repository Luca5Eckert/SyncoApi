package com.api.synco.module.course.application.dto.create;

/**
 * Data Transfer Object for course creation responses.
 *
 * <p>This record contains the data returned after successfully creating
 * a new course in the system.</p>
 *
 * @param id the unique identifier assigned to the new course
 * @param name the course name
 * @param acronym the course acronym
 * @param description the course description
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record CreateCourseResponse(
        long id,
        String name,
        String acronym,
        String description
) {
}
