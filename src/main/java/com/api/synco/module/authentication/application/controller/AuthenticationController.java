package com.api.synco.module.authentication.application.controller;

import com.api.synco.module.authentication.domain.port.UserAuthenticationService;
import com.api.synco.core.api.CustomApiResponse;
import com.api.synco.module.authentication.application.dto.login.UserLoginRequest;
import com.api.synco.module.authentication.application.dto.login.UserLoginResponse;
import com.api.synco.module.authentication.application.dto.register.UserRegisterRequest;
import com.api.synco.module.authentication.application.dto.register.UserRegisterResponse;
import com.api.synco.module.authentication.application.dto.reset_password.UserResetRequest;
import com.api.synco.module.authentication.domain.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication operations.
 *
 * <p>This controller provides endpoints for user authentication including:</p>
 * <ul>
 *   <li>User registration (creating new accounts)</li>
 *   <li>User login (obtaining JWT tokens)</li>
 *   <li>Password reset (changing authenticated user's password)</li>
 * </ul>
 *
 * <p>The registration and login endpoints are publicly accessible, while
 * password reset requires authentication.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see AuthenticationService
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserAuthenticationService userAuthenticationService;

    /**
     * Constructs a new authentication controller.
     *
     * @param authenticationService the service for authentication operations
     * @param userAuthenticationService the service for accessing authenticated user information
     */
    public AuthenticationController(AuthenticationService authenticationService, UserAuthenticationService userAuthenticationService) {
        this.authenticationService = authenticationService;
        this.userAuthenticationService = userAuthenticationService;
    }

    /**
     * Registers a new user in the system.
     *
     * <p>This endpoint is publicly accessible and creates a new user account
     * with the USER role.</p>
     *
     * @param userRegisterRequest the request containing user registration data
     * @return the created user data with HTTP 201 status
     */
    @PostMapping("/register")
    @Operation(
            summary = "Register new user",
            description = "Creates a new user in the system with the provided credentials. Returns the details of the newly created user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserRegisterResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data (e.g., missing fields, weak password, or validation error)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email already registered",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<UserRegisterResponse>> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        var user = authenticationService.register(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.success(HttpStatus.CREATED.value(), "User created successfully", user));
    }


    /**
     * Authenticates a user and returns a JWT token.
     *
     * <p>This endpoint validates user credentials and returns a JWT token
     * that can be used for accessing protected endpoints.</p>
     *
     * @param userLoginRequest the request containing login credentials
     * @return the login response with JWT token and HTTP 202 status
     */
    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns a JWT token for access to protected endpoints"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Login successful",
                    content = @Content(schema = @Schema(implementation = UserLoginResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        var user = authenticationService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(CustomApiResponse.success(HttpStatus.CREATED.value(), "Login successful", user));
    }

    /**
     * Resets the password for the authenticated user.
     *
     * <p>This endpoint requires authentication. The user must provide their
     * current password for verification before setting a new password.</p>
     *
     * @param userResetRequest the request containing current and new passwords
     * @return success message with HTTP 202 status
     */
    @PatchMapping("/password")
    @Operation (
            summary = "Reset password",
            description = "Reset the password of user authenticated"
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "202",
                    description = "Reset executed with success",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The password is incorrect",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The password is not valid",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<Void>> resetPassword(@RequestBody @Valid UserResetRequest userResetRequest){
        long idUser = userAuthenticationService.getAuthenticatedUserId();

        authenticationService.resetPassword(userResetRequest, idUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(CustomApiResponse.success(HttpStatus.ACCEPTED.value(), "Reset executed with success"));
    }

}