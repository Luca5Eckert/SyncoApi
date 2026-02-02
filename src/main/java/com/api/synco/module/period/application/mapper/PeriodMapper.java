package com.api.synco.module.period.application.mapper;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.period.application.dto.ClassEntityIdRequest;
import com.api.synco.module.period.application.dto.CreatePeriodResponse;
import com.api.synco.module.period.application.dto.GetPeriodResponse;
import com.api.synco.module.period.domain.PeriodEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class PeriodMapper {

    public CreatePeriodResponse toCreateResponse(PeriodEntity period){
        ClassEntityIdRequest classEntityIdRequest = toClassId(period.getClassEntity().getId());

        return new CreatePeriodResponse(
                period.getId(),
                period.getTeacher().getId(),
                period.getRoom().getId(),
                classEntityIdRequest,
                period.getDate().format(DateTimeFormatter.BASIC_ISO_DATE),
                period.getTypePeriod()
        );
    }

    private ClassEntityIdRequest toClassId(ClassEntityId classEntityId){
        return new ClassEntityIdRequest(
                classEntityId.getCourseId(),
                classEntityId.getNumber()
        );
    }

    public GetPeriodResponse toGetResponse(PeriodEntity period) {
        ClassEntityIdRequest classEntityIdRequest = toClassId(period.getClassEntity().getId());

        return new GetPeriodResponse (
                period.getId(),
                period.getTeacher().getId(),
                period.getRoom().getId(),
                classEntityIdRequest,
                period.getDate().format(DateTimeFormatter.BASIC_ISO_DATE),
                period.getTypePeriod()
        );
    }
}
