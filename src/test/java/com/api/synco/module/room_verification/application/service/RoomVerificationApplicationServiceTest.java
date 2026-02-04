package com.api.synco.module.room_verification.application.service;

import com.api.synco.module.room_verification.application.dto.RoomVerificationResponse;
import com.api.synco.module.room_verification.application.dto.create.CreateRoomVerificationRequest;
import com.api.synco.module.room_verification.application.dto.update.UpdateRoomVerificationRequest;
import com.api.synco.module.room_verification.application.mapper.RoomVerificationMapper;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.CreateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.command.UpdateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.use_case.CreateRoomVerificationUseCase;
import com.api.synco.module.room_verification.domain.use_case.UpdateRoomVerificationUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomVerificationApplicationServiceTest {

    @Mock
    private CreateRoomVerificationUseCase createRoomVerificationUseCase;

    @Mock
    private UpdateRoomVerificationUseCase updateRoomVerificationUseCase;

    @Mock
    private RoomVerificationMapper roomVerificationMapper;

    @InjectMocks
    private RoomVerificationApplicationService applicationService;

    @Test
    @DisplayName("Should successfully coordinate the creation of a room verification")
    void shouldCreateRoomVerificationSuccessfully() {
        // Arrange
        long periodId = 1L;
        long userId = 100L;

        CreateRoomVerificationRequest request = mock(CreateRoomVerificationRequest.class);
        CreateRoomVerificationCommand command = mock(CreateRoomVerificationCommand.class);
        RoomVerificationEntity entity = mock(RoomVerificationEntity.class);
        RoomVerificationResponse expectedResponse = mock(RoomVerificationResponse.class);

        // Define the flow
        when(roomVerificationMapper.toCreateCommand(request, periodId, userId)).thenReturn(command);
        when(createRoomVerificationUseCase.execute(command)).thenReturn(entity);
        when(roomVerificationMapper.toResponse(entity)).thenReturn(expectedResponse);

        // Act
        RoomVerificationResponse actualResponse = applicationService.create(request, periodId, userId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        // Verify interactions
        verify(roomVerificationMapper).toCreateCommand(request, periodId, userId);
        verify(createRoomVerificationUseCase).execute(command);
        verify(roomVerificationMapper).toResponse(entity);
    }

    @Test
    @DisplayName("Should successfully coordinate the update of a room verification")
    void shouldUpdateRoomVerificationSuccessfully() {
        // Arrange
        long periodId = 1L;
        long userId = 100L;

        UpdateRoomVerificationCommand command = mock(UpdateRoomVerificationCommand.class);
        UpdateRoomVerificationRequest request = mock(UpdateRoomVerificationRequest.class);
        RoomVerificationEntity entity = mock(RoomVerificationEntity.class);
        RoomVerificationResponse expectedResponse = mock(RoomVerificationResponse.class);

        // Define the flow
        when(roomVerificationMapper.toUpdateCommand(request, periodId, userId)).thenReturn(command);
        when(updateRoomVerificationUseCase.execute(command)).thenReturn(entity);
        when(roomVerificationMapper.toResponse(entity)).thenReturn(expectedResponse);

        // Act
        RoomVerificationResponse actualResponse = applicationService.update(request, periodId, userId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        // Verify interactions
        verify(roomVerificationMapper).toUpdateCommand(request, periodId, userId);
        verify(updateRoomVerificationUseCase).execute(command);
        verify(roomVerificationMapper).toResponse(entity);
    }
}