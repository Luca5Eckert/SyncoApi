package com.api.synco.module.room.application.controller;

import com.api.synco.module.authentication.domain.port.UserAuthenticationService;
import com.api.synco.core.api.CustomApiResponse;
import com.api.synco.module.room.application.dto.CreateRoomRequest;
import com.api.synco.module.room.application.dto.RoomResponse;
import com.api.synco.module.room.application.dto.UpdateRoomRequest;
import com.api.synco.module.room.application.service.RoomApplicationService;
import com.api.synco.module.room.domain.enumerator.TypeRoom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for room management operations.
 *
 * <p>This controller provides endpoints for creating, reading, updating, and deleting rooms.
 * All endpoints require authentication via JWT.</p>
 *
 * <p>The controller supports:</p>
 * <ul>
 *   <li>Room creation</li>
 *   <li>Room update</li>
 *   <li>Room deletion (with permission checks)</li>
 *   <li>Room retrieval by ID</li>
 *   <li>Paginated room listing with filters</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see RoomApplicationService
 */
@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Rooms", description = "Endpoints for room management")
@SecurityRequirement(name = "bearer-jwt")
public class RoomController {

    private final RoomApplicationService roomApplicationService;
    private final UserAuthenticationService authenticationService;

    /**
     * Constructs a new room controller.
     *
     * @param roomApplicationService the application service that orchestrates room use cases
     * @param authenticationService the service for accessing authenticated user information
     */
    public RoomController(
            RoomApplicationService roomApplicationService,
            UserAuthenticationService authenticationService
    ) {
        this.roomApplicationService = roomApplicationService;
        this.authenticationService = authenticationService;
    }

    /**
     * Creates a new room in the system.
     *
     * @param request the request containing room data
     * @return the created room data with HTTP 201 status
     */
    @PostMapping
    @Operation(
            summary = "Create new room",
            description = "Creates a new room in the system. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Room created successfully",
                    content = @Content(schema = @Schema(implementation = RoomResponse.class))
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
                    description = "No permission to create room",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<RoomResponse>> create(
            @RequestBody @Valid CreateRoomRequest request
    ) {
        long userId = authenticationService.getAuthenticatedUserId();
        var response = roomApplicationService.create(request, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CustomApiResponse.success(HttpStatus.CREATED.value(), "Room created successfully", response));
    }

    /**
     * Updates an existing room.
     *
     * @param id room id
     * @param request update payload
     * @return the updated room data with HTTP 202 status
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update room",
            description = "Updates a room. Requires authentication and proper permissions."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Room updated successfully",
                    content = @Content(schema = @Schema(implementation = RoomResponse.class))
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
                    description = "No permission to update this room",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Room not found",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<RoomResponse>> update(
            @Parameter(description = "Room ID", required = true)
            @PathVariable("id") long id,
            @RequestBody @Valid UpdateRoomRequest request
    ) {
        long userId = authenticationService.getAuthenticatedUserId();
        var response = roomApplicationService.update(request, id, userId);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(CustomApiResponse.success(HttpStatus.ACCEPTED.value(), "Room updated successfully", response));
    }

    /**
     * Deletes a room.
     *
     * @param id room id
     * @return success message with HTTP 202 status
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete room",
            description = "Deletes a room. Requires authentication and proper permissions."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Room deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to delete this room",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Room not found",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<String>> delete(
            @Parameter(description = "Room ID", required = true)
            @PathVariable("id") long id
    ) {
        long userId = authenticationService.getAuthenticatedUserId();
        roomApplicationService.delete(id, userId);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(CustomApiResponse.success(HttpStatus.ACCEPTED.value(), "Room deleted successfully", null));
    }

    /**
     * Retrieves a room by its unique identifier.
     *
     * @param id the unique identifier of the room to retrieve
     * @return the room data with HTTP 200 status
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Search room by ID",
            description = "Returns the data for a specific room by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Room found",
                    content = @Content(schema = @Schema(implementation = RoomResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Room not found",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<RoomResponse>> get(
            @Parameter(description = "Room ID", required = true)
            @PathVariable("id") long id
    ) {
        var room = roomApplicationService.getById(id);

        return ResponseEntity
                .ok(CustomApiResponse.success(HttpStatus.OK.value(), "Room found", room));
    }

    /**
     * Retrieves a paginated list of rooms with optional filters.
     *
     * @param typeRoom optional filter by room type
     * @param number optional filter by room number
     * @param pageNumber page number (0-based)
     * @param pageSize page size
     * @return a paginated list of rooms matching the criteria
     */
    @GetMapping
    @Operation(
            summary = "List and filter rooms with pagination",
            description = "Returns a paginated list of rooms, allowing filters by type and number."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of rooms returned successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content
            )
    })
    public ResponseEntity<CustomApiResponse<Page<RoomResponse>>> getAll(
            @RequestParam(value = "typeRoom", required = false)
            @Parameter(description = "Filters by room type (e.g., MEETING, CLASSROOM)")
            TypeRoom typeRoom,

            @RequestParam(value = "number", required = false, defaultValue = "0")
            @Parameter(description = "Filters by room number (e.g., 101). If omitted, no filter is applied.")
            int number,

            @RequestParam(value = "page", defaultValue = "0")
            @Parameter(description = "Page number (starts at 0)")
            int pageNumber,

            @RequestParam(value = "size", defaultValue = "10")
            @Parameter(description = "Page size (default 10)")
            int pageSize
    ) {
        var rooms = roomApplicationService.getAll(typeRoom, number, pageNumber, pageSize);

        return ResponseEntity
                .ok(CustomApiResponse.success(HttpStatus.OK.value(), "List of rooms returned successfully", rooms));
    }

}
