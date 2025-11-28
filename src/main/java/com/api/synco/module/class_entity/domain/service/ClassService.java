package com.api.synco.module.class_entity.domain.service;

import com.api.synco.module.class_entity.application.dto.create.CreateClassRequest;
import com.api.synco.module.class_entity.application.dto.create.CreateClassResponse;
import com.api.synco.module.class_entity.application.dto.get.GetAllClassResponse;
import com.api.synco.module.class_entity.application.dto.get.GetClassResponse;
import com.api.synco.module.class_entity.application.dto.update.UpdateClassRequest;
import com.api.synco.module.class_entity.application.dto.update.UpdateClassResponse;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.filter.ClassFilter;
import com.api.synco.module.class_entity.domain.filter.PageClass;
import com.api.synco.module.class_entity.domain.mapper.ClassMapper;
import com.api.synco.module.class_entity.domain.use_case.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    private final ClassMapper classMapper;

    private final CreateClassUseCase createClassUseCase;
    private final UpdateClassUseCase updateClassUseCase;
    private final DeleteClassUseCase deleteClassUseCase;
    private final GetClassUseCase getClassUseCase;
    private final GetAllClassUseCase getAllClassUseCase;

    public ClassService(ClassMapper classMapper, CreateClassUseCase createClassUseCase, UpdateClassUseCase updateClassUseCase, DeleteClassUseCase deleteClassUseCase, GetClassUseCase getClassUseCase, GetAllClassUseCase getAllClassUseCase) {
        this.classMapper = classMapper;
        this.createClassUseCase = createClassUseCase;
        this.updateClassUseCase = updateClassUseCase;
        this.deleteClassUseCase = deleteClassUseCase;
        this.getClassUseCase = getClassUseCase;
        this.getAllClassUseCase = getAllClassUseCase;
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

    public void delete(long idCourse, int numberClass, long idUser){
        ClassEntityId classEntityId = new ClassEntityId(idCourse, numberClass);

        deleteClassUseCase.execute(classEntityId, idUser);
    }

    public GetClassResponse get(long courseId, int numberClass){
        ClassEntityId classEntityId = new ClassEntityId(courseId, numberClass);

        ClassEntity classEntity = getClassUseCase.execute(classEntityId);

        return classMapper.toGetResponse(classEntity);
    }

    public List<GetAllClassResponse> getAll(
            long courseId,
            int numberClass,
            int pageNumber,
            int pageSize,
            Shift shiftContains
    ){

        ClassEntityId classEntityId = new ClassEntityId(courseId, numberClass);

        ClassFilter classFilter = ClassFilter.builder()
                .setCourseIdContains(courseId)
                .setClassNumberContains(numberClass)
                .setShiftContains(shiftContains)
                .build();

        PageClass pageClass = new PageClass(pageNumber, pageSize);

        Page<ClassEntity> classEntities = getAllClassUseCase.execute(pageClass, classFilter);

        return classEntities.stream()
                .map(classMapper::toGetAllResponse)
                .toList();
    }


}
