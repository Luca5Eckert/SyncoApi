package com.api.synco.module.room_verification.domain.command;

public record GetRoomVerificationCommand(
        long roomVerificationId,
        long userAuthenticatedId
) {
}
