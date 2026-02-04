package com.api.synco.module.room_verification.application.mapper;

import com.api.synco.module.room_verification.application.dto.RoomVerificationResponse;
import com.api.synco.module.room_verification.application.dto.create.CreateRoomVerificationRequest;
import com.api.synco.module.room_verification.application.dto.update.UpdateRoomVerificationRequest;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.CreateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.command.UpdateRoomVerificationCommand;
import org.springframework.stereotype.Component;

@Component
public class RoomVerificationMapper {

    public CreateRoomVerificationCommand toCreateCommand(
            CreateRoomVerificationRequest createRoomVerificationRequest,
            long periodId,
            long userAuthenticatedId
    ) {
        return new CreateRoomVerificationCommand(
                periodId,
                userAuthenticatedId,
                createRoomVerificationRequest.allOrganized(),
                createRoomVerificationRequest.description(),
                createRoomVerificationRequest.observations(),
                createRoomVerificationRequest.ticket()
        );
    }

    public RoomVerificationResponse toResponse(RoomVerificationEntity roomVerificationEntity) {
        return new RoomVerificationResponse(
                roomVerificationEntity.getId(),
                roomVerificationEntity.getPeriod().getId(),
                roomVerificationEntity.getRoomVerificationForm().isAllOrganized(),
                roomVerificationEntity.getRoomVerificationForm().getDescription(),
                roomVerificationEntity.getRoomVerificationForm().getObservations(),
                roomVerificationEntity.getRoomVerificationForm().getTicket()
        );
    }

    public UpdateRoomVerificationCommand toUpdateCommand(UpdateRoomVerificationRequest request, long roomVerificationId, long userAuthenticatedId) {
        return new UpdateRoomVerificationCommand(
                roomVerificationId,
                userAuthenticatedId,
                request.allOrganized(),
                request.description(),
                request.observations(),
                request.ticket()
        );
    }
}
