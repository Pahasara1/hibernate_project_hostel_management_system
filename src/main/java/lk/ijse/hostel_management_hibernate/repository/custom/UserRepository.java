package lk.ijse.hostel_management_hibernate.repository.custom;

import lk.ijse.hostel_management_hibernate.entity.User;
import lk.ijse.hostel_management_hibernate.repository.CrudRepository;
import org.hibernate.Session;

public interface UserRepository extends CrudRepository<User, String> {
    void setSession(Session session);
}
