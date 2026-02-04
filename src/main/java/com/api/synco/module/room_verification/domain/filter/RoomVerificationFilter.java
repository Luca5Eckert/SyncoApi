package com.api.synco.module.room_verification.domain.filter;

public record RoomVerificationFilter(
        Long periodId,
        long courseId,
        int numbeer,
        Boolean allOrganized,
        String description,
        String ticket
) {
}
