package com.api.synco.module.room_verification.domain.exception.user;

import com.api.synco.module.room_verification.domain.exception.RoomVerificationDomainException;

public class UserNotHavePermissionToGetRoomVerificationException extends RoomVerificationDomainException {

    public UserNotHavePermissionToGetRoomVerificationException() {
        super("User does not have permission to get room verification for this period");
    }

}
