package com.api.synco.module.room_verification.domain.command;

public record GetAllRoomVerificationCommand(
        long userAuthenticatedId,
        Long periodId,
        long courseId,
        int number,
        Boolean allOrganized,
        String description,
        String ticket,
        int pageNumber,
        int pageSize
) {
}
