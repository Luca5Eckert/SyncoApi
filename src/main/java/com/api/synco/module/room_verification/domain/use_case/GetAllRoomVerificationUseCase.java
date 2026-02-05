package com.api.synco.module.room_verification.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.GetAllRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.exception.RoomVerificationNotFound;
import com.api.synco.module.room_verification.domain.exception.user.UserNotHavePermissionToGetRoomVerificationException;
import com.api.synco.module.room_verification.domain.filter.RoomVerificationFilter;
import com.api.synco.module.room_verification.domain.filter.RoomVerificationPage;
import com.api.synco.module.room_verification.domain.permission.RoomVerificationPermissionPolicy;
import com.api.synco.module.room_verification.domain.port.RoomVerificationRepository;
import com.api.synco.module.room_verification.domain.value_object.RoomVerificationForm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GetAllRoomVerificationUseCase {

    private final RoomVerificationRepository roomVerificationRepository;
    private final ClassUserRepository classUserRepository;

    private final RoomVerificationPermissionPolicy permissionPolicy;


    public GetAllRoomVerificationUseCase(RoomVerificationRepository roomVerificationRepository, ClassUserRepository classUserRepository, RoomVerificationPermissionPolicy permissionPolicy) {
        this.roomVerificationRepository = roomVerificationRepository;
        this.classUserRepository = classUserRepository;
        this.permissionPolicy = permissionPolicy;
    }

    public Page<RoomVerificationEntity> execute(GetAllRoomVerificationCommand command){
        RoomVerificationEntity roomVerificationEntity = roomVerificationRepository.findById(command.roomVerificationId())
                .orElseThrow(RoomVerificationNotFound::new);

        validateUserPermissions(
                roomVerificationEntity.getPeriod().getClassEntity().getId(),
                command.userAuthenticatedId()
        );

        RoomVerificationFilter roomVerificationFilter = createRoomVerificationFilter(command,  command.courseId(), command.number());

        RoomVerificationPage roomVerificationPage = RoomVerificationPage.of(command.pageNumber(), command.pageSize());

        return roomVerificationRepository.findAll(roomVerificationFilter, roomVerificationPage);
    }

    private RoomVerificationFilter createRoomVerificationFilter(GetAllRoomVerificationCommand command, long l, int number) {
        ClassEntityId classEntityId = new ClassEntityId(
                command.courseId(),
                command.number()
        );

        return RoomVerificationFilter.builder()
                .periodId(command.periodId())
                .allOrganized(command.allOrganized())
                .classEntityId(classEntityId)
                .ticket(command.ticket())
                .description(command.description())
                .build();
    }

    public void validateUserPermissions(ClassEntityId classEntityId, long userId){
        ClassUserId classUserId = new ClassUserId(userId, classEntityId);
        ClassUser classUser = classUserRepository.findById(classUserId)
                .orElseThrow(ClassUserNotFoundException::new);

        if (!permissionPolicy.canGet(classUser.getUserEntity().getRole(), classUser.getTypeUserClass())) {
            throw new UserNotHavePermissionToGetRoomVerificationException();
        }
    }

}
