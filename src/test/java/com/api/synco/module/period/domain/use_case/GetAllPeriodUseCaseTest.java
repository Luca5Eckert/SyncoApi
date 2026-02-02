package com.api.synco.module.period.domain.use_case;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.enumerator.TypePeriod;
import com.api.synco.module.period.domain.filter.PeriodFilter;
import com.api.synco.module.period.domain.filter.PeriodPage;
import com.api.synco.module.period.domain.port.PeriodRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllPeriodUseCaseTest {

    @InjectMocks
    private GetAllPeriodUseCase getAllPeriodUseCase;

    @Mock
    private PeriodRepository periodRepository;

    @Test
    @DisplayName("Deve converter parâmetros corretamente para Filter e Page e chamar o repositório")
    void shouldMapParametersToFilterAndPageAndCallRepository() {
        // Arrange
        long teacherId = 1L;
        long roomId = 2L;
        long classId = 3L;
        TypePeriod typePeriod = TypePeriod.MORNING;
        int pageNumber = 0;
        int pageSize = 10;

        // Criamos um mock da página de retorno
        Page<PeriodEntity> expectedPage = new PageImpl<>(List.of());
        when(periodRepository.findAll(any(PeriodFilter.class), any(PeriodPage.class)))
                .thenReturn(expectedPage);

        // Capturadores para validar os objetos criados internamente
        ArgumentCaptor<PeriodFilter> filterCaptor = ArgumentCaptor.forClass(PeriodFilter.class);
        ArgumentCaptor<PeriodPage> pageCaptor = ArgumentCaptor.forClass(PeriodPage.class);

        // Act
        Page<PeriodEntity> result = getAllPeriodUseCase.execute(
                teacherId, roomId, classId, typePeriod, pageNumber, pageSize
        );

        // Assert
        verify(periodRepository).findAll(filterCaptor.capture(), pageCaptor.capture());

        PeriodFilter capturedFilter = filterCaptor.getValue();
        assertEquals(teacherId, capturedFilter.teacherId());
        assertEquals(roomId, capturedFilter.roomId());
        assertEquals(classId, capturedFilter.classId());
        assertEquals(typePeriod, capturedFilter.typePeriod());

        PeriodPage capturedPage = pageCaptor.getValue();
        assertEquals(pageNumber, capturedPage.pageNumber());
        assertEquals(pageSize, capturedPage.pageSize());

        assertEquals(expectedPage, result);
    }
}