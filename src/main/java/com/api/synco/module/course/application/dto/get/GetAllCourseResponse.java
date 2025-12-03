package com.api.synco.module.course.application.dto.get;

/**
 * Data Transfer Object for paginated course list responses.
 *
 * <p>This record contains course data returned when listing
 * multiple courses with pagination.</p>
 *
 * @param id the unique identifier of the course
 * @param name the course name
 * @param acronym the course acronym
 * @param description the course description
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record GetAllCourseResponse(
        long id,
        String name,
        String acronym,
        String description
) {
}
