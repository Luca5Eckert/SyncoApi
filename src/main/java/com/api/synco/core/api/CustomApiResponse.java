package com.api.synco.core.api;

import java.time.Instant;

/**
 * Generic API response wrapper for consistent response formatting across all endpoints.
 *
 * <p>This record provides a standardized structure for all API responses, including both
 * successful responses and error responses. It encapsulates metadata such as timestamps,
 * status codes, and messages along with the actual response data.</p>
 *
 * <p>Example usage for successful response:</p>
 * <pre>{@code
 * CustomApiResponse.success(200, "Operation completed successfully", userData);
 * }</pre>
 *
 * <p>Example usage for error response:</p>
 * <pre>{@code
 * CustomApiResponse.error(400, "VALIDATION_ERROR", "Invalid input", "/api/users");
 * }</pre>
 *
 * @param <T> the type of data contained in the response
 * @param timestamp the instant when the response was generated
 * @param status the HTTP status code
 * @param error the error type identifier (null for successful responses)
 * @param message a human-readable message describing the result
 * @param path the request path (typically included for error responses)
 * @param data the response payload (null for error responses or void operations)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record CustomApiResponse<T>(Instant timestamp, int status, String error, String message, String path, T data) {

    /**
     * Creates an error response without data payload.
     *
     * @param status the HTTP status code
     * @param error the error type identifier
     * @param message a human-readable error description
     * @param path the request path where the error occurred
     * @return a new {@link CustomApiResponse} configured for an error response
     */
    public static CustomApiResponse<Void> error(int status, String error, String message, String path){
        return new CustomApiResponse<Void>(Instant.now(), status, error, message, path, null);
    }

    /**
     * Creates a successful response with data payload.
     *
     * @param <T> the type of the data payload
     * @param status the HTTP status code
     * @param message a human-readable success message
     * @param data the response payload
     * @return a new {@link CustomApiResponse} configured for a successful response
     */
    public static <T> CustomApiResponse<T> success(int status, String message, T data){
        return new CustomApiResponse<>(Instant.now(), status, null, message, null, data);
    }

    /**
     * Creates a successful response without data payload.
     *
     * <p>Useful for operations that don't return data, such as delete operations.</p>
     *
     * @param status the HTTP status code
     * @param message a human-readable success message
     * @return a new {@link CustomApiResponse} configured for a successful response without data
     */
    public static CustomApiResponse<Void> success(int status, String message){
        return new CustomApiResponse<Void>(Instant.now(), status, null, message, null, null);
    }

}
