package com.api.synco.module.course.application.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for course update requests.
 *
 * <p>This record encapsulates the data required to update an existing course.</p>
 *
 * @param name the new course name (max 80 characters)
 * @param acronym the new course acronym (max 10 characters)
 * @param description the new course description (max 150 characters)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UpdateCourseRequest(
        @NotBlank(message = "Course's name can't be blank") @Size(max = 80, message = "Name's size can't be more than 80 caracteres") String name,
        @NotBlank(message = "Course's acronym can't be blank") @Size(max = 10, message = "Acronym's size can't be more than 10 caracteres") String acronym,
        @NotBlank(message = "Course's description can't be blank") @Size(max = 10, message = "Acronym's description can't be more than 150 caracteres") String description
) {
}
