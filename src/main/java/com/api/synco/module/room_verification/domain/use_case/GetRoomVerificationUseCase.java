package com.api.synco.module.room_verification.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.GetRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.exception.RoomVerificationNotFound;
import com.api.synco.module.room_verification.domain.exception.user.UserNotHavePermissionToGetRoomVerificationException;
import com.api.synco.module.room_verification.domain.permission.RoomVerificationPermissionPolicy;
import com.api.synco.module.room_verification.domain.port.RoomVerificationRepository;
import org.springframework.stereotype.Component;

@Component
public class GetRoomVerificationUseCase {

    private final RoomVerificationRepository roomVerificationRepository;
    private final ClassUserRepository classUserRepository;

    private final RoomVerificationPermissionPolicy permissionPolicy;

    public GetRoomVerificationUseCase(RoomVerificationRepository roomVerificationRepository, ClassUserRepository classUserRepository, RoomVerificationPermissionPolicy permissionPolicy) {
        this.roomVerificationRepository = roomVerificationRepository;
        this.classUserRepository = classUserRepository;
        this.permissionPolicy = permissionPolicy;
    }

    public RoomVerificationEntity execute(GetRoomVerificationCommand command){
        RoomVerificationEntity roomVerificationEntity = roomVerificationRepository.findById(command.roomVerificationId())
                .orElseThrow(RoomVerificationNotFound::new);

        validateUserPermissions(
                roomVerificationEntity.getPeriod().getClassEntity().getId(),
                command.userAuthenticatedId()
        );

        return roomVerificationEntity;
    }

    public void validateUserPermissions(ClassEntityId classEntityId, long userId){
        ClassUserId classUserId = new ClassUserId(userId, classEntityId);
        ClassUser classUser = classUserRepository.findById(classUserId)
                .orElseThrow(ClassUserNotFoundException::new);

        if (!permissionPolicy.canUpdate(classUser.getUserEntity().getRole(), classUser.getTypeUserClass())) {
            throw new UserNotHavePermissionToGetRoomVerificationException();
        }
    }

}
