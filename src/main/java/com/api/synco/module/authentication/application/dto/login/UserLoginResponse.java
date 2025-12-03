package com.api.synco.module.authentication.application.dto.login;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Data Transfer Object for user login responses.
 *
 * <p>This record contains the authentication result including the JWT token
 * for subsequent API requests.</p>
 *
 * @param id the unique identifier of the authenticated user
 * @param email the user's email address
 * @param roles the collection of granted authorities for the user
 * @param token the JWT token for authentication
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserLoginResponse(long id, String email, Collection<? extends GrantedAuthority> roles, String token){
}
