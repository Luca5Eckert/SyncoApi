package com.api.synco.module.class_user.application.controller;

import com.api.synco.module.authentication.domain.port.UserAuthenticationService;
import com.api.synco.core.api.CustomApiResponse;
import com.api.synco.module.class_user.application.dto.create.CreateClassUserRequest;
import com.api.synco.module.class_user.application.dto.create.CreateClassUserResponse;
import com.api.synco.module.class_user.application.dto.get.GetAllClassUserResponse;
import com.api.synco.module.class_user.application.dto.get.GetClassUserResponse;
import com.api.synco.module.class_user.application.dto.update.UpdateClassUserRequest;
import com.api.synco.module.class_user.application.dto.update.UpdateClassUserResponse;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.class_user.domain.service.ClassUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Class-User associations (enrollments).
 *
 * <p>This controller provides endpoints for creating, reading, updating, and
 * deleting associations between Users and Course Classes. It handles the
 * lifecycle of student/teacher enrollments.</p>
 *
 * <p>The controller supports:</p>
 * <ul>
 * <li>Enrollment creation (linking a user to a class)</li>
 * <li>Enrollment cancellation (deletion)</li>
 * <li>Status/Data updates for an enrollment</li>
 * <li>Retrieval by composite key (Course + Class + User)</li>
 * <li>Paginated listing with comprehensive filters</li>
 * </ul>
 *
 * @author SyncoTeam
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassUserService
 */
@RestController
@RequestMapping("/api/class-users")
@Tag(name = "Class Users (Enrollments)", description = "Endpoints for managing user enrollments in classes")
@SecurityRequirement(name = "bearer-jwt")
@Validated
public class ClassUserController {

    private final UserAuthenticationService authenticationService;
    private final ClassUserService classUserService;

    /**
     * Constructs a new class-user controller.
     *
     * @param authenticationService the service for accessing authenticated user information
     * @param classUserService the service for class-user business logic
     */
    public ClassUserController(UserAuthenticationService authenticationService, ClassUserService classUserService) {
        this.authenticationService = authenticationService;
        this.classUserService = classUserService;
    }

    /**
     * Creates a new class-user association (enrollment).
     *
     * <p>Requires ADMIN role. Used to enroll a specific user into a class.</p>
     *
     * @param createClassUserRequest the request containing enrollment details (course, class, type, etc.)
     * @param userId the ID of the user being enrolled
     * @return the created enrollment data with HTTP 201 status
     */
    @Operation(
            summary = "Create enrollment",
            description = "Creates a new association between a user and a class. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Enrollment created successfully.",
                    content = @Content(schema = @Schema(implementation = CreateClassUserResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.", content = @Content),
            @ApiResponse(responseCode = "404", description = "User or Class not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already enrolled in this class.", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomApiResponse<CreateClassUserResponse>> create(
            @Valid @RequestBody CreateClassUserRequest createClassUserRequest
    ) {
        long userId = authenticationService.getAuthenticatedUserId();

        CreateClassUserResponse response = classUserService.create(createClassUserRequest, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CustomApiResponse.success(201, "Enrollment created successfully", response));
    }

    /**
     * Updates an existing enrollment.
     *
     * <p>Identifies the enrollment by the composite key: Course ID, Class Number, and User ID.</p>
     *
     * @param courseId the course identifier
     * @param classNumber the class number identifier
     * @param userId the user identifier (the student/teacher)
     * @param request the update data
     * @return the updated enrollment data
     */
    @Operation(
            summary = "Update enrollment",
            description = "Updates an existing class-user association. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Enrollment updated successfully.",
                    content = @Content(schema = @Schema(implementation = UpdateClassUserResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Enrollment not found.", content = @Content)
    })
    @PatchMapping("/courses/{courseId}/classes/{classNumber}/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomApiResponse<UpdateClassUserResponse>> update(
            @PathVariable long courseId,
            @PathVariable int classNumber,
            @PathVariable long userId,
            @Valid @RequestBody UpdateClassUserRequest request
    ) {
        long authenticatedUserId = authenticationService.getAuthenticatedUserId();

        UpdateClassUserResponse response = classUserService.update(
                request,
                courseId,
                classNumber,
                userId,
                authenticatedUserId
        );

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(CustomApiResponse.success(202, "Enrollment updated successfully", response));
    }

    /**
     * Deletes (un-enrolls) a user from a class.
     *
     * @param courseId the course identifier
     * @param classNumber the class number identifier
     * @param userId the user identifier to be removed
     * @return success message with HTTP 202 status
     */
    @Operation(
            summary = "Delete enrollment",
            description = "Removes the association between a user and a class. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Enrollment deleted successfully.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Enrollment not found.", content = @Content)
    })
    @DeleteMapping("/courses/{courseId}/classes/{classNumber}/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomApiResponse<Void>> delete(
            @PathVariable long courseId,
            @PathVariable int classNumber,
            @PathVariable long userId
    ) {
        long authenticatedUserId = authenticationService.getAuthenticatedUserId();

        classUserService.delete(courseId, classNumber, userId, authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(CustomApiResponse.success(202, "Enrollment deleted successfully"));
    }

    /**
     * Retrieves a specific enrollment detail.
     *
     * @param courseId the course identifier
     * @param classNumber the class number identifier
     * @param userId the user identifier
     * @return the enrollment details
     */
    @Operation(
            summary = "Get specific enrollment",
            description = "Retrieves details of a specific user enrollment in a class."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment found.",
                    content = @Content(schema = @Schema(implementation = GetClassUserResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Enrollment not found.", content = @Content)
    })
    @GetMapping("/courses/{courseId}/classes/{classNumber}/users/{userId}")
    public ResponseEntity<CustomApiResponse<GetClassUserResponse>> get(
            @PathVariable long courseId,
            @PathVariable int classNumber,
            @PathVariable long userId
    ) {
        GetClassUserResponse response = classUserService.get(courseId, classNumber, userId);

        return ResponseEntity
                .ok(CustomApiResponse.success(200, "Enrollment details retrieved", response));
    }

    /**
     * Retrieves a paginated list of class-user associations with filters.
     *
     * @param userId filter by user ID (optional)
     * @param courseId filter by course ID (optional)
     * @param numberClass filter by class number (optional)
     * @param typeUserClass filter by user type (e.g. STUDENT, TEACHER) (optional)
     * @param pageNumber the page number to retrieve (0-based)
     * @param pageSize the number of records per page
     * @return a list of enrollments matching the criteria
     */
    @Operation(
            summary = "Get all enrollments",
            description = "Retrieves a paginated list of enrollments based on filters."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of enrollments retrieved.",
                    content = @Content(schema = @Schema(implementation = GetAllClassUserResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<GetAllClassUserResponse>>> getAll(
            @Parameter(description = "Filter by User ID")
            @RequestParam(required = false, defaultValue = "0") long userId,

            @Parameter(description = "Filter by Course ID")
            @RequestParam(required = false, defaultValue = "0") long courseId,

            @Parameter(description = "Filter by Class Number")
            @RequestParam(required = false, defaultValue = "0") int numberClass,

            @Parameter(description = "Filter by User Type (STUDENT, TEACHER, etc)")
            @RequestParam(required = false) TypeUserClass typeUserClass,

            @Parameter(description = "Page number (starts at 0)")
            @RequestParam(defaultValue = "0") @Min(0) int pageNumber,

            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") @Min(1) int pageSize
    ) {
        List<GetAllClassUserResponse> listResponse = classUserService.getAll(
                userId,
                courseId,
                numberClass,
                typeUserClass,
                pageNumber,
                pageSize
        );

        return ResponseEntity
                .ok(CustomApiResponse.success(200, "Enrollments list retrieved", listResponse));
    }
}