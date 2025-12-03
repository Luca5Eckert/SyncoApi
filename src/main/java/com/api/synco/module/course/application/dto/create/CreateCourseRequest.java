package com.api.synco.module.course.application.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for course creation requests.
 *
 * <p>This record encapsulates all the data required to create a new course
 * in the system. All fields are validated using Jakarta Bean Validation.</p>
 *
 * @param name the course name (max 80 characters)
 * @param acronym the course acronym/abbreviation (max 10 characters)
 * @param description the course description (max 150 characters)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record CreateCourseRequest(
        @NotBlank(message = "Course's name can't be blank") @Size(max = 80, message = "Name's size can't be more than 80 caracteres") String name,
        @NotBlank(message = "Course's acronym can't be blank") @Size(max = 10, message = "Acronym's size can't be more than 10 caracteres") String acronym,
        @NotBlank(message = "Course's description can't be blank") @Size(max = 10, message = "Acronym's description can't be more than 150 caracteres") String description
) {
}
