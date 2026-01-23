package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.application.dto.delete.DeleteCourseRequest;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.exception.UserWithoutDeleteCoursePermissionException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteCourseUseCase {

    private final PermissionPolicy permissionPolicy;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public DeleteCourseUseCase(PermissionPolicy permissionPolicy, CourseRepository courseRepository, UserRepository userRepository) {
        this.permissionPolicy = permissionPolicy;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Delete the course
     *
     * <p>
     * The method performs in the following steps:
     * <ol>
     * <li>Find the user by {@code idUser}</li>
     * <li>Valid the user permission</li>
     * <li>Verify if the course exist</li>
     * </ol>
     *
     * @param deleteCourseRequest Record with date of the request
     * @param idUser The id of the user authenticated
     */
    public void execute(DeleteCourseRequest deleteCourseRequest, long idUser){
        var user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundDomainException(idUser));

        if (!permissionPolicy.canDelete(user.getRole())) throw new UserWithoutDeleteCoursePermissionException();

        if(!courseRepository.existById(deleteCourseRequest.id())) throw new CourseNotFoundException(deleteCourseRequest.id());

        courseRepository.deleteById(deleteCourseRequest.id());

    }

}
