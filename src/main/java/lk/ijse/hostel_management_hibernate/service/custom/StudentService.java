package lk.ijse.hostel_management_hibernate.service.custom;

import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.dto.StudentDTO;
import lk.ijse.hostel_management_hibernate.service.SuperService;

public interface StudentService extends SuperService {
    boolean saveStudent(StudentDTO student);

    boolean updateStudent(StudentDTO studentDTO);

    boolean deleteStudent(StudentDTO studentDTO);

    ObservableList<StudentDTO> getDetailsToTableView();
}
