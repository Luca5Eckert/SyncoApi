package com.api.synco.module.room_verification.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.UpdateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.exception.RoomVerificationNotFound;
import com.api.synco.module.room_verification.domain.exception.UserNotHavePermissionToCreateRoomVerificationException;
import com.api.synco.module.room_verification.domain.permission.RoomVerificationPermissionPolicy;
import com.api.synco.module.room_verification.domain.port.RoomVerificationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateRoomVerificationUseCase {

    private final RoomVerificationRepository roomVerificationRepository;
    private final ClassUserRepository classUserRepository;

    private final RoomVerificationPermissionPolicy permissionPolicy;

    public UpdateRoomVerificationUseCase(RoomVerificationRepository roomVerificationRepository, ClassUserRepository classUserRepository, RoomVerificationPermissionPolicy permissionPolicy) {
        this.roomVerificationRepository = roomVerificationRepository;
        this.classUserRepository = classUserRepository;
        this.permissionPolicy = permissionPolicy;
    }

    @Transactional
    public RoomVerificationEntity execute(UpdateRoomVerificationCommand updateRoomVerificationCommand){
        RoomVerificationEntity roomVerificationEntity = roomVerificationRepository.findById(updateRoomVerificationCommand.roomVerificationId())
                .orElseThrow(RoomVerificationNotFound::new);

        validateUserPermissions(
                updateRoomVerificationCommand.userAuthenticatedId(),
                roomVerificationEntity.getPeriod().getClassEntity().getId()
        );

        roomVerificationEntity.update(
                updateRoomVerificationCommand.allOrganized(),
                updateRoomVerificationCommand.description(),
                updateRoomVerificationCommand.observations(),
                updateRoomVerificationCommand.ticket()
        );

        roomVerificationRepository.save(roomVerificationEntity);

        return roomVerificationEntity;
    }

    private void validateUserPermissions(long userId, ClassEntityId classEntityId) {
        ClassUserId classUserId = new ClassUserId(userId, classEntityId);
        ClassUser classUser = classUserRepository.findById(classUserId)
                .orElseThrow(ClassUserNotFoundException::new);

        if (!permissionPolicy.canUpdate(classUser.getUserEntity().getRole(), classUser.getTypeUserClass())) {
            throw new UserNotHavePermissionToCreateRoomVerificationException();
        }
    }

}
