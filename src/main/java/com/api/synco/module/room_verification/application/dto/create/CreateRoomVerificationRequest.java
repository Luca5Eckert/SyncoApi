package com.api.synco.module.room_verification.application.dto.create;

public record CreateRoomVerificationRequest(
        boolean allOrganized,
        String description,
        String observations,
        String ticket
) {
}
