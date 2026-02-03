package com.api.synco.module.room_verification.application.service;

import com.api.synco.module.room_verification.application.dto.RoomVerificationResponse;
import com.api.synco.module.room_verification.application.dto.create.CreateRoomVerificationRequest;
import com.api.synco.module.room_verification.application.mapper.RoomVerificationMapper;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.CreateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.use_case.CreateRoomVerificationUseCase;
import org.springframework.stereotype.Service;

@Service
public class RoomVerificationApplicationService {

    private final CreateRoomVerificationUseCase createRoomVerificationUseCase;

    private final RoomVerificationMapper roomVerificationMapper;

    public RoomVerificationApplicationService(CreateRoomVerificationUseCase createRoomVerificationUseCase, RoomVerificationMapper roomVerificationMapper) {
        this.createRoomVerificationUseCase = createRoomVerificationUseCase;
        this.roomVerificationMapper = roomVerificationMapper;
    }

    public RoomVerificationResponse create(CreateRoomVerificationRequest request, long periodId, long userAuthenticatedId) {
        CreateRoomVerificationCommand command = roomVerificationMapper.toCreateCommand(
                request,
                periodId,
                userAuthenticatedId
        );

        RoomVerificationEntity roomVerificationEntity = createRoomVerificationUseCase.execute(command);

        return roomVerificationMapper.toResponse(roomVerificationEntity);
    }

}
