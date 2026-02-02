package com.api.synco.module.period.application.service;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.period.application.dto.CreatePeriodRequest;
import com.api.synco.module.period.application.dto.CreatePeriodResponse;
import com.api.synco.module.period.application.dto.GetPeriodResponse;
import com.api.synco.module.period.application.mapper.PeriodMapper;
import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.command.CreatePeriodCommand;
import com.api.synco.module.period.domain.enumerator.TypePeriod;
import com.api.synco.module.period.domain.use_case.CreatePeriodUseCase;
import com.api.synco.module.period.domain.use_case.GetAllPeriodUseCase;
import com.api.synco.module.period.domain.use_case.GetPeriodUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodApplicationService {

    private final CreatePeriodUseCase createPeriodUseCase;
    private final GetPeriodUseCase getPeriodUseCase;
    private final GetAllPeriodUseCase getAllPeriodUseCase;

    private final PeriodMapper periodMapper;

    public PeriodApplicationService(CreatePeriodUseCase createPeriodUseCase, GetPeriodUseCase getPeriodUseCase, GetAllPeriodUseCase getAllPeriodUseCase, PeriodMapper periodMapper) {
        this.createPeriodUseCase = createPeriodUseCase;
        this.getPeriodUseCase = getPeriodUseCase;
        this.getAllPeriodUseCase = getAllPeriodUseCase;
        this.periodMapper = periodMapper;
    }

    public CreatePeriodResponse create(CreatePeriodRequest createPeriodRequest, long userAuthenticatedId) {
        ClassEntityId classEntityId = new ClassEntityId(
                createPeriodRequest.classEntity().courseId(),
                createPeriodRequest.classEntity().number()
        );

        CreatePeriodCommand createPeriodCommand = new CreatePeriodCommand(
                userAuthenticatedId,
                createPeriodRequest.teacherId(),
                createPeriodRequest.roomId(),
                classEntityId,
                createPeriodRequest.date(),
                createPeriodRequest.typePeriod()
        );

        PeriodEntity period = createPeriodUseCase.execute(createPeriodCommand);

        return periodMapper.toCreateResponse(period);
    }

    public GetPeriodResponse get(long periodId) {
        PeriodEntity period = getPeriodUseCase.execute(periodId);

        return periodMapper.toGetResponse(period);
    }

    public List<GetPeriodResponse> getAll(
            long teacherId,
            long roomId,
            long classId,
            TypePeriod typePeriod,
            int pageNumber,
            int pageSize
    ) {
        var periods = getAllPeriodUseCase.execute(
                teacherId,
                roomId,
                classId,
                typePeriod,
                pageNumber,
                pageSize
        );

        return periods.stream()
                .map(periodMapper::toGetResponse)
                .toList();
    }

}
