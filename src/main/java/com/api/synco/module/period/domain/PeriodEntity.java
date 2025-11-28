package com.api.synco.module.period.domain;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.period.domain.enumerator.TypePeriod;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter; // Adicionei para evitar escrever setters manualmente

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class PeriodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClassEntity classEntity;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TypePeriod typePeriod;

    public PeriodEntity() {
    }

    public PeriodEntity(Long id, UserEntity teacher, RoomEntity room, ClassEntity classEntity, LocalDate date, TypePeriod typePeriod) {
        this.id = id;
        this.teacher = teacher;
        this.room = room;
        this.classEntity = classEntity;
        this.date = date;
        this.typePeriod = typePeriod;
    }


}