package com.api.synco.module.class_user.domain.service;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.application.dto.get.GetClassUserResponse;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.mapper.ClassUserMapper;
import com.api.synco.module.class_user.domain.use_cases.*;
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

    public void delete(long idCourse, int classNumber, long idUser, long userAuthenticatedId){
        ClassEntityId classEntityId = new ClassEntityId(idCourse, classNumber);
        ClassUserId classUserId = new ClassUserId(idUser, classEntityId);

        deleteClassUserUseCase.execute(classUserId, userAuthenticatedId);
    }





}
