package com.api.synco.module.room.domain.exception.user;

import com.api.synco.module.room.domain.exception.RoomDomainException;

public class UserWithoutCreateRoomPermissionException extends RoomDomainException {
    public UserWithoutCreateRoomPermissionException(String message) {
        super(message);
    }

    public UserWithoutCreateRoomPermissionException(){
        super("User don't have permission to create a room");
    }
}
