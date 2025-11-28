package com.api.synco.module.class_entity.domain.mapper;

import com.api.synco.module.class_entity.application.dto.ClassIdResponse;
import com.api.synco.module.class_entity.application.dto.create.CreateClassResponse;
import com.api.synco.module.class_entity.application.dto.get.GetAllClassResponse;
import com.api.synco.module.class_entity.application.dto.get.GetClassResponse;
import com.api.synco.module.class_entity.application.dto.update.UpdateClassResponse;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import org.springframework.stereotype.Component;

@Component
public class ClassMapper {

    public CreateClassResponse toCreateResponse(ClassEntity classEntity) {
        ClassIdResponse classIdResponse = toIdResponse(classEntity.getId());

        return new CreateClassResponse(
                classIdResponse,
                classEntity.getTotalHours(),
                classEntity.getShift()
        );
    }

    public UpdateClassResponse toUpdateResponse(ClassEntity classEntity) {
        ClassIdResponse classIdResponse = toIdResponse(classEntity.getId());

        return new UpdateClassResponse(
                classIdResponse,
                classEntity.getTotalHours(),
                classEntity.getShift()
        );
    }

    public GetClassResponse toGetResponse(ClassEntity classEntity) {
        return new GetClassResponse(
                toIdResponse(classEntity.getId()),
                classEntity.getTotalHours(),
                classEntity.getShift()
        );
    }

    public GetAllClassResponse toGetAllResponse(ClassEntity classEntity) {
        return new GetAllClassResponse(
                toIdResponse(classEntity.getId()),
                classEntity.getTotalHours(),
                classEntity.getShift()
        );
    }

    private ClassIdResponse toIdResponse(ClassEntityId id) {
        return new ClassIdResponse(
                id.getCourseId(),
                id.getNumber()
        );

    }

}
