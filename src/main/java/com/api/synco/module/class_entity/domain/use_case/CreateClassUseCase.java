package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.application.dto.create.CreateClassRequest;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutCreateClassPermissionException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutUpdateClassPermissionException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CreateClassUseCase {

    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CreateClassUseCase(ClassRepository classRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.classRepository = classRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ClassEntity execute(CreateClassRequest createClassRequest, long idUser){
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow( () -> new UserNotFoundDomainException(idUser));

        if(!ClassEntity.havePermissionToModify(userEntity.getRole())) throw new UserWithoutCreateClassPermissionException();
        CourseEntity course = courseRepository.findById(createClassRequest.courseId())
                .orElseThrow( () -> new CourseNotFoundException(createClassRequest.courseId()));

        int numberOfClass = classRepository.getNextNumberOfCourse(course);
        ClassEntityId id = new ClassEntityId(course.getId(), numberOfClass);

        ClassEntity classEntity = new ClassEntity(id, course, createClassRequest.totalHours(), createClassRequest.shift());
        classRepository.save(classEntity);

        return classEntity;
    }




}
