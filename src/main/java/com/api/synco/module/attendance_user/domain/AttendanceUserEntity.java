package com.api.synco.module.attendance_user.domain;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.user.domain.UserEntity;
import jakarta.persistence.*;

/**
 * Domain entity representing user attendance for a specific period.
 *
 * <p>This entity tracks whether a user was present or absent during
 * a specific class period.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see PeriodEntity
 * @see UserEntity
 */
@Entity
public class AttendanceUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PeriodEntity period;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    private boolean isPresent;

    /**
     * Default constructor required by JPA.
     */
    public AttendanceUserEntity() {
    }

    /**
     * Constructs a new attendance record.
     *
     * @param id the attendance record ID
     * @param period the associated period
     * @param userEntity the associated user
     * @param isPresent whether the user was present
     */
    public AttendanceUserEntity(long id, PeriodEntity period, UserEntity userEntity, boolean isPresent) {
        this.id = id;
        this.period = period;
        this.userEntity = userEntity;
        this.isPresent = isPresent;
    }

    /**
     * Returns the attendance record ID.
     *
     * @return the ID
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the associated period.
     *
     * @return the period entity
     */
    public PeriodEntity getPeriod() {
        return period;
    }

    /**
     * Sets the associated period.
     *
     * @param period the period entity
     */
    public void setPeriod(PeriodEntity period) {
        this.period = period;
    }

    /**
     * Returns the associated user.
     *
     * @return the user entity
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * Sets the associated user.
     *
     * @param userEntity the user entity
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * Checks if the user was present.
     *
     * @return {@code true} if present, {@code false} otherwise
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * Sets the presence status.
     *
     * @param present the presence status
     */
    public void setPresent(boolean present) {
        isPresent = present;
    }

}
