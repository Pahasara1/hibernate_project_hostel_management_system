package lk.ijse.hostel_management_hibernate.repository.custom;

import lk.ijse.hostel_management_hibernate.entity.Student;
import lk.ijse.hostel_management_hibernate.repository.CrudRepository;
import org.hibernate.Session;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, String> {
    void setSession(Session session);

    List<String> loadStudentIds();
}
