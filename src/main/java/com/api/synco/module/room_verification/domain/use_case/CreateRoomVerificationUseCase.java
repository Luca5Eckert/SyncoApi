package com.api.synco.module.room_verification.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.exception.PeriodNotFoundException;
import com.api.synco.module.period.domain.port.PeriodRepository;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.CreateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.exception.RoomVerificationAlreadyExistException;
import com.api.synco.module.room_verification.domain.exception.UserNotHavePermissionToCreateRoomVerificationException;
import com.api.synco.module.room_verification.domain.permission.RoomVerificationPermissionPolicy;
import com.api.synco.module.room_verification.domain.port.RoomVerificationRepository;
import com.api.synco.module.room_verification.domain.value_object.RoomVerificationForm;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreateRoomVerificationUseCase {

    private final RoomVerificationRepository roomVerificationRepository;
    private final PeriodRepository periodRepository;
    private final ClassUserRepository classUserRepository;
    private final RoomVerificationPermissionPolicy permissionPolicy;

    public CreateRoomVerificationUseCase(RoomVerificationRepository roomVerificationRepository, PeriodRepository periodRepository, ClassUserRepository classUserRepository, RoomVerificationPermissionPolicy permissionPolicy) {
        this.roomVerificationRepository = roomVerificationRepository;
        this.periodRepository = periodRepository;
        this.classUserRepository = classUserRepository;
        this.permissionPolicy = permissionPolicy;
    }

    public RoomVerificationEntity execute(CreateRoomVerificationCommand command) {
        PeriodEntity period = periodRepository.findById(command.periodId())
                .orElseThrow(PeriodNotFoundException::new);

        if (roomVerificationRepository.existByPeriodId(period.getId())) {
            throw new RoomVerificationAlreadyExistException();
        }
        validateUserPermissions(command.userAuthenticatedId(), period.getClassEntity().getId());

        RoomVerificationEntity entity = mapToEntity(command, period);
        return roomVerificationRepository.save(entity);
    }

    private void validateUserPermissions(long userId, ClassEntityId classEntityId) {
        ClassUserId classUserId = new ClassUserId(userId, classEntityId);
        ClassUser classUser = classUserRepository.findById(classUserId)
                .orElseThrow(ClassUserNotFoundException::new);

        if (!permissionPolicy.canCreate(classUser.getUserEntity().getRole(), classUser.getTypeUserClass())) {
            throw new UserNotHavePermissionToCreateRoomVerificationException();
        }
    }

    private RoomVerificationEntity mapToEntity(CreateRoomVerificationCommand command, PeriodEntity period) {
        RoomVerificationForm form = RoomVerificationForm.builder()
                .allOrganized(command.allOrganized())
                .observations(command.observations())
                .ticket(command.ticket())
                .build();

        return new RoomVerificationEntity(
                period,
                LocalDate.now(),
                form
        );
    }

}