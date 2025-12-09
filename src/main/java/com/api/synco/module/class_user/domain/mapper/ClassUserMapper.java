package com.api.synco.module.class_user.domain.mapper;

import com.api.synco.module.class_entity.application.dto.ClassIdResponse;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.application.dto.ClassUserIdResponse;
import com.api.synco.module.class_user.application.dto.create.CreateClassUserResponse;
import com.api.synco.module.class_user.application.dto.get.GetAllClassUserResponse;
import com.api.synco.module.class_user.application.dto.get.GetClassUserResponse;
import com.api.synco.module.class_user.application.dto.update.UpdateClassUserRequest;
import com.api.synco.module.class_user.application.dto.update.UpdateClassUserResponse;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import org.springframework.stereotype.Component;

@Component
public class ClassUserMapper {

    public GetClassUserResponse toGetResponse(ClassUser classUser) {
        return new GetClassUserResponse(
                toIdResponse(classUser.getClassUserId()),
                classUser.getTypeUserClass()
        );
    }

    public GetAllClassUserResponse toGetAllResponse(ClassUser classUser){
        return new GetAllClassUserResponse(
                toIdResponse(classUser.getClassUserId()),
                classUser.getTypeUserClass()
        );
    }


    public ClassUserIdResponse toIdResponse(ClassUserId classUserId){
        return new ClassUserIdResponse(
            toIdClassResponse(classUserId.getClassEntityId()),
            classUserId.getUserId()
        );
    }

    private ClassIdResponse toIdClassResponse(ClassEntityId classEntityId) {
        return new ClassIdResponse(
                classEntityId.getCourseId(),
                classEntityId.getNumber()
        );
    }

    public CreateClassUserResponse toCreateResponse(ClassUser classUser) {
        return new CreateClassUserResponse(
                toIdResponse(classUser.getClassUserId()),
                classUser.getTypeUserClass()
        );
    }

    public UpdateClassUserResponse toUpdateResponse(ClassUser classUser) {
        return new UpdateClassUserResponse(
                toIdResponse(classUser.getClassUserId()),
                classUser.getTypeUserClass()
        );
    }
}
