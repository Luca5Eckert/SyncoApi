package com.api.synco.module.class_entity.domain.service;

import com.api.synco.module.class_entity.application.dto.create.CreateClassRequest;
import com.api.synco.module.class_entity.application.dto.create.CreateClassResponse;
import com.api.synco.module.class_entity.application.dto.update.UpdateClassRequest;
import com.api.synco.module.class_entity.application.dto.update.UpdateClassResponse;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.mapper.ClassMapper;
import com.api.synco.module.class_entity.domain.use_case.CreateClassUseCase;
import com.api.synco.module.class_entity.domain.use_case.UpdateClassUseCase;
import org.springframework.stereotype.Service;

@Service
public class ClassService {

    private final ClassMapper classMapper;

    private final CreateClassUseCase createClassUseCase;
    private final UpdateClassUseCase updateClassUseCase;

    public ClassService(ClassMapper classMapper, CreateClassUseCase createClassUseCase, UpdateClassUseCase updateClassUseCase) {
        this.classMapper = classMapper;
        this.createClassUseCase = createClassUseCase;
        this.updateClassUseCase = updateClassUseCase;
    }

    public CreateClassResponse create(CreateClassRequest createClassRequest, long idUser) {
        ClassEntity classEntity = createClassUseCase.execute(createClassRequest, idUser);

        return classMapper.toCreateResponse(classEntity);
    }

    public UpdateClassResponse update(UpdateClassRequest updateClassRequest, long idCourse, int numberClass, long idUser){
        ClassEntityId classEntityId = new ClassEntityId(idCourse, numberClass);

        ClassEntity classEntity = updateClassUseCase.execute(updateClassRequest, classEntityId, idUser);

        return classMapper.toUpdateResponse(classEntity);
    }

}
