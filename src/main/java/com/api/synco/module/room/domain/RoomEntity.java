package com.api.synco.module.room.domain;

import com.api.synco.module.room.domain.enumerator.TypeRoom;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int number;

    private TypeRoom typeRoom;

    public RoomEntity() {
    }

    public RoomEntity(long id, int number, TypeRoom typeRoom) {
        this.id = id;
        this.number = number;
        this.typeRoom = typeRoom;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }

}
