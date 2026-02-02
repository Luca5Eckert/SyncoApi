package com.api.synco.module.period.domain.exception;

public class PeriodNotFoundException extends PeriodDomainException {
    public PeriodNotFoundException(String message) {
        super(message);
    }

    public PeriodNotFoundException(){
        super("Period not found");
    }

}
