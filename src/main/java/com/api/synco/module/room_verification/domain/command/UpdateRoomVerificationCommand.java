package com.api.synco.module.room_verification.domain.command;

public record UpdateRoomVerificationCommand(
        long roomVerificationId,
        long userAuthenticatedId,
        Boolean allOrganized,
        String description,
        String observations,
        String ticket
) {
}
