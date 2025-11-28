package com.api.synco.module.user.application.controller;

import com.api.synco.core.UserAuthenticationService;
import com.api.synco.infrastructure.api.CustomApiResponse;
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

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for user management")
@SecurityRequirement(name = "bearer-jwt")
public class UserController {

    private final UserService userService;
    private final UserAuthenticationService authenticationService;

    public UserController(UserService userService, UserAuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

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
        var response = userService.create(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.success(HttpStatus.CREATED.value(), "User created successfully", response));
    }

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