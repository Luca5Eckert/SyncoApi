package com.api.synco.module.room_verification.domain.exception.user;

import com.api.synco.module.room_verification.domain.exception.RoomVerificationDomainException;

public class UserNotHavePermissionToUpdateRoomVerificationException extends RoomVerificationDomainException {
    public UserNotHavePermissionToUpdateRoomVerificationException() {
        super("User does not have permission to update room verification for this period");
    }
}
