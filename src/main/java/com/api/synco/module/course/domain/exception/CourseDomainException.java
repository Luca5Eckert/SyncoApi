package com.api.synco.module.course.domain.exception;

/**
 * Base exception class for course domain-related errors.
 *
 * <p>This exception serves as the parent class for all course-specific
 * exceptions in the domain layer.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public class CourseDomainException extends RuntimeException {

    /**
     * Constructs a new course domain exception with the specified message.
     *
     * @param message the detail message describing the error
     */
    public CourseDomainException(String message) {
        super(message);
    }

}
