package lk.ijse.hostel_management_hibernate.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "room_type_id")
    private String roomTypeId;

    @Column(name = "type", nullable = false, unique = true)
    private String roomType;

    @Column(name = "per_room", nullable = false)
    private int perRoom;

    @Column(name = "key_money", nullable = false)
    private String keyMoney;

    @Column(name = "room_qty", nullable = false)
    private int roomQuantity;

    @Column(name = "available_room_qty")
    private int availableRooms;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    private List<Reservation> reservationList = new ArrayList<>();

    public Room() {
    }

    public Room(String roomTypeId, String roomType, int perRoom, String keyMoney, int roomQuantity, int availableRooms, List<Reservation> reservationList) {
        this.roomTypeId = roomTypeId;
        this.roomType = roomType;
        this.perRoom = perRoom;
        this.keyMoney = keyMoney;
        this.roomQuantity = roomQuantity;
        this.availableRooms = availableRooms;
        this.reservationList = reservationList;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPerRoom() {
        return perRoom;
    }

    public void setPerRoom(int perRoom) {
        this.perRoom = perRoom;
    }

    public String getKeyMoney() {
        return keyMoney;
    }

    public void setKeyMoney(String keyMoney) {
        this.keyMoney = keyMoney;
    }

    public int getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomTypeId='" + roomTypeId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", perRoom=" + perRoom +
                ", keyMoney='" + keyMoney + '\'' +
                ", roomQuantity=" + roomQuantity +
                ", availableRooms=" + availableRooms +
                ", reservationList=" + reservationList +
                '}';
    }
}
