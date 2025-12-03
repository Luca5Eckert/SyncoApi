package com.api.synco.module.period.domain;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.period.domain.enumerator.TypePeriod;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Domain entity representing a class period or session.
 *
 * <p>This entity represents a scheduled period when a class takes place,
 * including the teacher, room, date, and type of period.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see ClassEntity
 * @see UserEntity
 * @see RoomEntity
 * @see TypePeriod
 */
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

    /**
     * Default constructor required by JPA.
     */
    public PeriodEntity() {
    }

    /**
     * Constructs a new period entity with the specified attributes.
     *
     * @param id the period identifier
     * @param teacher the assigned teacher
     * @param room the assigned room
     * @param classEntity the associated class
     * @param date the period date
     * @param typePeriod the type of period
     */
    public PeriodEntity(Long id, UserEntity teacher, RoomEntity room, ClassEntity classEntity, LocalDate date, TypePeriod typePeriod) {
        this.id = id;
        this.teacher = teacher;
        this.room = room;
        this.classEntity = classEntity;
        this.date = date;
        this.typePeriod = typePeriod;
    }


}