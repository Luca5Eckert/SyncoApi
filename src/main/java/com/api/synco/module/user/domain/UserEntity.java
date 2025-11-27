package com.api.synco.module.user.domain;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "user_tb", indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true),
        @Index(name = "idx_user_role", columnList = "role"),
        @Index(name = "idx_user_create_at", columnList = "createAt")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Name name;

    private Email email;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @CreationTimestamp
    private Instant createAt;

    @UpdateTimestamp
    private Instant updateAt;

    public UserEntity() {
    }

    public UserEntity(Name name, Email email, String password, RoleUser role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserEntity(long id, Name name, Email email, String password, RoleUser role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public RoleUser getRole() {
        return role;
    }

    public void setRole(RoleUser role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public String getPassword() {
        return password;
    }

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
