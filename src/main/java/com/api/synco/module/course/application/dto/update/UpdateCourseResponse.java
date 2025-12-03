package com.api.synco.module.course.application.dto.update;

/**
 * Data Transfer Object for course update responses.
 *
 * <p>This record contains the updated course data returned after
 * successfully updating a course.</p>
 *
 * @param id the unique identifier of the updated course
 * @param name the updated course name
 * @param acronym the updated course acronym
 * @param description the updated course description
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UpdateCourseResponse(
        long id,
        String name,
        String acronym,
        String description
) {
}
