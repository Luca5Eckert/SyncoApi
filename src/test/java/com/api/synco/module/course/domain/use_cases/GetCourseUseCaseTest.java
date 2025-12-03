package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.port.CourseRepository;
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
class GetCourseUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GetCourseUseCase getCourseUseCase;

    private long courseId;
    private CourseEntity courseEntity;

    @BeforeEach
    void setup() {
        courseId = 1L;
        courseEntity = new CourseEntity(courseId, "Computer Science", "CS", "Computer Science course description");
    }

    @DisplayName("Should return course when found by ID")
    @Test
    void shouldReturnCourseWhenFound() {
        // arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));

        // act
        var result = getCourseUseCase.execute(courseId);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(courseId);
        assertThat(result.getName()).isEqualTo("Computer Science");
        assertThat(result.getAcronym()).isEqualTo("CS");
        assertThat(result.getDescription()).isEqualTo("Computer Science course description");

        verify(courseRepository).findById(courseId);
    }

    @DisplayName("Should throw CourseNotFoundException when course not found")
    @Test
    void shouldThrowCourseNotFoundExceptionWhenNotFound() {
        // arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(() -> getCourseUseCase.execute(courseId))
                .isInstanceOf(CourseNotFoundException.class);

        verify(courseRepository).findById(courseId);
    }
}
