package com.api.synco.module.room_verification.domain.permission;

import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.stereotype.Component;

@Component
public class RoomVerificationPermissionPolicy {

    public boolean canCreate(RoleUser roleUser, TypeUserClass typeUserClass){
        if(roleUser == RoleUser.ADMIN) return true;

        return switch(typeUserClass){
            case REPRESENTATIVE, TEACHER, ADMINISTRATOR, SECRETARY -> true;
            case STUDENT -> false;
        };
    }

    public boolean canUpdate(RoleUser roleUser, TypeUserClass typeUserClass) {
        if(roleUser == RoleUser.ADMIN) return true;

        return switch(typeUserClass){
            case REPRESENTATIVE, TEACHER, ADMINISTRATOR, SECRETARY -> true;
            case STUDENT -> false;
        };
    }

}
