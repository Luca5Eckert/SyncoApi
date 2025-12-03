package com.api.synco.module.course.application.dto.delete;

import com.api.synco.module.user.application.exception.UserIdInvalidException;

/**
 * Data Transfer Object for course deletion requests.
 *
 * <p>This record contains the identifier of the course to be deleted.
 * Validation ensures the ID is a positive number.</p>
 *
 * @param id the unique identifier of the course to delete (must be >= 1)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @throws IllegalArgumentException if the ID is less than 1
 */
public record DeleteCourseRequest(long id) {

    /**
     * Constructs a new deletion request with validation.
     *
     * @param id the course ID to delete
     * @throws IllegalArgumentException if the ID is invalid
     */
    public DeleteCourseRequest {
        if(id < 1){
            throw new IllegalArgumentException("User's id is invalid");
        }
    }

}
