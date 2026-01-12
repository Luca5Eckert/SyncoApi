package com.api.synco.modules.academic_class.domain.enumerator;

import com.api.synco.module.period.domain.enumerator.TypePeriod;

public enum Shift {
    FIRST_SHIFT(TypePeriod.MORNING, TypePeriod.AFTERNOON),
    SECOND_SHIFT(TypePeriod.AFTERNOON, TypePeriod.EVENING);

    public final TypePeriod firstPeriod;
    public final TypePeriod secondPeriod;

    public TypePeriod getFirstPeriod() {
        return firstPeriod;
    }

    public TypePeriod getSecondPeriod() {
        return secondPeriod;
    }

    Shift(TypePeriod firstPeriod, TypePeriod secondPeriod) {
        this.firstPeriod = firstPeriod;
        this.secondPeriod = secondPeriod;
    }

    public boolean isItsAValidPeriod(TypePeriod typePeriod){
        return firstPeriod == typePeriod || secondPeriod == typePeriod;
    }

}
