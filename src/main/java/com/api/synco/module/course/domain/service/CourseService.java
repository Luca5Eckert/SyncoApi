package com.api.synco.module.course.domain.service;

import com.api.synco.module.course.application.dto.create.CreateCourseRequest;
import com.api.synco.module.course.application.dto.create.CreateCourseResponse;
import com.api.synco.module.course.application.dto.delete.DeleteCourseRequest;
import com.api.synco.module.course.application.dto.get.GetAllCourseResponse;
import com.api.synco.module.course.application.dto.get.GetCourseResponse;
import com.api.synco.module.course.application.dto.update.UpdateCourseRequest;
import com.api.synco.module.course.application.dto.update.UpdateCourseResponse;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.mapper.CourseMapper;
import com.api.synco.module.course.domain.use_cases.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Domain service for course management operations.
 *
 * <p>This service coordinates course-related business operations by delegating
 * to appropriate use cases and handling the mapping between domain entities
 * and DTOs.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Course creation with permission checks</li>
 *   <li>Course deletion with permission checks</li>
 *   <li>Course updates</li>
 *   <li>Course retrieval by ID</li>
 *   <li>Paginated course listing with filters</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see CourseMapper
 */
@Service
public class CourseService {

    private final CourseMapper courseMapper;

    private final CreateCourseUseCase createCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final GetAllCourseUseCase getAllCourseUseCase;
    private final GetCourseUseCase getCourseUseCase;

    /**
     * Constructs a new course service with the required dependencies.
     *
     * @param courseMapper the mapper for converting between entities and DTOs
     * @param createCourseUseCase the use case for course creation
     * @param deleteCourseUseCase the use case for course deletion
     * @param updateCourseUseCase the use case for course updates
     * @param getAllCourseUseCase the use case for retrieving multiple courses
     * @param getCourseUseCase the use case for retrieving a single course
     */
    public CourseService(CourseMapper courseMapper, CreateCourseUseCase createCourseUseCase, DeleteCourseUseCase deleteCourseUseCase, UpdateCourseUseCase updateCourseUseCase, GetAllCourseUseCase getAllCourseUseCase, GetCourseUseCase getCourseUseCase) {
        this.courseMapper = courseMapper;
        this.createCourseUseCase = createCourseUseCase;
        this.deleteCourseUseCase = deleteCourseUseCase;
        this.updateCourseUseCase = updateCourseUseCase;
        this.getAllCourseUseCase = getAllCourseUseCase;
        this.getCourseUseCase = getCourseUseCase;
    }

    /**
     * Creates a new course in the system.
     *
     * @param createCourseRequest the request containing course data
     * @param idUser the ID of the authenticated user performing the creation
     * @return the created course data
     */
    public CreateCourseResponse create(CreateCourseRequest createCourseRequest, long idUser) {
        var course = createCourseUseCase.execute(createCourseRequest, idUser);

        return courseMapper.toCreateResponse(course);
    }

    /**
     * Deletes a course from the system.
     *
     * @param deleteCourseRequest the request containing the course ID to delete
     * @param idUser the ID of the authenticated user performing the deletion
     */
    public void delete(DeleteCourseRequest deleteCourseRequest, long idUser){
        deleteCourseUseCase.execute(deleteCourseRequest, idUser);
    }

    /**
     * Updates an existing course.
     *
     * @param updateCourseRequest the request containing updated course data
     * @param idCourse the ID of the course to update
     * @param idUser the ID of the authenticated user performing the update
     * @return the updated course data
     */
    public UpdateCourseResponse update(UpdateCourseRequest updateCourseRequest, long idCourse, long idUser){
        CourseEntity course = updateCourseUseCase.execute(updateCourseRequest, idCourse, idUser);
        return courseMapper.toUpdateResponse(course);
    }

    /**
     * Retrieves a course by its unique identifier.
     *
     * @param id the unique identifier of the course
     * @return the course data
     */
    public GetCourseResponse getCourse(long id){
        CourseEntity course = getCourseUseCase.execute(id);

        return courseMapper.toGetResponse(course);
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
    public List<GetAllCourseResponse> getAll(
            String name,
            String acronym,
            int pageNumber,
            int pageSize
    ){

        var listUsers = getAllCourseUseCase.execute(
                name,
                acronym,
                pageNumber,
                pageSize
        );

        return listUsers.stream()
                .map(courseMapper::toGetAllResponse)
                .toList();

    }


}
