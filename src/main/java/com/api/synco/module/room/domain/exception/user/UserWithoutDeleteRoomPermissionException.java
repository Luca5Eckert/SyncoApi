package com.api.synco.module.room.domain.exception.user;

import com.api.synco.module.room.domain.exception.RoomDomainException;

public class UserWithoutDeleteRoomPermissionException extends RoomDomainException {
    public UserWithoutDeleteRoomPermissionException(String message) {
        super(message);
    }

    public UserWithoutDeleteRoomPermissionException(){
        super("User don't have permission to delete a room");
    }

}
