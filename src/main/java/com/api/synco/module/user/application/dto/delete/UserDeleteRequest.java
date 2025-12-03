package com.api.synco.module.user.application.dto.delete;

/**
 * Data Transfer Object for user deletion requests.
 *
 * <p>This record contains the identifier of the user to be deleted
 * from the system.</p>
 *
 * @param id the unique identifier of the user to delete
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserDeleteRequest(long id) {
}
