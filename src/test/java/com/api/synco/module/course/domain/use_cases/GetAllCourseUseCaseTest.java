package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.filter.CourseFilter;
import com.api.synco.module.course.domain.filter.PageCourse;
import com.api.synco.module.course.domain.port.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllCourseUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GetAllCourseUseCase getAllCourseUseCase;

    private List<CourseEntity> courseList;

    @BeforeEach
    void setup() {
        courseList = Arrays.asList(
            new CourseEntity(1L, "Computer Science", "CS", "CS Description"),
            new CourseEntity(2L, "Mathematics", "MATH", "Math Description"),
            new CourseEntity(3L, "Physics", "PHY", "Physics Description")
        );
    }

    @DisplayName("Should return all courses without filters")
    @Test
    void shouldReturnAllCoursesWithoutFilters() {
        // arrange
        Page<CourseEntity> page = new PageImpl<>(courseList);
        when(courseRepository.findAll(any(Specification.class), any(PageCourse.class))).thenReturn(page);

        // act
        var result = getAllCourseUseCase.execute(null, null, 0, 10);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Computer Science");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Mathematics");
        assertThat(result.getContent().get(2).getName()).isEqualTo("Physics");

        verify(courseRepository).findAll(any(Specification.class), any(PageCourse.class));
    }

    @DisplayName("Should return filtered courses by name")
    @Test
    void shouldReturnFilteredCoursesByName() {
        // arrange
        List<CourseEntity> filteredList = Collections.singletonList(
            new CourseEntity(1L, "Computer Science", "CS", "CS Description")
        );
        Page<CourseEntity> page = new PageImpl<>(filteredList);
        when(courseRepository.findAll(any(Specification.class), any(PageCourse.class))).thenReturn(page);

        // act
        var result = getAllCourseUseCase.execute("Computer", null, 0, 10);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Computer Science");

        verify(courseRepository).findAll(any(Specification.class), any(PageCourse.class));
    }

    @DisplayName("Should return filtered courses by acronym")
    @Test
    void shouldReturnFilteredCoursesByAcronym() {
        // arrange
        List<CourseEntity> filteredList = Collections.singletonList(
            new CourseEntity(2L, "Mathematics", "MATH", "Math Description")
        );
        Page<CourseEntity> page = new PageImpl<>(filteredList);
        when(courseRepository.findAll(any(Specification.class), any(PageCourse.class))).thenReturn(page);

        // act
        var result = getAllCourseUseCase.execute(null, "MATH", 0, 10);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getAcronym()).isEqualTo("MATH");

        verify(courseRepository).findAll(any(Specification.class), any(PageCourse.class));
    }

    @DisplayName("Should return empty page when no courses found")
    @Test
    void shouldReturnEmptyPageWhenNoCoursesFound() {
        // arrange
        Page<CourseEntity> emptyPage = new PageImpl<>(Collections.emptyList());
        when(courseRepository.findAll(any(Specification.class), any(PageCourse.class))).thenReturn(emptyPage);

        // act
        var result = getAllCourseUseCase.execute("NonExistent", null, 0, 10);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();

        verify(courseRepository).findAll(any(Specification.class), any(PageCourse.class));
    }

    @DisplayName("Should handle pagination correctly")
    @Test
    void shouldHandlePaginationCorrectly() {
        // arrange
        List<CourseEntity> paginatedList = Collections.singletonList(
            new CourseEntity(2L, "Mathematics", "MATH", "Math Description")
        );
        Page<CourseEntity> page = new PageImpl<>(paginatedList);
        when(courseRepository.findAll(any(Specification.class), any(PageCourse.class))).thenReturn(page);

        // act
        var result = getAllCourseUseCase.execute(null, null, 1, 1);

        // assert
        assertThat(result).isNotNull();

        var pageCaptor = ArgumentCaptor.forClass(PageCourse.class);
        verify(courseRepository).findAll(any(Specification.class), pageCaptor.capture());
        var capturedPage = pageCaptor.getValue();
        assertThat(capturedPage.pageNumber()).isEqualTo(1);
        assertThat(capturedPage.pageSize()).isEqualTo(1);
    }
}
