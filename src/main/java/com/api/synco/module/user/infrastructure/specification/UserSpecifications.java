package com.api.synco.module.user.infrastructure.specification;

import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

/**
 * Utility class containing JPA Specifications for querying {@link UserEntity}.
 *
 * <p>This class provides reusable, composable specifications that can be
 * combined using Spring Data JPA's specification API to build dynamic
 * queries for user filtering and searching.</p>
 *
 * <p>Each specification method returns a {@link Specification} that can be
 * combined with others using {@code and()}, {@code or()}, and {@code not()}
 * methods.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Specification<UserEntity> spec = UserSpecifications.nameContains("John")
 *     .and(UserSpecifications.roleEquals(RoleUser.ADMIN));
 * }</pre>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see Specification
 * @see UserEntity
 */
public class UserSpecifications {

    /**
     * Creates a specification to filter users by name containing the given value.
     *
     * <p>The search is case-insensitive and matches any part of the name.</p>
     *
     * @param name the name substring to search for (can be null or blank to skip filter)
     * @return a specification for name filtering, or null if name is blank/null
     */
    public static Specification<UserEntity> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("name").as(String.class)), "%" + name.toLowerCase() + "%");
        };
    }

    /**
     * Creates a specification to filter users by email containing the given value.
     *
     * <p>The search is case-insensitive and matches any part of the email address.</p>
     *
     * @param email the email substring to search for (can be null or blank to skip filter)
     * @return a specification for email filtering, or null if email is blank/null
     */
    public static Specification<UserEntity> emailContains(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) return null;
            return cb.like(
                    cb.lower(root.get("email").get("value").as(String.class)),
                    "%" + email.toLowerCase() + "%"
            );
        };
    }

    /**
     * Creates a specification to filter users by exact role match.
     *
     * @param role the role to match (can be null to skip filter)
     * @return a specification for role filtering, or null if role is null
     */
    public static Specification<UserEntity> roleEquals(RoleUser role) {
        return (root, query, cb) -> {
            if (role == null) return null;
            return cb.equal(root.get("role"), role);
        };
    }

    /**
     * Creates a specification to filter users by creation date range.
     *
     * <p>Both bounds are inclusive. If either bound is null, a default value is used:</p>
     * <ul>
     *   <li>If {@code from} is null, {@link Instant#EPOCH} is used</li>
     *   <li>If {@code to} is null, {@link Instant#now()} is used</li>
     * </ul>
     *
     * @param from the start of the date range (inclusive, can be null)
     * @param to the end of the date range (inclusive, can be null)
     * @return a specification for date range filtering, or null if both bounds are null
     */
    public static Specification<UserEntity> createdBetween(Instant from, Instant to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;

            var path = root.<Instant>get("createAt");

            Instant start = (from != null) ? from : Instant.EPOCH;
            Instant end = (to != null) ? to : Instant.now();

            return cb.between(path, start, end);
        };
    }
}