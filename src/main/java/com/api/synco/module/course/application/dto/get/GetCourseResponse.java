package com.api.synco.module.course.application.dto.get;

import java.util.List;

/**
 * Data Transfer Object for course retrieval responses.
 *
 * <p>This record contains the full course data returned when
 * retrieving a single course by ID.</p>
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
public record GetCourseResponse(
        long id,
        String name,
        String acronym,
        String description
) {
}
