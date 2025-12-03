package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.filter.ClassUserFilter;
import com.api.synco.module.class_user.domain.filter.ClassUserSearchProvider;
import com.api.synco.module.class_user.domain.filter.PageClassUser;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; // Importante: Usar a interface
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllClassUserUseCaseTest {

    @InjectMocks
    private GetAllClassUserUseCase getAllClassUserUseCase;

    @Mock
    private ClassUserRepository classUserRepository;

    // A anotação @Captor resolve problemas de inferência de tipos genéricos
    @Captor
    private ArgumentCaptor<Specification<ClassUser>> specCaptor;

    @Captor
    private ArgumentCaptor<PageRequest> pageableCaptor;

    @Test
    @DisplayName("Should return a page of ClassUsers with correct pagination and specification")
    void shouldReturnPageOfClassUsers() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 10;
        PageClassUser pageClassUser = new PageClassUser(pageNumber, pageSize);

        ClassUserFilter classUserFilter = mock(ClassUserFilter.class);
        Page<ClassUser> expectedPage = new PageImpl<>(List.of(new ClassUser(), new ClassUser()));

        Specification<ClassUser> mockedSpecification = mock(Specification.class);

        try (MockedStatic<ClassUserSearchProvider> providerMock = mockStatic(ClassUserSearchProvider.class)) {

            providerMock.when(() -> ClassUserSearchProvider.of(classUserFilter))
                    .thenReturn(mockedSpecification);

            when(classUserRepository.findAll(any(Specification.class), any(PageRequest.class)))
                    .thenReturn(expectedPage);

            // Act
            Page<ClassUser> result = getAllClassUserUseCase.execute(classUserFilter, pageClassUser);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(2);
            assertThat(result).isEqualTo(expectedPage);

            verify(classUserRepository).findAll(specCaptor.capture(), pageableCaptor.capture());

            assertThat(specCaptor.getValue()).isEqualTo(mockedSpecification);

            Pageable capturedPageable = pageableCaptor.getValue();
            assertThat(capturedPageable.getPageNumber()).isEqualTo(pageNumber);
            assertThat(capturedPageable.getPageSize()).isEqualTo(pageSize);

        }
    }
}