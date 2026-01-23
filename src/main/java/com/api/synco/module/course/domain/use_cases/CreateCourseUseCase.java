package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.application.dto.create.CreateCourseRequest;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotUniqueException;
import com.api.synco.module.course.domain.exception.UserWithoutCreateCoursePermissionException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CreateCourseUseCase {

    private final PermissionPolicy permissionPolicy;

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public CreateCourseUseCase(@Qualifier("coursePermissionPolicy") PermissionPolicy permissionPolicy,
                               UserRepository userRepository,
                               CourseRepository courseRepository
    ) {
        this.permissionPolicy = permissionPolicy;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Create the course
     *
     * <p>
     * The method performs in the following steps:
     * <ol>
     * <li>Find the user by {@code idUser}</li>
     * <li>Valid the user permission</li>
     * <li>Create the course entity</li>
     * <li>Verify if the course is unique</li>
     * <li>Save the **course** in Database</li>
     * </ol>
     *
     * @param createCourseRequest Request containg the data for create a new course
     * @param idUser              The id of User who wants to create a course
     * @return The created and persisted {@link CourseEntity}, including its generated ID.
     * @throws UserNotFoundDomainException                If the user is not found
     * @throws UserWithoutCreateCoursePermissionException If the user don't have permission to create the course
     * @throws CourseNotUniqueException                   If the database alrealdy have a course with the same name or acronym
     */

    public CourseEntity execute(CreateCourseRequest createCourseRequest, long idUser) {
        var user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundDomainException(idUser));

        if (!permissionPolicy.canCreate(user.getRole())) throw new UserWithoutCreateCoursePermissionException();

        CourseEntity course = new CourseEntity(
                createCourseRequest.name(),
                createCourseRequest.acronym(),
                createCourseRequest.description()
        );

        if (courseRepository.existsByNameOrAcronym(course.getName(), course.getAcronym())) {
            throw new CourseNotUniqueException();
        }

        courseRepository.save(course);

        return course;
    }


}
