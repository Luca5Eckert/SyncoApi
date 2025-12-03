package com.api.synco.module.user.domain;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain entity representing a user in the system.
 *
 * <p>This entity encapsulates all user-related data including personal information,
 * authentication credentials, and authorization role. It uses value objects for
 * validated fields like {@link Name} and {@link Email}.</p>
 *
 * <p>The entity is persisted with the following indexes for query optimization:</p>
 * <ul>
 *   <li>Unique index on email for fast lookups and uniqueness enforcement</li>
 *   <li>Index on role for filtering by user type</li>
 *   <li>Index on creation timestamp for date-based queries</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see RoleUser
 * @see Name
 * @see Email
 */
@Entity
@Table(name = "user_tb", indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true),
        @Index(name = "idx_user_role", columnList = "role"),
        @Index(name = "idx_user_create_at", columnList = "createAt")
})
public class UserEntity {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The user's display name, stored as a value object.
     */
    private Name name;

    /**
     * The user's email address, stored as a value object.
     */
    private Email email;

    /**
     * The user's encoded password.
     */
    private String password;

    /**
     * The user's authorization role.
     */
    @Enumerated(EnumType.STRING)
    private RoleUser role;

    /**
     * Timestamp of when the user was created.
     */
    @CreationTimestamp
    private Instant createAt;

    /**
     * Timestamp of the last update to the user record.
     */
    @UpdateTimestamp
    private Instant updateAt;

    /**
     * Default constructor required by JPA.
     */
    public UserEntity() {
    }

    /**
     * Constructs a new user with the specified attributes (without ID).
     *
     * @param name the user's name
     * @param email the user's email
     * @param password the user's encoded password
     * @param role the user's role
     */
    public UserEntity(Name name, Email email, String password, RoleUser role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructs a new user with the specified attributes (with ID).
     *
     * @param id the user's unique identifier
     * @param name the user's name
     * @param email the user's email
     * @param password the user's encoded password
     * @param role the user's role
     */
    public UserEntity(long id, Name name, Email email, String password, RoleUser role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the user's role.
     *
     * @return the user's role
     */
    public RoleUser getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     *
     * @param role the new role for the user
     */
    public void setRole(RoleUser role) {
        this.role = role;
    }

    /**
     * Returns the user's unique identifier.
     *
     * @return the user ID
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the user's name.
     *
     * @return the user's name value object
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the new name for the user
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Returns the user's email.
     *
     * @return the user's email value object
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email the new email for the user
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    /**
     * Returns the creation timestamp.
     *
     * @return the instant when the user was created
     */
    public Instant getCreateAt() {
        return createAt;
    }

    /**
     * Returns the last update timestamp.
     *
     * @return the instant when the user was last updated
     */
    public Instant getUpdateAt() {
        return updateAt;
    }

    /**
     * Returns the user's encoded password.
     *
     * @return the encoded password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new encoded password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(createAt, user.createAt) && Objects.equals(updateAt, user.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, role, createAt, updateAt);
    }

}
