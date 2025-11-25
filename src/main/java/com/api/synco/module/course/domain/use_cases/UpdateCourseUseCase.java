package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.application.dto.update.UpdateCourseRequest;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutEditPermissionDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UpdateCourseUseCase {

    private final PermissionService permissionService;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public UpdateCourseUseCase(PermissionService permissionService, CourseRepository courseRepository, UserRepository userRepository) {
        this.permissionService = permissionService;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Update the Course
     *
     * <p>
     * The method performs the following steps:
     * <ol>
     * <li>Find the user authenticated</li>
     * <li>Verify if the user have permission to update</li>
     * <li>Find the course</li>
     * <li>Update the atributes</li>
     * <li>Save the course in database</li>
     * </ol>
     *
     * @param updateCourseRequest The record with data of request
     * @param idCourse The id of the course who will be update
     * @param idUserAuthenticated The id of the user authenticated
     * @return The course entity edited
     * @throws UserWithoutEditPermissionDomainException if the user do not have permission to edit
     * @throws UserNotFoundDomainException if the user is not found
     * @throws CourseNotFoundException if the course is not found
     *
     */
    public CourseEntity execute(UpdateCourseRequest updateCourseRequest, long idCourse, long idUserAuthenticated){
        var user = userRepository.findById(idUserAuthenticated).orElseThrow(() -> new UserNotFoundDomainException(idUserAuthenticated));

        if (!permissionService.canModifyCourse(user.getRole())) throw new UserWithoutEditPermissionDomainException();

        CourseEntity course = courseRepository.findById(idCourse).orElseThrow( () -> new CourseNotFoundException(idCourse));

        course.setName(updateCourseRequest.name());
        course.setAcronym(updateCourseRequest.acronym());
        course.setDescription(updateCourseRequest.description());

        courseRepository.save(course);

        return course;
    }


}
