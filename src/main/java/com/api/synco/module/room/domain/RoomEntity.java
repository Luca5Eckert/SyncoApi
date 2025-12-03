package com.api.synco.module.room.domain;

import com.api.synco.module.room.domain.enumerator.TypeRoom;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Domain entity representing a room in the system.
 *
 * <p>This entity stores room information including the room number
 * and type (e.g., computer lab, classroom).</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see TypeRoom
 */
@Entity
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int number;

    private TypeRoom typeRoom;

    /**
     * Default constructor required by JPA.
     */
    public RoomEntity() {
    }

    /**
     * Constructs a new room entity.
     *
     * @param id the room ID
     * @param number the room number
     * @param typeRoom the type of room
     */
    public RoomEntity(long id, int number, TypeRoom typeRoom) {
        this.id = id;
        this.number = number;
        this.typeRoom = typeRoom;
    }

    /**
     * Returns the room number.
     *
     * @return the room number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the room number.
     *
     * @param number the room number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Returns the room type.
     *
     * @return the room type
     */
    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    /**
     * Sets the room type.
     *
     * @param typeRoom the room type
     */
    public void setTypeRoom(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }

}
