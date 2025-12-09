package com.api.synco.module.room.domain.use_cases;

import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.room.application.dto.CreateRoomRequest;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.exception.number.RoomNotUniqueNumberException;
import com.api.synco.module.room.domain.exception.user.UserWithoutCreateRoomPermissionException;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomUseCase {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final PermissionService permissionService;

    public CreateRoomUseCase(RoomRepository roomRepository, UserRepository userRepository, PermissionService permissionService) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    public RoomEntity execute(CreateRoomRequest createRoomRequest, long userAuthenticatedId){
        UserEntity userAuthenticated = userRepository.findById(userAuthenticatedId)
                .orElseThrow( () -> new UserNotFoundDomainException(userAuthenticatedId));

        if(!permissionService.canModifyRoom(userAuthenticated.getRole())){
            throw new UserWithoutCreateRoomPermissionException();
        }

        if(roomRepository.existByNumber(createRoomRequest.number())) throw new RoomNotUniqueNumberException(createRoomRequest.number());

        RoomEntity room = new RoomEntity(
                createRoomRequest.number(),
                createRoomRequest.typeRoom()
        );

        roomRepository.save(room);

        return room;
    }


}
