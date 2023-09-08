package lk.ijse.hostel_management_hibernate.repository.custom.impl;

import lk.ijse.hostel_management_hibernate.entity.Room;
import lk.ijse.hostel_management_hibernate.projection.RoomProjection;
import lk.ijse.hostel_management_hibernate.repository.custom.RoomRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public List<String> loadRoomTypeIds() {
        String sql ="SELECT room_type_id FROM room";
        Query query = session.createNativeQuery(sql);
        List<String> list = query.list();
        return list;
    }


    @Override
    public void save(Room entity) {
        session.save(entity);
    }

    @Override
    public void update(Room entity) {
        session.update(entity);
    }

    @Override
    public void delete(Room entity) {
        session.delete(entity);
    }

    @Override
    public Room getById(String s) {
        return null;
    }

    @Override
    public List<Room> getDetailsToTableView() {
        String sql = "SELECT C FROM Room AS C";
        Query query = session.createQuery(sql);
        List<Room> roomList = query.list();
        return roomList;
    }

    @Override
    public List<RoomProjection> getDetailsForRoomAvailabily() {
        String sql = "SELECT new lk.ijse.hostel_management_hibernate.projection.RoomProjection" +
                "(c.roomTypeId, c.roomType, c.availableRooms,c.keyMoney) FROM Room c";
        Query query = session.createQuery(sql);
        List<RoomProjection> list = query.list();
        return list;
    }
}
