package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.command.CreateRoomCommand;
import com.api.synco.module.room.domain.exception.number.RoomNotUniqueNumberException;
import com.api.synco.module.room.domain.exception.user.UserWithoutCreateRoomPermissionException;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomUseCase {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final PermissionPolicy permissionPolicy;

    public CreateRoomUseCase(
            RoomRepository roomRepository,
            UserRepository userRepository,
            @Qualifier("roomPermissionPolicy") PermissionPolicy permissionPolicy
    ) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.permissionPolicy = permissionPolicy;
    }

    public RoomEntity execute(CreateRoomCommand command) {
        UserEntity authenticatedUser = userRepository.findById(command.authenticatedUserId())
                .orElseThrow(() -> new UserNotFoundDomainException(command.authenticatedUserId()));

        if (!permissionPolicy.canCreate(authenticatedUser.getRole())) {
            throw new UserWithoutCreateRoomPermissionException();
        }

        if (roomRepository.existByNumber(command.number())) {
            throw new RoomNotUniqueNumberException(command.number());
        }

        RoomEntity room = new RoomEntity(
                command.number(),
                command.typeRoom()
        );

        roomRepository.save(room);

        return room;
    }
}
