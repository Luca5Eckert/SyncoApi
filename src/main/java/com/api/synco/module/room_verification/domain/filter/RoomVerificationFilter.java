package com.api.synco.module.room_verification.domain.filter;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import lombok.Builder;

@Builder
public record RoomVerificationFilter(
        Long periodId,
        ClassEntityId classEntityId,
        Boolean allOrganized,
        String description,
        String ticket
) {
}
