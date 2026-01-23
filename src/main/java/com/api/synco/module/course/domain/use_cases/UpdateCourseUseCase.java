package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.application.dto.update.UpdateCourseRequest;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutEditUserPermissionException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class UpdateCourseUseCase {

    private final PermissionPolicy permissionPolicy;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public UpdateCourseUseCase(
            @Qualifier("coursePermissionPolicy") PermissionPolicy permissionPolicy,
            CourseRepository courseRepository,
            UserRepository userRepository
    ) {
        this.permissionPolicy = permissionPolicy;
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
     * @throws UserWithoutEditUserPermissionException if the user do not have permission to edit
     * @throws UserNotFoundDomainException if the user is not found
     * @throws CourseNotFoundException if the course is not found
     *
     */
    public CourseEntity execute(UpdateCourseRequest updateCourseRequest, long idCourse, long idUserAuthenticated){
        var user = userRepository.findById(idUserAuthenticated).orElseThrow(() -> new UserNotFoundDomainException(idUserAuthenticated));

        if (!permissionPolicy.canEdit(user.getRole())) throw new UserWithoutEditUserPermissionException();

        CourseEntity course = courseRepository.findById(idCourse).orElseThrow( () -> new CourseNotFoundException(idCourse));

        course.setName(updateCourseRequest.name());
        course.setAcronym(updateCourseRequest.acronym());
        course.setDescription(updateCourseRequest.description());

        courseRepository.save(course);

        return course;
    }


}
