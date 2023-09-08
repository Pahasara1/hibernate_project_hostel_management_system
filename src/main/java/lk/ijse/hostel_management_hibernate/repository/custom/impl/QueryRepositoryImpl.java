package lk.ijse.hostel_management_hibernate.repository.custom.impl;

import lk.ijse.hostel_management_hibernate.projection.CustomProjection;
import lk.ijse.hostel_management_hibernate.repository.custom.QueryRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class QueryRepositoryImpl implements QueryRepository {
    private Session session;
    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public List<CustomProjection> getDetailsOfStudentsWithoutKeyMoney() {
        String sql = "SELECT new lk.ijse.hostel_management_hibernate.projection.CustomProjection" +
                "(r.reservationId, s.studentId, s.studentName, s.studentAddress, s.studentContact, r.reservationDate, rm.keyMoney) FROM Reservation r\n" +
                "    INNER JOIN Student s on r.reservationPK.studentId = s.studentId                                                           \n" +
                "    INNER JOIN Room rm on r.reservationPK.roomTypeId = rm.roomTypeId WHERE r.reservationStatus  = : status ";
        Query query = session.createQuery(sql);
        query.setParameter("status", "NOT PAID");
        List<CustomProjection> list = query.list();
        return list;
    }
}
