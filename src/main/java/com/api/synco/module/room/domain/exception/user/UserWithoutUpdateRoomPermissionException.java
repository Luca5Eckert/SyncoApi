package com.api.synco.module.room.domain.exception.user;

import com.api.synco.module.room.domain.exception.RoomDomainException;

public class UserWithoutUpdateRoomPermissionException extends RoomDomainException {
    public UserWithoutUpdateRoomPermissionException(String message) {
        super(message);
    }

    public UserWithoutUpdateRoomPermissionException(){
        super("User don't have permission to update the room");
    }

}
