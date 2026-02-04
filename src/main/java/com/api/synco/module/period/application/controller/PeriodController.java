package com.api.synco.module.period.application.controller;

import com.api.synco.module.authentication.domain.port.UserAuthenticationService;
import com.api.synco.core.api.CustomApiResponse;
import com.api.synco.module.period.application.dto.CreatePeriodRequest;
import com.api.synco.module.period.application.dto.CreatePeriodResponse;
import com.api.synco.module.period.application.dto.GetPeriodResponse;
import com.api.synco.module.period.application.service.PeriodApplicationService;
import com.api.synco.module.period.domain.enumerator.TypePeriod;
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

import java.util.List;

/**
 * REST controller for period management operations.
 *
 * <p>This controller provides endpoints for creating and retrieving academic periods.
 * All endpoints require authentication via JWT.</p>
 *
 * <p>The controller supports:</p>
 * <ul>
 * <li>Period creation</li>
 * <li>Period retrieval by ID</li>
 * <li>Paginated period listing with extensive filters (Teacher, Room, Class, Type)</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see PeriodApplicationService
 */
@RestController
@RequestMapping("/api/periods")
@Tag(name = "Periods", description = "Endpoints for period management")
@SecurityRequirement(name = "bearer-jwt")
public class PeriodController {

    private final PeriodApplicationService periodApplicationService;
    private final UserAuthenticationService authenticationService;

    /**
     * Constructs a new period controller.
     *
     * @param periodApplicationService the application service that orchestrates period use cases
     * @param authenticationService the service for accessing authenticated user information
     */
    public PeriodController(
            PeriodApplicationService periodApplicationService,
            UserAuthenticationService authenticationService
    ) {
        this.periodApplicationService = periodApplicationService;
        this.authenticationService = authenticationService;
    }

    /**
     * Creates a new period in the system.
     *
     * @param request the request containing period data
     * @return the created period data with HTTP 201 status
     */
    @PostMapping
    @Operation(
            summary = "Create new period",
            description = "Creates a new academic period in the system. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Period created successfully",
                    content = @Content(schema = @Schema(implementation = CreatePeriodResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to create period",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<CreatePeriodResponse>> create(
            @RequestBody @Valid CreatePeriodRequest request
    ) {
        long userId = authenticationService.getAuthenticatedUserId();
        var response = periodApplicationService.create(request, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CustomApiResponse.success(HttpStatus.CREATED.value(), "Period created successfully", response));
    }

    /**
     * Retrieves a period by its unique identifier.
     *
     * @param id the unique identifier of the period to retrieve
     * @return the period data with HTTP 200 status
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Search period by ID",
            description = "Returns the data for a specific period by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Period found",
                    content = @Content(schema = @Schema(implementation = GetPeriodResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Period not found",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<GetPeriodResponse>> get(
            @Parameter(description = "Period ID", required = true)
            @PathVariable("id") long id
    ) {
        var period = periodApplicationService.get(id);

        return ResponseEntity
                .ok(CustomApiResponse.success(HttpStatus.OK.value(), "Period found", period));
    }

    /**
     * Retrieves a list of periods with optional filters.
     *
     * @param teacherId optional filter by teacher ID
     * @param roomId optional filter by room ID
     * @param classId optional filter by class ID
     * @param typePeriod optional filter by period type
     * @param pageNumber page number (0-based)
     * @param pageSize page size
     * @return a list of periods matching the criteria
     */
    @GetMapping
    @Operation(
            summary = "List and filter periods",
            description = "Returns a list of periods, allowing filters by teacher, room, class and type."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of periods returned successfully",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<List<GetPeriodResponse>>> getAll(
            @RequestParam(value = "teacherId", required = false, defaultValue = "0")
            @Parameter(description = "Filter by Teacher ID")
            long teacherId,

            @RequestParam(value = "roomId", required = false, defaultValue = "0")
            @Parameter(description = "Filter by Room ID")
            long roomId,

            @RequestParam(value = "classId", required = false, defaultValue = "0")
            @Parameter(description = "Filter by Class ID")
            long classId,

            @RequestParam(value = "typePeriod", required = false)
            @Parameter(description = "Filter by Type (e.g., REGULAR, EXAM)")
            TypePeriod typePeriod,

            @RequestParam(value = "page", defaultValue = "0")
            @Parameter(description = "Page number (starts at 0)")
            int pageNumber,

            @RequestParam(value = "size", defaultValue = "10")
            @Parameter(description = "Page size (default 10)")
            int pageSize
    ) {
        var periods = periodApplicationService.getAll(
                teacherId,
                roomId,
                classId,
                typePeriod,
                pageNumber,
                pageSize
        );

        return ResponseEntity
                .ok(CustomApiResponse.success(HttpStatus.OK.value(), "List of periods returned successfully", periods));
    }
}