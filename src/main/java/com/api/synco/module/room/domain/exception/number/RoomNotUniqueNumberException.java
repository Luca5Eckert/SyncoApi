package com.api.synco.module.room.domain.exception.number;

import com.api.synco.module.room.domain.exception.RoomDomainException;

public class RoomNotUniqueNumberException extends RoomDomainException {
    public RoomNotUniqueNumberException(String message) {
        super(message);
    }

    public RoomNotUniqueNumberException(int number) {
        super("Room's number not unique: " + number);
    }

}
