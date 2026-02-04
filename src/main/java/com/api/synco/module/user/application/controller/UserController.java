package com.api.synco.module.user.application.controller;

import com.api.synco.module.authentication.domain.port.UserAuthenticationService;
import com.api.synco.core.api.CustomApiResponse;
import com.api.synco.module.user.application.dto.create.UserCreateRequest;
import com.api.synco.module.user.application.dto.create.UserCreateResponse;
import com.api.synco.module.user.application.dto.delete.UserDeleteRequest;
import com.api.synco.module.user.application.dto.edit.UserEditRequest;
import com.api.synco.module.user.application.dto.edit.UserEditResponse;
import com.api.synco.module.user.application.dto.get.UserGetResponse;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.filter.PageUser;
import com.api.synco.module.user.domain.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * REST controller for user management operations.
 *
 * <p>This controller provides endpoints for creating, reading, updating, and
 * deleting users in the system. All endpoints require authentication via JWT.</p>
 *
 * <p>The controller supports:</p>
 * <ul>
 *   <li>User creation with role assignment</li>
 *   <li>User deletion (with permission checks)</li>
 *   <li>User profile editing</li>
 *   <li>User retrieval by ID</li>
 *   <li>Paginated user listing with filters</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserService
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for user management")
@SecurityRequirement(name = "bearer-jwt")
public class UserController {

    private final UserService userService;
    private final UserAuthenticationService authenticationService;

    /**
     * Constructs a new user controller.
     *
     * @param userService the service for user business logic
     * @param authenticationService the service for accessing authenticated user information
     */
    public UserController(UserService userService, UserAuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    /**
     * Creates a new user in the system.
     *
     * <p>This endpoint requires administrative privileges. The authenticated user
     * must have permission to create new users.</p>
     *
     * @param userCreateRequest the request containing user data
     * @return the created user data with HTTP 201 status
     */
    @PostMapping
    @Operation(
            summary = "Create new user",
            description = "Creates a new user in the system. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserCreateResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content)
    })
    public ResponseEntity<CustomApiResponse<UserCreateResponse>> create(@RequestBody @Valid UserCreateRequest userCreateRequest){
        long userId = authenticationService.getAuthenticatedUserId();

        var response = userService.create(userCreateRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.success(HttpStatus.CREATED.value(), "User created successfully", response));
    }

    /**
     * Deletes a user from the system.
     *
     * <p>Only users with appropriate permissions can delete other users.
     * A user cannot delete themselves if they are the last administrator.</p>
     *
     * @param userDeleteRequest the request containing the user ID to delete
     * @return success message with HTTP 202 status
     */
    @DeleteMapping
    @Operation(
            summary = "Delete user",
            description = "Removes a user from the system. Only the user themselves or an administrator can delete."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "User deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "Not authenticated",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to delete this user",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content)
    })
    public ResponseEntity<CustomApiResponse<String>> delete(@RequestBody @Valid UserDeleteRequest userDeleteRequest){
        long idUserAutenticated = authenticationService.getAuthenticatedUserId();

        userService.delete(userDeleteRequest, idUserAutenticated);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(CustomApiResponse.success(HttpStatus.ACCEPTED.value(), "User deleted with success", null));
    }

    /**
     * Updates an existing user's information.
     *
     * <p>Only users with appropriate permissions can edit other users' profiles.
     * Users can always edit their own profile information.</p>
     *
     * @param userEditRequest the request containing updated user data
     * @return the updated user data with HTTP 202 status
     */
    @PatchMapping
    @Operation(
            summary = "Edit user",
            description = "Updates a user's information. Only the user themselves or an administrator can edit."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = UserEditResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data provided",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No permission to edit this user",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public ResponseEntity<CustomApiResponse<UserEditResponse>> edit(@RequestBody @Valid UserEditRequest userEditRequest){
        long idUserAutenticated = authenticationService.getAuthenticatedUserId();

        var response = userService.edit(userEditRequest, idUserAutenticated);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(CustomApiResponse.success(HttpStatus.ACCEPTED.value(), "User updated successfully", response));
    }


    /**
     * Retrieves a specific user by their unique identifier.
     *
     * @param id the unique identifier of the user to retrieve
     * @return the user data with HTTP 200 status
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Search user by ID",
            description = "Returns the data for a specific user by their ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserGetResponse.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public ResponseEntity<CustomApiResponse<UserGetResponse>> get(
            @Parameter(description = "User ID", required = true)
            @PathVariable("id") long id){

        var user = userService.get(id);

        return ResponseEntity.ok(CustomApiResponse.success(HttpStatus.OK.value(), "User found", user));
    }

    /**
     * Retrieves a paginated list of users with optional filters.
     *
     * <p>This endpoint supports filtering by:</p>
     * <ul>
     *   <li>Name (partial match, case-insensitive)</li>
     *   <li>Email (partial match, case-insensitive)</li>
     *   <li>Role (exact match)</li>
     *   <li>Creation date range</li>
     * </ul>
     *
     * @param name filter by name containing this value (optional)
     * @param email filter by email containing this value (optional)
     * @param role filter by user role (optional)
     * @param createAt filter users created from this date onwards (optional)
     * @param updateAt filter users updated up to this date (optional)
     * @param pageNumber the page number to retrieve (0-based)
     * @param pageSize the number of users per page
     * @return a list of users matching the criteria
     */
    @GetMapping()
    @Operation(
            summary = "List and filter users with pagination",
            description = "Returns a paginated list of users, allowing filters by name, email, role, and dates."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users returned successfully",
                    content = @Content(schema = @Schema(implementation = UserGetResponse.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content)
    })
    public ResponseEntity<CustomApiResponse<List<UserGetResponse>>> getAll(
            @RequestParam(value = "name", required = false)
            @Parameter(description = "Filters by name containing the value")
            String name,
            @RequestParam(value = "email", required = false)
            @Parameter(description = "Filters by email containing the value")
            String email,
            @RequestParam(value = "role", required = false)
            @Parameter(description = "Filters by Role (e.g., ADMIN, USER)")
            RoleUser role,
            @RequestParam(value = "createAt", required = false)
            @Parameter(description = "Filters users created from this date onwards (ISO 8601)")
            Instant createAt,
            @RequestParam(value = "updateAt", required = false)
            @Parameter(description = "Filters users created up to this date (ISO 8601)")
            Instant updateAt,
            @RequestParam(value = "page", defaultValue = "0")
            @Parameter(description = "Page number (starts at 0)")
            int pageNumber,
            @RequestParam(value = "size", defaultValue = "10")
            @Parameter(description = "Page size (max. 50, default 10)")
            int pageSize
    ){
        var pageUser = new PageUser(pageNumber, pageSize);

        var users = userService.getAll(
                name,
                email,
                role,
                createAt,
                updateAt,
                pageUser.pageNumber(),
                pageUser.pageSize()
        );

        return ResponseEntity.ok(CustomApiResponse.success(HttpStatus.OK.value(), "List of users returned successfully", users));
    }

}