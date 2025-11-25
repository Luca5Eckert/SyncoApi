package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.application.dto.create.CreateClassRequest;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutCreateClassPermissionException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CreateClassUseCase {

    private final PermissionService permissionService;

    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CreateClassUseCase(PermissionService permissionService, ClassRepository classRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.permissionService = permissionService;
        this.classRepository = classRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new Class entity after validating user permissions and the existence of the associated Course.
     * The method also determines the next sequential class number within the course.
     * *
     *
     * @param createClassRequest The DTO (Data Transfer Object) containing the necessary data for class creation,
     * such as the Course ID, total hours, and shift.
     * @param idUser The ID of the authenticated user attempting the creation operation.
     *
     * @return The newly persisted {@link ClassEntity} object.
     *
     * @throws UserNotFoundDomainException If the user with the provided {@code idUser} is not found.
     * @throws UserWithoutCreateClassPermissionException If the authenticated user's role lacks the permission to create classes.
     * @throws CourseNotFoundException If the course referenced by {@code createClassRequest.courseId()} is not found.
     */
    @Transactional
    public ClassEntity execute(CreateClassRequest createClassRequest, long idUser){
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow( () -> new UserNotFoundDomainException(idUser));

        if(!permissionService.canModifyClass(userEntity.getRole())) throw new UserWithoutCreateClassPermissionException();

        CourseEntity course = courseRepository.findById(createClassRequest.courseId())
                .orElseThrow( () -> new CourseNotFoundException(createClassRequest.courseId()));

        int numberOfClass = classRepository.getNextNumberOfCourse(course);
        ClassEntityId id = new ClassEntityId(course.getId(), numberOfClass);

        ClassEntity classEntity = new ClassEntity(id, course, createClassRequest.totalHours(), createClassRequest.shift());
        classRepository.save(classEntity);

        return classEntity;
    }




}
