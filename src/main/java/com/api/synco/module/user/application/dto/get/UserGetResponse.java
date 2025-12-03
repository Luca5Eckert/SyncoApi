package com.api.synco.module.user.application.dto.get;

import com.api.synco.module.user.domain.enumerator.RoleUser;

import java.time.Instant;

/**
 * Data Transfer Object for user retrieval responses.
 *
 * <p>This record contains comprehensive user information returned
 * when retrieving user data, including timestamps and role.</p>
 *
 * @param id the unique identifier of the user
 * @param name the user's display name
 * @param email the user's email address
 * @param role the user's assigned role
 * @param createAt the timestamp when the user was created
 * @param updateAt the timestamp when the user was last updated
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserGetResponse(long id, String name, String email, RoleUser role, Instant createAt, Instant updateAt) {
}
