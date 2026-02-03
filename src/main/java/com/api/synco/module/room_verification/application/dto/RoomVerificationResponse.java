package com.api.synco.module.room_verification.application.dto;

public record RoomVerificationResponse(
        long id,
        long periodId,
        boolean allOrganized,
        String description,
        String observations,
        String ticket
) {
}
