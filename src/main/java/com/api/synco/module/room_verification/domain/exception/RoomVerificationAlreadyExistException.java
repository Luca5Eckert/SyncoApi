package com.api.synco.module.room_verification.domain.exception;

public class RoomVerificationAlreadyExistException extends RoomVerificationDomainException {
    public RoomVerificationAlreadyExistException() {
        super("Room verification for this period already exists");
    }
}
