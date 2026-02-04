package com.api.synco.core.exception;

import com.api.synco.core.api.CustomApiResponse;
import com.api.synco.core.exception.token.TokenException;
import com.api.synco.module.authentication.domain.exception.AuthenticationException;
import com.api.synco.module.class_entity.domain.exception.ClassDomainException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutClassPermisionException;
import com.api.synco.module.course.domain.exception.CourseDomainException;
import com.api.synco.module.user.domain.exception.UserDomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the Synco API.
 *
 * <p>This class provides centralized exception handling across all controllers
 * using Spring's {@link ControllerAdvice} mechanism. It transforms various
 * exception types into standardized API error responses.</p>
 *
 * <p>The handler manages:</p>
 * <ul>
 *   <li>Domain-specific exceptions (User, Course, Authentication)</li>
 *   <li>Token-related exceptions</li>
 *   <li>Data integrity violations</li>
 *   <li>Generic runtime and checked exceptions</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ControllerAdvice
 * @see ExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Entity Exceptions

    /**
     * Handles user domain-related exceptions.
     *
     * @param e the user domain exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 400 status
     */
    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<CustomApiResponse<?>> handlerUserException(UserDomainException e, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        return ResponseEntity.badRequest().body(CustomApiResponse.error(HttpStatus.BAD_REQUEST.value(), "USER_EXCEPTION", e.getMessage(), path));
    }

    /**
     * handles class domain-related exceptions
     *
     * @param e the class domain exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 400 status
     */
    @ExceptionHandler(ClassDomainException.class)
    public ResponseEntity<CustomApiResponse<?>> handleClassException(ClassDomainException e, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        HttpStatus status = switch (e) {
            case com.api.synco.module.class_entity.domain.exception.ClassNotFoundException cnf -> HttpStatus.NOT_FOUND;
            case UserWithoutClassPermisionException cwcp -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };

        return ResponseEntity.status(status).body(CustomApiResponse.error(status.value(), "USER_EXCEPTION", e.getMessage(), path));
    }


    /**
     * handles class domain-related exceptions
     *
     * @param e the class domain exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 400 status
     */
    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<CustomApiResponse<?>> handleClassException(ClassNotFoundException e, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomApiResponse.error(HttpStatus.NOT_FOUND.value(), "USER_EXCEPTION", e.getMessage(), path));
    }

    /**
     * Handles internal authentication service exceptions.
     *
     * <p>These exceptions typically occur when authentication fails due to
     * internal service errors during the authentication process.</p>
     *
     * @param e the internal authentication service exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 401 status
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<CustomApiResponse<?>> handlerInternalAuthenticationException(InternalAuthenticationServiceException e, HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getRequestURI();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(CustomApiResponse.error(
                status.value(),
                "AUTHENTICATION_SERVICE_ERROR",
                "Authentication failed: " + e.getMessage(),
                path)
        );
    }

    /**
     * Handles course domain-related exceptions.
     *
     * @param e the course domain exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 400 status
     */
    @ExceptionHandler(CourseDomainException.class)
    public ResponseEntity<CustomApiResponse<?>> handlerCourseException(CourseDomainException e, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        return ResponseEntity.badRequest().body(CustomApiResponse.error(HttpStatus.BAD_REQUEST.value(), "COURSE_EXCEPTION", e.getMessage(), path));
    }

    /**
     * Handles authentication-related exceptions.
     *
     * <p>These exceptions occur when user credentials are invalid or
     * authentication validation fails.</p>
     *
     * @param e the authentication exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 401 status
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomApiResponse<?>> handlerAuthenticationException(AuthenticationException e, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(CustomApiResponse.error(status.value(), "AUTHENTICATION_EXCEPTION", e.getMessage(), path));
    }

    /**
     * Handles JWT token-related exceptions.
     *
     * <p>These exceptions occur when JWT tokens are invalid, expired,
     * malformed, or have invalid signatures.</p>
     *
     * @param tokenException the token exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 401 status
     */
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<CustomApiResponse<?>> handlerTokenException(TokenException tokenException, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(CustomApiResponse.error(status.value(), "TOKEN_EXCEPTION", tokenException.getMessage(), path));
    }

    // Major Exceptions

    /**
     * Handles uncaught runtime exceptions.
     *
     * <p>This is a fallback handler for any runtime exceptions that are not
     * caught by more specific handlers. It returns a generic error message
     * to avoid exposing internal details.</p>
     *
     * @param e the runtime exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 500 status
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomApiResponse<?>> handlerRuntimeException(RuntimeException e, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(CustomApiResponse.error(status.value(), "RUNTIME_EXCEPTION", "An unexpected error occurred", path));
    }

    /**
     * Handles generic checked exceptions.
     *
     * <p>This is the most generic exception handler, catching any exception
     * not handled by other handlers.</p>
     *
     * @param e the exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 400 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse<?>> handler(Exception e, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        return ResponseEntity.badRequest().body(CustomApiResponse.error(HttpStatus.BAD_REQUEST.value(), "EXCEPTION", "Generic error", path));
    }

    /**
     * Handles data integrity violation exceptions.
     *
     * <p>These exceptions occur when database constraints are violated,
     * such as unique constraint violations or foreign key constraints.</p>
     *
     * @param dataIntegrityViolationException the data integrity violation exception
     * @param httpServletRequest the HTTP request that triggered the exception
     * @return a {@link ResponseEntity} containing the error response with HTTP 409 status
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomApiResponse<?>> handlerDataViolation(DataIntegrityViolationException dataIntegrityViolationException, HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(CustomApiResponse.error(status.value(), "DATA_INTEGRITY_VIOLATION", "The data integrity violation have been violation", path));
    }

}
