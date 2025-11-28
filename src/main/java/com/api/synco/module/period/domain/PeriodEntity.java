package com.api.synco.module.period.domain;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.period.domain.enumerator.TypePeriod;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.user.domain.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class PeriodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UserEntity teacher;

    private RoomEntity room;

    private ClassEntity classEntity;

    private LocalDate date;

    private TypePeriod typePeriod;

    public PeriodEntity() {
    }

    public PeriodEntity(long id, UserEntity teacher, RoomEntity room, ClassEntity classEntity, LocalDate date, TypePeriod typePeriod) {
        this.id = id;
        this.teacher = teacher;
        this.room = room;
        this.classEntity = classEntity;
        this.date = date;
        this.typePeriod = typePeriod;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTypePeriod(TypePeriod typePeriod) {
        this.typePeriod = typePeriod;
    }

}
