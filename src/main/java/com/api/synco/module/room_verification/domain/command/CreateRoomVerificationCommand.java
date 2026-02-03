package com.api.synco.module.room_verification.domain.command;

public record CreateRoomVerificationCommand(
        long periodId,
        long userAuthenticatedId,
        boolean allOrganized,
        String description,
        String observations,
        String ticket
) {
}
