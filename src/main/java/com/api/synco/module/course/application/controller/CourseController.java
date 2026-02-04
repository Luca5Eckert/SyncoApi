package com.api.synco.module.course.application.controller;

import com.api.synco.module.authentication.domain.port.UserAuthenticationService;
import com.api.synco.core.api.CustomApiResponse;
import com.api.synco.module.course.application.dto.create.CreateCourseRequest;
import com.api.synco.module.course.application.dto.create.CreateCourseResponse;
import com.api.synco.module.course.application.dto.delete.DeleteCourseRequest;
import com.api.synco.module.course.application.dto.get.GetAllCourseResponse;
import com.api.synco.module.course.application.dto.get.GetCourseResponse;
import com.api.synco.module.course.application.dto.update.UpdateCourseRequest;
import com.api.synco.module.course.application.dto.update.UpdateCourseResponse;
import com.api.synco.module.course.domain.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for course management operations.
 *
 * <p>This controller provides endpoints for creating, reading, updating, and
 * deleting courses in the system. Most operations require authentication and
 * ADMIN role.</p>
 *
 * <p>The controller supports:</p>
 * <ul>
 *   <li>Course creation (ADMIN only)</li>
 *   <li>Course deletion (ADMIN only)</li>
 *   <li>Course updates (ADMIN only)</li>
 *   <li>Course retrieval by ID</li>
 *   <li>Paginated course listing with filters</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see CourseService
 */
@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "Endpoints for the course management")
@SecurityRequirement(name = "bearer-jwt")
public class CourseController {

    private final UserAuthenticationService authenticationService;

    private final CourseService courseService;

    /**
     * Constructs a new course controller.
     *
     * @param authenticationService the service for accessing authenticated user information
     * @param courseService the service for course business logic
     */
    public CourseController(UserAuthenticationService authenticationService, CourseService courseService) {
        this.authenticationService = authenticationService;
        this.courseService = courseService;
    }


    /**
     * Creates a new course in the system.
     *
     * <p>This endpoint requires ADMIN role. The authenticated user must have
     * permission to create new courses.</p>
     *
     * @param createCourseRequest the request containing course data
     * @return the created course data with HTTP 201 status
     */
    @Operation(
            summary = "Create the new course",
            description = "Creates a new course in the system. Requires authentication"
    )
    @ApiResponses ( value = {
            @ApiResponse (
                    responseCode = "201",
                    description = "The course is created success.",
                    content = @Content(schema = @Schema(implementation = CreateCourseResponse.class))
            ),
            @ApiResponse (
                    responseCode = "400",
                    description = "The course is not unique.",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "400",
                    description = "The user don't have permission to create course.",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
        }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomApiResponse<CreateCourseResponse>> create(@RequestBody @Valid CreateCourseRequest createCourseRequest){
        long idUser = authenticationService.getAuthenticatedUserId();

        var response = courseService.create(createCourseRequest, idUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.success(201, "The course is created success", response));
    }


    /**
     * Deletes a course from the system.
     *
     * <p>This endpoint requires ADMIN role.</p>
     *
     * @param id the unique identifier of the course to delete
     * @return success message with HTTP 202 status
     */
    @Operation(
            summary = "Delete the course",
            description = "Delete the course in the System. Requires authentication"
    )
    @ApiResponses ( value = {
            @ApiResponse (
                    responseCode = "202",
                    description = "The course is deleted success.",
                    content = @Content(schema = @Schema(implementation = CreateCourseResponse.class))
            ),
            @ApiResponse (
                    responseCode = "400",
                    description = "The user don't have permission to delete course.",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomApiResponse<Void>> delete(@PathVariable long id){
        DeleteCourseRequest request = new DeleteCourseRequest(id);

        long idUserAuthenticated = authenticationService.getAuthenticatedUserId();

        courseService.delete(request, idUserAuthenticated);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(CustomApiResponse.success(202, "The course is deleted success"));
    }

    /**
     * Updates an existing course.
     *
     * <p>This endpoint requires ADMIN role.</p>
     *
     * @param updateCourseRequest the request containing updated course data
     * @param id the unique identifier of the course to update
     * @return the updated course data with HTTP 202 status
     */
    @Operation(
            summary = "Update the course",
            description = "Update the course in the System. Requires authentication"
    )
    @ApiResponses ( value = {
            @ApiResponse (
                    responseCode = "202",
                    description = "The course is updated success.",
                    content = @Content(schema = @Schema(implementation = CreateCourseResponse.class))
            ),
            @ApiResponse (
                    responseCode = "400",
                    description = "The user don't have permission to update the course.",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content
            )
    }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomApiResponse<UpdateCourseResponse>> update(
            @Valid @RequestBody UpdateCourseRequest updateCourseRequest,
            @PathVariable long id){

        long idUser = authenticationService.getAuthenticatedUserId();

        var response = courseService.update(updateCourseRequest, id, idUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(CustomApiResponse.success(202, "THe course is updated success", response));
    }

    /**
     * Retrieves a paginated list of courses with optional filters.
     *
     * @param name filter by name containing this value (optional)
     * @param acronym filter by acronym containing this value (optional)
     * @param pageNumber the page number to retrieve (0-based)
     * @param pageSize the number of courses per page
     * @return a list of courses matching the criteria
     */
    @Operation(
            summary = "Get All Courses",
            description = "Get all courses following the filters"
    )
    @ApiResponses ( value = {
            @ApiResponse (
                    responseCode = "200",
                    description = "Get all users.",
                    content = @Content(schema = @Schema(implementation = GetCourseResponse.class))
            )
    })
    @GetMapping()
    public ResponseEntity<CustomApiResponse<List<GetAllCourseResponse>>> getALl(
            @RequestParam(value = "name", required = false)
            @Parameter(description = "Filters by name containing the value")
            String name,
            @RequestParam(value = "acroym", required = false)
            @Parameter(description = "Filters by acronym containing the value")
            String acronym,
            @RequestParam(value = "page", defaultValue = "0")
            @Parameter(description = "Page number (starts at 0)")
            int pageNumber,
            @RequestParam(value = "size", defaultValue = "10")
            @Parameter(description = "Page size (max. 50, default 10)")
            int pageSize
    ){
        var listResponse = courseService.getAll(name, acronym, pageNumber, pageSize);

        return ResponseEntity.ok(CustomApiResponse.success(200, "Get all courses.", listResponse));

    }

    /**
     * Retrieves a specific course by its unique identifier.
     *
     * @param id the unique identifier of the course to retrieve
     * @return the course data with HTTP 200 status
     */
    @Operation(
            summary = "Get a Course",
            description = "Get the course "
    )
    @ApiResponses ( value = {
            @ApiResponse (
                    responseCode = "200",
                    description = "Get user.",
                    content = @Content(schema = @Schema(implementation = GetCourseResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<GetCourseResponse>> get(@PathVariable long id){
        var response = courseService.getCourse(id);

        return ResponseEntity.ok(CustomApiResponse.success(200, "Get the course", response));
    }


}
