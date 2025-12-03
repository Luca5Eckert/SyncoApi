package com.api.synco.module.user.domain.mapper;

import com.api.synco.module.user.application.dto.create.UserCreateResponse;
import com.api.synco.module.user.application.dto.edit.UserEditResponse;
import com.api.synco.module.user.application.dto.get.UserGetResponse;
import com.api.synco.module.user.domain.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting user domain entities to DTOs.
 *
 * <p>This class provides mapping methods to transform {@link UserEntity}
 * instances into various response DTOs used by the API layer.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserEntity
 */
@Component
public class UserMapper {

    /**
     * Maps a user entity to a creation response DTO.
     *
     * @param user the user entity to map
     * @return a {@link UserCreateResponse} containing the user's data
     */
    public UserCreateResponse toCreateResponse(UserEntity user) {
        return new UserCreateResponse(user.getId(), user.getName().value(), user.getEmail().address(), user
                .getRole());
    }

    /**
     * Maps a user entity to an edit response DTO.
     *
     * @param user the user entity to map
     * @return a {@link UserEditResponse} containing the updated user data
     */
    public UserEditResponse toEditResponse(UserEntity user) {
        return new UserEditResponse(user.getId(), user.getName().value(), user.getEmail().address());
    }

    /**
     * Maps a user entity to a get response DTO.
     *
     * @param user the user entity to map
     * @return a {@link UserGetResponse} containing comprehensive user data
     */
    public UserGetResponse toGetResponse(UserEntity user) {
        return new UserGetResponse(
                user.getId(),
                user.getName().value(),
                user.getEmail().address(),
                user.getRole(),
                user.getCreateAt(),
                user.getUpdateAt()
        );
    }


}
