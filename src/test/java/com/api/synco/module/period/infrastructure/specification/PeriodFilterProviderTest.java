package com.api.synco.module.period.infrastructure.specification;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.enumerator.TypePeriod;
import com.api.synco.module.period.domain.filter.PeriodFilter;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeriodFilterProviderTest {

    @InjectMocks
    private PeriodFilterProvider periodFilterProvider;

    @Mock
    private Root<PeriodEntity> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder cb;

    @Mock
    private Path<Object> path;

    @BeforeEach
    void setUp() {
        lenient().when(root.get(anyString())).thenReturn(path);
        lenient().when(path.get(anyString())).thenReturn(path);
    }

    @Test
    @DisplayName("Deve construir predicados quando todos os filtros estiverem preenchidos")
    void shouldBuildPredicatesWhenFilterIsFull() {
        TypePeriod expectedType = TypePeriod.AFTERNOON;
        PeriodFilter filter = new PeriodFilter(1L, 2L, 3L, expectedType);

        Specification<PeriodEntity> spec = periodFilterProvider.of(filter);

        // Act
        spec.toPredicate(root, query, cb);

        verify(cb).equal(any(), eq(1L));
        verify(cb).equal(any(), eq(2L));
        verify(cb).equal(any(), eq(3L));

        verify(cb).equal(any(), eq(expectedType));

        verify(cb).and(any(Predicate[].class));
    }

    @Test
    void shouldNotApplyFiltersWhenEmpty() {
        // Arrange
        PeriodFilter filter = new PeriodFilter(0, 0, 0, null);
        Specification<PeriodEntity> spec = periodFilterProvider.of(filter);

        // Act
        spec.toPredicate(root, query, cb);

        // Assert
        verify(cb, never()).equal(any(), any());
    }

}