package com.api.synco.module.room_verification.application.dto.update;

public record UpdateRoomVerificationRequest(
        Boolean allOrganized,
        String description,
        String observations,
        String ticket
) {
}
