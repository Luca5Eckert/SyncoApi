package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.filter.ClassFilter;
import com.api.synco.module.class_entity.domain.filter.PageClass;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.course.domain.CourseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllClassUseCaseTest {

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private GetAllClassUseCase getAllClassUseCase;

    private List<ClassEntity> classList;
    private CourseEntity courseEntity;
    private PageClass pageClass;
    private ClassFilter classFilter;

    @BeforeEach
    void setup() {
        courseEntity = new CourseEntity(1L, "Computer Science", "CS", "CS Description");
        classList = Arrays.asList(
            new ClassEntity(new ClassEntityId(1L, 1), courseEntity, 800, Shift.FIRST_SHIFT),
            new ClassEntity(new ClassEntityId(1L, 2), courseEntity, 700, Shift.SECOND_SHIFT),
            new ClassEntity(new ClassEntityId(1L, 3), courseEntity, 600, Shift.FIRST_SHIFT)
        );
        pageClass = new PageClass(0, 10);
        classFilter = ClassFilter.builder().build();
    }

    @DisplayName("Should return all classes without filters")
    @Test
    void shouldReturnAllClassesWithoutFilters() {
        // arrange
        Page<ClassEntity> page = new PageImpl<>(classList);
        when(classRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        // act
        var result = getAllClassUseCase.execute(pageClass, classFilter);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getContent().get(0).getTotalHours()).isEqualTo(800);
        assertThat(result.getContent().get(1).getTotalHours()).isEqualTo(700);
        assertThat(result.getContent().get(2).getTotalHours()).isEqualTo(600);

        verify(classRepository).findAll(any(Specification.class), any(PageRequest.class));
    }

    @DisplayName("Should return filtered classes by shift")
    @Test
    void shouldReturnFilteredClassesByShift() {
        // arrange
        var filteredFilter = ClassFilter.builder().setShiftContains(Shift.FIRST_SHIFT).build();
        List<ClassEntity> filteredList = Arrays.asList(
            new ClassEntity(new ClassEntityId(1L, 1), courseEntity, 800, Shift.FIRST_SHIFT),
            new ClassEntity(new ClassEntityId(1L, 3), courseEntity, 600, Shift.FIRST_SHIFT)
        );
        Page<ClassEntity> page = new PageImpl<>(filteredList);
        when(classRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        // act
        var result = getAllClassUseCase.execute(pageClass, filteredFilter);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).allMatch(c -> c.getShift() == Shift.FIRST_SHIFT);

        verify(classRepository).findAll(any(Specification.class), any(PageRequest.class));
    }

    @DisplayName("Should return filtered classes by course ID")
    @Test
    void shouldReturnFilteredClassesByCourseId() {
        // arrange
        var courseFilter = ClassFilter.builder().setCourseIdContains(1L).build();
        Page<ClassEntity> page = new PageImpl<>(classList);
        when(classRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        // act
        var result = getAllClassUseCase.execute(pageClass, courseFilter);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(3);

        verify(classRepository).findAll(any(Specification.class), any(PageRequest.class));
    }

    @DisplayName("Should return empty page when no classes found")
    @Test
    void shouldReturnEmptyPageWhenNoClassesFound() {
        // arrange
        Page<ClassEntity> emptyPage = new PageImpl<>(Collections.emptyList());
        when(classRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(emptyPage);

        // act
        var result = getAllClassUseCase.execute(pageClass, classFilter);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();

        verify(classRepository).findAll(any(Specification.class), any(PageRequest.class));
    }

    @DisplayName("Should handle pagination correctly")
    @Test
    void shouldHandlePaginationCorrectly() {
        // arrange
        var paginatedPageClass = new PageClass(1, 1);
        List<ClassEntity> paginatedList = Collections.singletonList(
            new ClassEntity(new ClassEntityId(1L, 2), courseEntity, 700, Shift.SECOND_SHIFT)
        );
        Page<ClassEntity> page = new PageImpl<>(paginatedList);
        when(classRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        // act
        var result = getAllClassUseCase.execute(paginatedPageClass, classFilter);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);

        verify(classRepository).findAll(any(Specification.class), any(PageRequest.class));
    }
}
