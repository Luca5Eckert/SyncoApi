package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.command.CreateRoomCommand;
import com.api.synco.module.room.domain.enumerator.TypeRoom;
import com.api.synco.module.room.domain.exception.number.RoomNotUniqueNumberException;
import com.api.synco.module.room.domain.exception.user.UserWithoutCreateRoomPermissionException;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRoomUseCaseTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionPolicy permissionPolicy;

    @InjectMocks
    private CreateRoomUseCase useCase;

    private long userId;

    @BeforeEach
    void setup() {
        userId = 1L;
    }

    @Test
    void shouldCreateRoomSuccessfully() {
        CreateRoomCommand command =
                new CreateRoomCommand(10, TypeRoom.LAB_INFORMATICA, userId);

        UserEntity user = mock(UserEntity.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(permissionPolicy.canCreate(user.getRole())).thenReturn(true);
        when(roomRepository.existByNumber(10)).thenReturn(false);

        RoomEntity result = useCase.execute(command);

        assertNotNull(result);
        assertEquals(10, result.getNumber());
        assertEquals(TypeRoom.LAB_INFORMATICA, result.getTypeRoom());

        verify(roomRepository).save(any(RoomEntity.class));
    }

    @Test
    void shouldThrowUserNotFoundException() {
        CreateRoomCommand command =
                new CreateRoomCommand(10, TypeRoom.LAB_INFORMATICA, userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundDomainException.class,
                () -> useCase.execute(command)
        );

        verifyNoInteractions(permissionPolicy, roomRepository);
    }

    @Test
    void shouldThrowPermissionException() {
        CreateRoomCommand command =
                new CreateRoomCommand(10, TypeRoom.LAB_INFORMATICA, userId);

        UserEntity user = mock(UserEntity.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(permissionPolicy.canCreate(user.getRole())).thenReturn(false);

        assertThrows(
                UserWithoutCreateRoomPermissionException.class,
                () -> useCase.execute(command)
        );

        verify(roomRepository, never()).save(any());
    }

    @Test
    void shouldThrowRoomNotUniqueNumberException() {
        CreateRoomCommand command =
                new CreateRoomCommand(10, TypeRoom.LAB_INFORMATICA, userId);

        UserEntity user = mock(UserEntity.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(permissionPolicy.canCreate(user.getRole())).thenReturn(true);
        when(roomRepository.existByNumber(10)).thenReturn(true);

        assertThrows(
                RoomNotUniqueNumberException.class,
                () -> useCase.execute(command)
        );

        verify(roomRepository, never()).save(any());
    }

}
