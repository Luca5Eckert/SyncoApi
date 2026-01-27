package com.api.synco.module.period.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.command.CreatePeriodCommand;
import com.api.synco.module.period.domain.exception.UserWithoutCreatePeriodPermissionException;
import com.api.synco.module.period.domain.permission.PeriodPermissionPolicy;
import com.api.synco.module.period.domain.port.PeriodRepository;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.exception.RoomNotExistException;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePeriodUseCase {

    private final PeriodRepository periodRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ClassRepository classRepository;
    private final ClassUserRepository classUserRepository;
    private final PeriodPermissionPolicy periodPermissionPolicy;

    public CreatePeriodUseCase(
            PeriodRepository periodRepository,
            UserRepository userRepository,
            RoomRepository roomRepository,
            ClassRepository classRepository,
            ClassUserRepository classUserRepository,
            PeriodPermissionPolicy periodPermissionPolicy
    ) {
        this.periodRepository = periodRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.classRepository = classRepository;
        this.classUserRepository = classUserRepository;
        this.periodPermissionPolicy = periodPermissionPolicy;
    }

    @Transactional
    public PeriodEntity execute(CreatePeriodCommand command) {
        UserEntity authenticatedUser = userRepository.findById(command.authenticatedUserId())
                .orElseThrow(() -> new UserNotFoundDomainException(command.authenticatedUserId()));

        UserEntity teacher = userRepository.findById(command.teacherId())
                .orElseThrow(() -> new UserNotFoundDomainException(command.teacherId()));

        ClassUser authenticatedClassUser = classUserRepository
                .findById(new ClassUserId(authenticatedUser.getId(), command.classId()))
                .orElseThrow(UserWithoutCreatePeriodPermissionException::new);

        ClassUser teacherClassUser = classUserRepository.findById(new ClassUserId(teacher.getId(), command.classId()))
                .orElseThrow( () -> new ClassUserNotFoundException("Teacher is not linked to this class") );

        if (!periodPermissionPolicy.canCreate(
                authenticatedClassUser.getTypeUserClass(),
                teacherClassUser.getTypeUserClass(),
                authenticatedUser.getRole()
        )) {
            throw new UserWithoutCreatePeriodPermissionException();
        }

        RoomEntity room = roomRepository.findById(command.roomId())
                .orElseThrow(() -> new RoomNotExistException(command.roomId()));

        ClassEntity classEntity = classRepository.findById(command.classId())
                .orElseThrow(ClassNotFoundException::new);

        PeriodEntity period = new PeriodEntity(
                teacher,
                room,
                classEntity,
                command.date(),
                command.typePeriod()
        );

        return periodRepository.save(period);
    }
}
