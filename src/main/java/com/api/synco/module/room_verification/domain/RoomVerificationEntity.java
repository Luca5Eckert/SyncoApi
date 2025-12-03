package com.api.synco.module.room_verification.domain;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.room_verification.domain.value_object.RoomVerificationForm;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Domain entity representing a room verification record.
 *
 * <p>This entity stores the result of a room verification check,
 * including the verification form data and the registration date.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see PeriodEntity
 * @see RoomVerificationForm
 */
@Entity
public class RoomVerificationEntity {

    /**
     * The unique identifier for this verification record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The period this verification was performed for.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private PeriodEntity period;

    /**
     * The date when the verification was registered.
     */
    private LocalDate dateRegister;

    /**
     * The verification form data.
     */
    @Embedded
    private RoomVerificationForm roomVerificationForm;

    /**
     * Constructs a new room verification entity.
     *
     * @param id the verification ID
     * @param period the associated period
     * @param dateRegister the registration date
     * @param roomVerificationForm the verification form data
     */
    public RoomVerificationEntity(long id, PeriodEntity period, LocalDate dateRegister, RoomVerificationForm roomVerificationForm) {
        this.id = id;
        this.period = period;
        this.dateRegister = dateRegister;
        this.roomVerificationForm = roomVerificationForm;
    }

    /**
     * Returns the verification ID.
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
     * Returns the registration date.
     *
     * @return the date of registration
     */
    public LocalDate getDateRegister() {
        return dateRegister;
    }

    /**
     * Sets the registration date.
     *
     * @param dateRegister the date of registration
     */
    public void setDateRegister(LocalDate dateRegister) {
        this.dateRegister = dateRegister;
    }

    /**
     * Returns the verification form data.
     *
     * @return the room verification form
     */
    public RoomVerificationForm getRoomVerificationForm() {
        return roomVerificationForm;
    }

    /**
     * Sets the verification form data.
     *
     * @param roomVerificationForm the room verification form
     */
    public void setRoomVerificationForm(RoomVerificationForm roomVerificationForm) {
        this.roomVerificationForm = roomVerificationForm;
    }
}
