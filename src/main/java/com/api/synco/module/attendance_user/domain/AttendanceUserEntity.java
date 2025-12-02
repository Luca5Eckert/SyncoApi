package com.api.synco.module.attendance_user.domain;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.user.domain.UserEntity;
import jakarta.persistence.*;

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


    public AttendanceUserEntity() {
    }

    public AttendanceUserEntity(long id, PeriodEntity period, UserEntity userEntity, boolean isPresent) {
        this.id = id;
        this.period = period;
        this.userEntity = userEntity;
        this.isPresent = isPresent;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

}
