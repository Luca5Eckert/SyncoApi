package com.api.synco.module.class_user.domain.use_cases;

import static org.junit.jupiter.api.Assertions.*;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetClassUserUseCaseTest {

    @Mock
    private ClassUserRepository classUserRepository;

    @InjectMocks
    private GetClassUserUseCase getClassUserUseCase;

    @Mock
    private ClassUserId classUserId;

    @Mock
    private ClassUser classUser;

    @Test
    @DisplayName("Should successfully return ClassUser when found by ID")
    void shouldReturnClassUser_WhenFound() {
        // GIVEN
        when(classUserRepository.findById(classUserId)).thenReturn(Optional.of(classUser));

        // WHEN
        ClassUser result = getClassUserUseCase.execute(classUserId);

        // THEN
        assertNotNull(result);
        assertEquals(classUser, result);
        verify(classUserRepository, times(1)).findById(classUserId);
    }

    @Test
    @DisplayName("Should throw ClassUserNotFoundException when ClassUser is not found")
    void shouldThrowException_WhenNotFound() {
        // GIVEN
        when(classUserRepository.findById(classUserId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ClassUserNotFoundException.class, () ->
                getClassUserUseCase.execute(classUserId)
        );

        verify(classUserRepository, times(1)).findById(classUserId);
    }

}