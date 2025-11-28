package com.api.synco.module.class_entity.application.controller;


import com.api.synco.core.UserAuthenticationService;
import com.api.synco.infrastructure.api.CustomApiResponse;
import com.api.synco.module.class_entity.application.dto.create.CreateClassRequest;
import com.api.synco.module.class_entity.application.dto.create.CreateClassResponse;
import com.api.synco.module.class_entity.application.dto.get.GetClassResponse;
import com.api.synco.module.class_entity.application.dto.update.UpdateClassRequest;
import com.api.synco.module.class_entity.application.dto.update.UpdateClassResponse;
import com.api.synco.module.class_entity.domain.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
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

@RequestMapping("/api/classes")
@RestController
@Tag(name = "Classes", description = "Endpoints for the class management")
@SecurityRequirement(name = "bearer-jwt")
public class ClassController {

    private final ClassService classService;

    private final UserAuthenticationService userAuthenticationService;

    public ClassController(ClassService classService, UserAuthenticationService userAuthenticationService) {
        this.classService = classService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Operation(
            summary = "Create a new class",
            description = "Creates a new class in the system. Requires authentication"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Class created with success.",
                    content = @Content(schema = @Schema(implementation = CreateClassResponse.class))
            ),
            @ApiResponse (
                    responseCode = "400",
                    description = "The user don't have permission to create class.",
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
    public ResponseEntity<CustomApiResponse<CreateClassResponse>> create(@Valid @RequestBody CreateClassRequest createClassRequest){
        long idUser = userAuthenticationService.getAuthenticatedUserId();

        var createResponse =  classService.create(createClassRequest, idUser);

        HttpStatus status = HttpStatus.CREATED;

        return ResponseEntity.status(status).body(CustomApiResponse.success(status.value(), "Class created with success.", createResponse));
    }

    @Operation(
            summary = "Update a class",
            description = "Update a class in the system. Requires authentication"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Class updated with success.",
                    content = @Content(schema = @Schema(implementation = CreateClassResponse.class))
            ),
            @ApiResponse (
                    responseCode = "400",
                    description = "The user don't have permission to update class.",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    }
    )
    @PutMapping("/{idCourse}/{numberClass}")
    public ResponseEntity<CustomApiResponse<UpdateClassResponse>> update(@Valid @RequestBody UpdateClassRequest updateClassRequest, @PathVariable long idCourse, @PathVariable int numberClass){
        long idUser = userAuthenticationService.getAuthenticatedUserId();

        var updateResponse = classService.update(updateClassRequest, idCourse, numberClass, idUser);

        HttpStatus status = HttpStatus.ACCEPTED;

        return ResponseEntity.status(status).body(CustomApiResponse.success(status.value(), "Class updated with success.", updateResponse));
    }

    @Operation(
            summary = "Delete a class",
            description = "Delete a class in the system. Requires authentication"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Class deleted with success.",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "400",
                    description = "The user don't have permission to delete class.",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "Class not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{idCourse}/{numberClass}")
    public ResponseEntity<CustomApiResponse<Void>> delete(@PathVariable long idCourse,@PathVariable int numberClass){
        long idUser = userAuthenticationService.getAuthenticatedUserId();

        classService.delete(idCourse, numberClass, idUser);

        return ResponseEntity.ok(CustomApiResponse.success(200, "Class deleted with success."));

    }

    @Operation(
            summary = "Get class",
            description = "Get the class with the ID correspondent in System"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Class retrieved with success.",
                    content = @Content( schema = @Schema(implementation = GetClassResponse.class))
            )
    }
    )
    @GetMapping("/{idCourse}/{numberClass}")
    public ResponseEntity<CustomApiResponse<GetClassResponse>> get(@PathVariable long idCourse, @PathVariable int numberClass){
        GetClassResponse classResponse = classService.get(idCourse, numberClass);

        return ResponseEntity.ok(CustomApiResponse.success(200, "Class retrieved with success.", classResponse));
    }


}
