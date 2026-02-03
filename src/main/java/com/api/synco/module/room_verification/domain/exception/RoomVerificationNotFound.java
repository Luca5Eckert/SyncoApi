package com.api.synco.module.room_verification.domain.exception;

public class RoomVerificationNotFound extends RuntimeException {
    public RoomVerificationNotFound(String message) {
        super(message);
    }

    public RoomVerificationNotFound() {
        super("Room verification not found");
    }

}
