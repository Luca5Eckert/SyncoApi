package com.api.synco.module.class_user.domain.service;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.application.dto.create.CreateClassUserRequest;
import com.api.synco.module.class_user.application.dto.create.CreateClassUserResponse;
import com.api.synco.module.class_user.application.dto.get.GetAllClassUserResponse;
import com.api.synco.module.class_user.application.dto.get.GetClassUserResponse;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.class_user.domain.filter.ClassUserFilter;
import com.api.synco.module.class_user.domain.filter.PageClassUser;
import com.api.synco.module.class_user.domain.mapper.ClassUserMapper;
import com.api.synco.module.class_user.domain.use_cases.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassUserService {

    private final ClassUserMapper classUserMapper;

    private final CreateClassUserUseCase createClassUserUseCase;
    private final UpdateClassUserUseCase updateClassUserUseCase;
    private final DeleteClassUserUseCase deleteClassUserUseCase;
    private final GetClassUserUseCase getClassUserUseCase;
    private final GetAllClassUserUseCase getAllClassUserUseCase;

    public ClassUserService(ClassUserMapper classUserMapper, CreateClassUserUseCase createClassUserUseCase, UpdateClassUserUseCase updateClassUserUseCase, DeleteClassUserUseCase deleteClassUserUseCase, GetClassUserUseCase getClassUserUseCase, GetAllClassUserUseCase getAllClassUserUseCase) {
        this.classUserMapper = classUserMapper;
        this.createClassUserUseCase = createClassUserUseCase;
        this.updateClassUserUseCase = updateClassUserUseCase;
        this.deleteClassUserUseCase = deleteClassUserUseCase;
        this.getClassUserUseCase = getClassUserUseCase;
        this.getAllClassUserUseCase = getAllClassUserUseCase;
    }

    public CreateClassUserResponse create(CreateClassUserRequest createClassUserRequest, long idUser){
        ClassUser classUser = createClassUserUseCase.execute(createClassUserRequest, idUser);

        return classUserMapper.toCreateResponse(classUser);
    }

    public void delete(long idCourse, int classNumber, long idUser, long userAuthenticatedId){
        ClassEntityId classEntityId = new ClassEntityId(idCourse, classNumber);
        ClassUserId classUserId = new ClassUserId(idUser, classEntityId);

        deleteClassUserUseCase.execute(classUserId, userAuthenticatedId);
    }

    public GetClassUserResponse get(long idCourse, int classNumber, long idUser){
        ClassEntityId classEntityId = new ClassEntityId(idCourse, classNumber);
        ClassUserId classUserId = new ClassUserId(idUser, classEntityId);

        ClassUser classUser = getClassUserUseCase.execute(classUserId);

        return classUserMapper.toGetResponse(classUser);
    }

    public List<GetAllClassUserResponse> getAll(
        long userId,
        long courseId,
        int numberClass,
        TypeUserClass typeUserClass,
        int pageNumber,
        int pageSize
    ){
        ClassEntityId classEntityId = new ClassEntityId(courseId, numberClass);
        ClassUserId classUserId = new ClassUserId(userId, classEntityId);

        ClassUserFilter classUserFilter = ClassUserFilter.builder()
                .setUserIdContains(userId)
                .setClassUserIdContains(classUserId)
                .setTypeUserClassContains(typeUserClass)
                .build();

        PageClassUser pageClassUser = new PageClassUser(pageNumber,pageSize);

        Page<ClassUser> classUsers = getAllClassUserUseCase.execute(classUserFilter, pageClassUser);

        return classUsers.stream()
                .map(classUserMapper::toGetAllResponse)
                .toList();
    }

}
