package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.course.domain.CourseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClassUseCaseTest {

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private GetClassUseCase getClassUseCase;

    private ClassEntityId classEntityId;
    private ClassEntity classEntity;
    private CourseEntity courseEntity;

    @BeforeEach
    void setup() {
        courseEntity = new CourseEntity(1L, "Computer Science", "CS", "CS Description");
        classEntityId = new ClassEntityId(1L, 1);
        classEntity = new ClassEntity(classEntityId, courseEntity, 800, Shift.FIRST_SHIFT);
    }

    @DisplayName("Should return class when found by ID")
    @Test
    void shouldReturnClassWhenFound() {
        // arrange
        when(classRepository.findById(classEntityId)).thenReturn(Optional.of(classEntity));

        // act
        var result = getClassUseCase.execute(classEntityId);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(classEntityId);
        assertThat(result.getCourse()).isEqualTo(courseEntity);
        assertThat(result.getTotalHours()).isEqualTo(800);
        assertThat(result.getShift()).isEqualTo(Shift.FIRST_SHIFT);

        verify(classRepository).findById(classEntityId);
    }

    @DisplayName("Should throw ClassNotFoundException when class not found")
    @Test
    void shouldThrowClassNotFoundExceptionWhenNotFound() {
        // arrange
        when(classRepository.findById(classEntityId)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(() -> getClassUseCase.execute(classEntityId))
                .isInstanceOf(ClassNotFoundException.class);

        verify(classRepository).findById(classEntityId);
    }

    @DisplayName("Should return class with second shift")
    @Test
    void shouldReturnClassWithSecondShift() {
        // arrange
        var classWithSecondShift = new ClassEntity(classEntityId, courseEntity, 600, Shift.SECOND_SHIFT);
        when(classRepository.findById(classEntityId)).thenReturn(Optional.of(classWithSecondShift));

        // act
        var result = getClassUseCase.execute(classEntityId);

        // assert
        assertThat(result.getShift()).isEqualTo(Shift.SECOND_SHIFT);
        assertThat(result.getTotalHours()).isEqualTo(600);
    }
}
