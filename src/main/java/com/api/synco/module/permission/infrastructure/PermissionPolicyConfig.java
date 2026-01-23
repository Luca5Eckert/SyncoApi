package com.api.synco.module.permission.infrastructure;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.room.domain.permission.RoomPermissionPolicy;
import com.api.synco.module.user.domain.permission.UserPermissionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionPolicyConfig {

    @Bean
    public PermissionPolicy roomPermissionPolicy() {
        return new RoomPermissionPolicy();
    }

    @Bean
    public PermissionPolicy userPermissionPolicy() {
        return new UserPermissionPolicy();
    }

}
