package lk.ijse.hostel_management_hibernate.repository.custom.impl;

import lk.ijse.hostel_management_hibernate.dto.CustomEntityDTO;
import lk.ijse.hostel_management_hibernate.entity.Reservation;
import lk.ijse.hostel_management_hibernate.repository.custom.ReservationRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository {
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void save(Reservation entity) {
        session.save(entity);
    }

    @Override
    public void update(Reservation entity) {
        session.update(entity);
    }

    @Override
    public void delete(Reservation entity) {
        session.delete(entity);
    }

    @Override
    public Reservation getById(String id) {
        String sql = "SELECT C FROM Reservation AS C WHERE C.reservationId = :res_id";
        Query query = session.createQuery(sql);
        query.setParameter("res_id", id);
        Reservation reservation = (Reservation) query.getSingleResult();
        return reservation;
    }

    @Override
    public List<Reservation> getDetailsToTableView() {
        String sql = "SELECT C FROM Reservation AS C ORDER BY C.reservationId";
        Query query = session.createQuery(sql);
        List<Reservation> list = query.list();
        return list;
    }

    @Override
    public int getReservationCount(String roomTypeId) {
       String sql = "SELECT COUNT(c) FROM Reservation c WHERE c.reservationPK.roomTypeId = :room_type_id";
       Query query = session.createQuery(sql);
       query.setParameter("room_type_id", roomTypeId);

       long count = (long) query.getSingleResult();
        System.out.println("getReservationCount = "+count);
       return (int) count;
    }

    @Override
    public List getMaxPersonsPerRoom(String roomTypeId) {
        String sql = "SELECT c.perRoom, c.roomQuantity FROM Room c WHERE c.roomTypeId = :room_type_id";
        Query query = session.createQuery(sql);
        query.setParameter("room_type_id", roomTypeId);
        List list = query.list();
        return list;
    }

    @Override
    public void updateAvailableRooms(int available_rooms, String roomTypeId) {
        String sql = "UPDATE Room r SET r.availableRooms = :available_room_qty WHERE r.roomTypeId = :room_type_id";
        Query query = session.createQuery(sql);
        query.setParameter("available_room_qty", available_rooms);
        query.setParameter("room_type_id", roomTypeId);
        query.executeUpdate();
    }

    @Override
    public void updatePendingPayment(CustomEntityDTO entityDTO) {
        String sql = "UPDATE Reservation r SET r.reservationStatus = :status WHERE r.reservationId = :res_id ";
        Query query = session.createQuery(sql);
        query.setParameter("status", entityDTO.getStatus());
        query.setParameter("res_id", entityDTO.getReservationId());
        query.executeUpdate();
    }
}
