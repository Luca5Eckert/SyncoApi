package com.api.synco.module.room.domain.exception;

public class RoomNotExistException extends RoomDomainException {
    public RoomNotExistException(long roomId) {
        super("Room with id " + roomId + " does not exist.");
    }
}
