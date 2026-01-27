package com.api.synco.module.room.application.service;

import com.api.synco.module.room.application.dto.CreateRoomRequest;
import com.api.synco.module.room.application.dto.RoomResponse;
import com.api.synco.module.room.application.dto.UpdateRoomRequest;
import com.api.synco.module.room.application.mapper.RoomMapper;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.command.CreateRoomCommand;
import com.api.synco.module.room.domain.command.UpdateRoomCommand;
import com.api.synco.module.room.domain.use_case.CreateRoomUseCase;
import com.api.synco.module.room.domain.use_case.UpdateRoomUseCase;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoomApplicationService {

    private final CreateRoomUseCase createRoomUseCase;
    private final UpdateRoomUseCase updateRoomUseCase;

    private final RoomMapper roomMapper;

    public RoomApplicationService(
            CreateRoomUseCase createRoomUseCase,
            UpdateRoomUseCase updateRoomUseCase, RoomMapper roomMapper
    ) {
        this.createRoomUseCase = createRoomUseCase;
        this.updateRoomUseCase = updateRoomUseCase;
        this.roomMapper = roomMapper;
    }

    public RoomResponse create(CreateRoomRequest request, long authenticatedUserId) {
        Objects.requireNonNull(request, "CreateRoomRequest must not be null");

        CreateRoomCommand command = new CreateRoomCommand(
                request.number(),
                request.typeRoom(),
                authenticatedUserId
        );

        RoomEntity created = createRoomUseCase.execute(command);
        return roomMapper.of(created);
    }

    public RoomResponse update(UpdateRoomRequest request, long roomId, long authenticatedUserId) {
        Objects.requireNonNull(request, "UpdateRoomRequest must not be null");

        UpdateRoomCommand command = new UpdateRoomCommand(
                roomId,
                authenticatedUserId,
                request.number(),
                request.typeRoom()
        );

        RoomEntity updated = updateRoomUseCase.execute(command);
        return roomMapper.of(updated);
    }

}
