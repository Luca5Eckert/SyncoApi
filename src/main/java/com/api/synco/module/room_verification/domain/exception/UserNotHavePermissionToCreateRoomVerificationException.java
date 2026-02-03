package com.api.synco.module.room_verification.domain.exception;

public class UserNotHavePermissionToCreateRoomVerificationException extends RuntimeException {
    public UserNotHavePermissionToCreateRoomVerificationException(String message) {
        super(message);
    }

    public UserNotHavePermissionToCreateRoomVerificationException() {
        super("User does not have permission to create room verification for this period");
    }
}
