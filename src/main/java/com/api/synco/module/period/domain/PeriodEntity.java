package com.api.synco.module.period.domain;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.user.domain.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PeriodEntity {

    @Id
    private long id;

    private UserEntity teacher;

    private RoomEntity room;



}
