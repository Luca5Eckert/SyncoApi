package com.api.synco.module.period.domain.use_case;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.exception.PeriodNotFoundException;
import com.api.synco.module.period.domain.port.PeriodRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPeriodUseCaseTest {

    @Mock
    private PeriodRepository periodRepository;

    @InjectMocks
    private GetPeriodUseCase periodUseCase;

    private long periodId;

    private PeriodEntity period;

    @BeforeEach
    public void setup(){
        periodId = 122L;
        period = new PeriodEntity(
                periodId,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Test
    @DisplayName("When period exist have to return entity")
    public void whenPeriodExistHaveReturnEntity(){
        // arrange
        when(periodRepository.findById(any(long.class))).thenReturn(Optional.of(period));

        // act
        PeriodEntity returnedPeriod = periodUseCase.execute(periodId);

         // assert
        assertEquals(returnedPeriod.getId(), periodId);
    }

    @Test
    public void whenPeriodNotExistHaveToThrowException(){
        //arrange
        when(periodRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // act and arrange
        assertThatThrownBy(() -> periodUseCase.execute(periodId))
                .isInstanceOf(PeriodNotFoundException.class);
    }




}