package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.room.application.dto.CreateRoomRequest;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.enumerator.TypeRoom;
import com.api.synco.module.room.domain.exception.number.RoomNotUniqueNumberException;
import com.api.synco.module.room.domain.exception.user.UserWithoutCreateRoomPermissionException;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
    private CreateRoomUseCase createRoomUseCase;

    // Constantes para reutilização e legibilidade
    private static final long USER_ID = 1L;
    private static final int ROOM_NUMBER = 101;
    private static final TypeRoom ROOM_TYPE = TypeRoom.LAB_INFORMATICA;
    private static final RoleUser USER_ROLE = RoleUser.ADMIN;

    @Test
    void shouldCreateRoomSuccessfully() {
        var request = new CreateRoomRequest(ROOM_NUMBER, ROOM_TYPE);

        var user = new UserEntity();
        user.setRole(USER_ROLE);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(permissionPolicy.canCreate(USER_ROLE)).thenReturn(true);
        when(roomRepository.existByNumber(ROOM_NUMBER)).thenReturn(false);

        RoomEntity result = createRoomUseCase.execute(request, USER_ID);

        assertNotNull(result);
        assertEquals(ROOM_NUMBER, result.getNumber());
        assertEquals(ROOM_TYPE, result.getTypeRoom());

        ArgumentCaptor<RoomEntity> roomCaptor = ArgumentCaptor.forClass(RoomEntity.class);
        verify(roomRepository).save(roomCaptor.capture());

        RoomEntity savedRoom = roomCaptor.getValue();
        assertEquals(ROOM_NUMBER, savedRoom.getNumber());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        var request = new CreateRoomRequest(ROOM_NUMBER, ROOM_TYPE);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundDomainException.class,
                () -> createRoomUseCase.execute(request, USER_ID));

        verifyNoInteractions(permissionPolicy);
        verifyNoInteractions(roomRepository);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotHavePermission() {
        var request = new CreateRoomRequest(ROOM_NUMBER, ROOM_TYPE);

        var user = new UserEntity();
        user.setRole(USER_ROLE);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(permissionPolicy.canCreate(USER_ROLE)).thenReturn(false);

        assertThrows(UserWithoutCreateRoomPermissionException.class,
                () -> createRoomUseCase.execute(request, USER_ID));

        verify(roomRepository, never()).existByNumber(anyInt());
        verify(roomRepository, never()).save(any(RoomEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenRoomNumberAlreadyExists() {
        var request = new CreateRoomRequest(ROOM_NUMBER, ROOM_TYPE);

        var user = new UserEntity();
        user.setRole(USER_ROLE);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(permissionPolicy.canCreate(USER_ROLE)).thenReturn(true);
        when(roomRepository.existByNumber(ROOM_NUMBER)).thenReturn(true);

        assertThrows(RoomNotUniqueNumberException.class,
                () -> createRoomUseCase.execute(request, USER_ID));

        verify(roomRepository, never()).save(any(RoomEntity.class));
    }
}