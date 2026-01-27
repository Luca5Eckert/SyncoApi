package com.api.synco.module.period.domain.exception;

public class UserWithoutCreatePeriodPermissionException extends PeriodDomainException {
    public UserWithoutCreatePeriodPermissionException(String message) {
        super(message);
    }

    public UserWithoutCreatePeriodPermissionException() {
        super("User does not have permission to create a period.");
    }
}
