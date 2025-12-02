package com.api.synco.module.room_verification.domain;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.room_verification.domain.value_object.RoomVerificationForm;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RoomVerificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PeriodEntity period;

    private LocalDate dateRegister;

    @Embedded
    private RoomVerificationForm roomVerificationForm;

    public RoomVerificationEntity(long id, PeriodEntity period, LocalDate dateRegister, RoomVerificationForm roomVerificationForm) {
        this.id = id;
        this.period = period;
        this.dateRegister = dateRegister;
        this.roomVerificationForm = roomVerificationForm;
    }

    public long getId() {
        return id;
    }

    public PeriodEntity getPeriod() {
        return period;
    }

    public void setPeriod(PeriodEntity period) {
        this.period = period;
    }

    public LocalDate getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDate dateRegister) {
        this.dateRegister = dateRegister;
    }

    public RoomVerificationForm getRoomVerificationForm() {
        return roomVerificationForm;
    }

    public void setRoomVerificationForm(RoomVerificationForm roomVerificationForm) {
        this.roomVerificationForm = roomVerificationForm;
    }
}
