package lk.ijse.hostel_management_hibernate.repository.custom;

import lk.ijse.hostel_management_hibernate.projection.CustomProjection;
import lk.ijse.hostel_management_hibernate.repository.SuperRepository;
import org.hibernate.Session;

import java.util.List;

public interface QueryRepository extends SuperRepository {
    void setSession(Session session);

    List<CustomProjection> getDetailsOfStudentsWithoutKeyMoney();
}
