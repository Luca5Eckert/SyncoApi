package com.api.synco.module.room.application.service;

import com.api.synco.module.room.application.dto.CreateRoomRequest;
import com.api.synco.module.room.application.dto.RoomResponse;
import com.api.synco.module.room.application.dto.UpdateRoomRequest;
import com.api.synco.module.room.application.mapper.RoomMapper;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.enumerator.TypeRoom;
import com.api.synco.module.room.domain.use_case.CreateRoomUseCase;
import com.api.synco.module.room.domain.use_case.DeleteRoomUseCase;
import com.api.synco.module.room.domain.use_case.GetAllRoomUseCase;
import com.api.synco.module.room.domain.use_case.GetRoomUseCase;
import com.api.synco.module.room.domain.use_case.UpdateRoomUseCase;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoomApplicationService {

    private final CreateRoomUseCase createRoomUseCase;
    private final UpdateRoomUseCase updateRoomUseCase;
    private final DeleteRoomUseCase deleteRoomUseCase;
    private final GetRoomUseCase getRoomUseCase;
    private final GetAllRoomUseCase getAllRoomUseCase;

    private final RoomMapper roomMapper;

    public RoomApplicationService(
            CreateRoomUseCase createRoomUseCase,
            UpdateRoomUseCase updateRoomUseCase,
            DeleteRoomUseCase deleteRoomUseCase,
            GetRoomUseCase getRoomUseCase,
            GetAllRoomUseCase getAllRoomUseCase,
            RoomMapper roomMapper
    ) {
        this.createRoomUseCase = createRoomUseCase;
        this.updateRoomUseCase = updateRoomUseCase;
        this.deleteRoomUseCase = deleteRoomUseCase;
        this.getRoomUseCase = getRoomUseCase;
        this.getAllRoomUseCase = getAllRoomUseCase;
        this.roomMapper = roomMapper;
    }

    public RoomResponse create(CreateRoomRequest request, long authenticatedUserId) {
        Objects.requireNonNull(request, "CreateRoomRequest must not be null");

        var command = new com.api.synco.module.room.domain.command.CreateRoomCommand(
                request.number(),
                request.typeRoom(),
                authenticatedUserId
        );

        RoomEntity created = createRoomUseCase.execute(command);
        return roomMapper.of(created);
    }

    public RoomResponse update(UpdateRoomRequest request, long roomId, long authenticatedUserId) {
        Objects.requireNonNull(request, "UpdateRoomRequest must not be null");

        var command = new com.api.synco.module.room.domain.command.UpdateRoomCommand(
                roomId,
                authenticatedUserId,
                request.number(),
                request.typeRoom()
        );

        RoomEntity updated = updateRoomUseCase.execute(command);
        return roomMapper.of(updated);
    }

    public void delete(long roomId, long authenticatedUserId) {
        deleteRoomUseCase.execute(roomId, authenticatedUserId);
    }

    public RoomResponse getById(long roomId) {
        RoomEntity room = getRoomUseCase.execute(roomId);
        return roomMapper.of(room);
    }

    public Page<RoomResponse> getAll(
            TypeRoom typeRoom,
            int number,
            int pageNumber,
            int pageSize
    ) {
        Page<RoomEntity> page = getAllRoomUseCase.execute(typeRoom, number, pageNumber, pageSize);
        return roomMapper.of(page);
    }


}
