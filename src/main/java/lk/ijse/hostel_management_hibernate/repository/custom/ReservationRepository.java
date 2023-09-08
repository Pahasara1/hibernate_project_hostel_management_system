package lk.ijse.hostel_management_hibernate.repository.custom;

import lk.ijse.hostel_management_hibernate.dto.CustomEntityDTO;
import lk.ijse.hostel_management_hibernate.entity.Reservation;
import lk.ijse.hostel_management_hibernate.repository.CrudRepository;
import org.hibernate.Session;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, String> {
    void setSession(Session session);

    int getReservationCount(String roomTypeId);

    List getMaxPersonsPerRoom(String roomTypeId);

    void updateAvailableRooms(int available_rooms, String roomTypeId);

    void updatePendingPayment(CustomEntityDTO customEntityDTO);
}

