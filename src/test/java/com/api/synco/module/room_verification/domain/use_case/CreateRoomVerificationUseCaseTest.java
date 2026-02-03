package com.api.synco.module.room_verification.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntity;
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
import com.api.synco.module.user.domain.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRoomVerificationUseCaseTest {

    @Mock private RoomVerificationRepository roomVerificationRepository;
    @Mock private PeriodRepository periodRepository;
    @Mock private ClassUserRepository classUserRepository;
    @Mock private RoomVerificationPermissionPolicy permissionPolicy;

    @InjectMocks
    private CreateRoomVerificationUseCase useCase;

    private CreateRoomVerificationCommand command;
    private PeriodEntity period;
    private ClassEntityId classId;

    @BeforeEach
    void setUp() {
        classId = new ClassEntityId(2L, 100);
        command = new CreateRoomVerificationCommand(1L, 2L, true, "Obs", "TKT-123", "aoefaho");
        period = mock(PeriodEntity.class);
    }


    private void setupFullPeriodChain() {
        ClassEntity classEntity = mock(ClassEntity.class);
        when(period.getId()).thenReturn(1L);
        when(period.getClassEntity()).thenReturn(classEntity);
        when(classEntity.getId()).thenReturn(classId);
    }

    @Test
    void shouldCreateRoomVerificationSuccessfully() {
        // Arrange
        setupFullPeriodChain();
        when(periodRepository.findById(command.periodId())).thenReturn(Optional.of(period));
        when(roomVerificationRepository.existByPeriodId(1L)).thenReturn(false);

        ClassUser classUser = mock(ClassUser.class);
        UserEntity user = mock(UserEntity.class);
        when(classUserRepository.findById(any(ClassUserId.class))).thenReturn(Optional.of(classUser));
        when(classUser.getUserEntity()).thenReturn(user);
        when(permissionPolicy.canCreate(any(), any())).thenReturn(true);

        // Act
        RoomVerificationEntity result = useCase.execute(command);

        // Assert
        assertNotNull(result);
        verify(roomVerificationRepository, times(1)).save(any(RoomVerificationEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenPeriodNotFound() {
        // Arrange - Não chamamos setupFullPeriodChain, então o Mockito não reclama
        when(periodRepository.findById(command.periodId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PeriodNotFoundException.class, () -> useCase.execute(command));
        verify(roomVerificationRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenAlreadyExists() {
        // Arrange
        when(period.getId()).thenReturn(1L); // Só precisamos do ID para este passo
        when(periodRepository.findById(command.periodId())).thenReturn(Optional.of(period));
        when(roomVerificationRepository.existByPeriodId(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(RoomVerificationAlreadyExistException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowExceptionWhenUserNotInClass() {
        // Arrange
        setupFullPeriodChain();
        when(periodRepository.findById(command.periodId())).thenReturn(Optional.of(period));
        when(roomVerificationRepository.existByPeriodId(1L)).thenReturn(false);
        when(classUserRepository.findById(any(ClassUserId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClassUserNotFoundException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowExceptionWhenPermissionDenied() {
        // Arrange
        setupFullPeriodChain();
        when(periodRepository.findById(command.periodId())).thenReturn(Optional.of(period));
        when(roomVerificationRepository.existByPeriodId(1L)).thenReturn(false);

        ClassUser classUser = mock(ClassUser.class);
        UserEntity user = mock(UserEntity.class);
        when(classUserRepository.findById(any(ClassUserId.class))).thenReturn(Optional.of(classUser));
        when(classUser.getUserEntity()).thenReturn(user);
        when(permissionPolicy.canCreate(any(), any())).thenReturn(false);

        // Act & Assert
        assertThrows(UserNotHavePermissionToCreateRoomVerificationException.class,
                () -> useCase.execute(command));
    }

}