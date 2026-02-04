package com.api.synco.module.room_verification.application.service;

import com.api.synco.module.room_verification.application.dto.RoomVerificationResponse;
import com.api.synco.module.room_verification.application.dto.create.CreateRoomVerificationRequest;
import com.api.synco.module.room_verification.application.dto.update.UpdateRoomVerificationRequest;
import com.api.synco.module.room_verification.application.mapper.RoomVerificationMapper;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.CreateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.command.UpdateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.use_case.CreateRoomVerificationUseCase;
import com.api.synco.module.room_verification.domain.use_case.UpdateRoomVerificationUseCase;
import org.springframework.stereotype.Service;

@Service
public class RoomVerificationApplicationService {

    private final CreateRoomVerificationUseCase createRoomVerificationUseCase;
    private final UpdateRoomVerificationUseCase updateRoomVerificationUseCase;

    private final RoomVerificationMapper roomVerificationMapper;

    public RoomVerificationApplicationService(CreateRoomVerificationUseCase createRoomVerificationUseCase, UpdateRoomVerificationUseCase updateRoomVerificationUseCase, RoomVerificationMapper roomVerificationMapper) {
        this.createRoomVerificationUseCase = createRoomVerificationUseCase;
        this.updateRoomVerificationUseCase = updateRoomVerificationUseCase;
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

    public RoomVerificationResponse update(UpdateRoomVerificationRequest request, long roomVerificationId, long userAuthenticatedId) {
        UpdateRoomVerificationCommand command = roomVerificationMapper.toUpdateCommand(
                request,
                roomVerificationId,
                userAuthenticatedId
        );

        RoomVerificationEntity roomVerificationEntity = updateRoomVerificationUseCase.execute(command);

        return roomVerificationMapper.toResponse(roomVerificationEntity);
    }

}
