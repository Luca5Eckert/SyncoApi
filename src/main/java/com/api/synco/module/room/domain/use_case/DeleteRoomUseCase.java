package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.room.domain.exception.user.UserWithoutDeleteRoomPermissionException;
import com.api.synco.module.room.domain.permission.RoomPermissionPolicy;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteRoomUseCase {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final PermissionPolicy permitionPolicy;

    public DeleteRoomUseCase(RoomRepository roomRepository, UserRepository userRepository, PermissionPolicy permitionPolicy) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.permitionPolicy = permitionPolicy;
    }


    @Transactional
    public void execute(long roomId, long userAuthenticatedId){
        UserEntity userEntity = userRepository.findById(userAuthenticatedId)
                .orElseThrow( () -> new UserNotFoundDomainException(userAuthenticatedId));

        if(!permitionPolicy.canDelete(userEntity.getRole())){
            throw new UserWithoutDeleteRoomPermissionException();
        }

        roomRepository.deleteById(roomId);
    }

}
