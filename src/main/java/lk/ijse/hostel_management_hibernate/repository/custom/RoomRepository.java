package lk.ijse.hostel_management_hibernate.repository.custom;

import lk.ijse.hostel_management_hibernate.entity.Room;
import lk.ijse.hostel_management_hibernate.projection.RoomProjection;
import lk.ijse.hostel_management_hibernate.repository.CrudRepository;
import org.hibernate.Session;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, String> {
    void setSession(Session session);

    List<String> loadRoomTypeIds();

    List<RoomProjection> getDetailsForRoomAvailabily();

}
