package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.exception.RoomNotExistException;
import com.api.synco.module.room.domain.exception.user.UserWithoutUpdateRoomPermissionException;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.room.domain.command.UpdateRoomCommand;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateRoomUseCase {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final PermissionPolicy permissionPolicy;

    public UpdateRoomUseCase(
            RoomRepository roomRepository, UserRepository userRepository,
            PermissionPolicy permissionPolicy
    ) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.permissionPolicy = permissionPolicy;
    }

    @Transactional
    public void execute(UpdateRoomCommand updateRoomCommand){
        UserEntity userEntity = userRepository.findById(updateRoomCommand.authenticatedUserId())
                .orElseThrow( () -> new UserNotFoundDomainException(updateRoomCommand.authenticatedUserId()));

        if(!permissionPolicy.canEdit(userEntity.getRole())){
            throw new UserWithoutUpdateRoomPermissionException();
        }

        RoomEntity room = roomRepository.findById(updateRoomCommand.roomId())
                .orElseThrow( () -> new RoomNotExistException(updateRoomCommand.roomId()));

        room.update(
                updateRoomCommand.number(),
                updateRoomCommand.typeRoom()
        );

        roomRepository.save(room);
    }

}
