package lk.ijse.hostel_management_hibernate.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.dto.StudentDTO;
import lk.ijse.hostel_management_hibernate.entity.Student;
import lk.ijse.hostel_management_hibernate.repository.RepositoryFactory;
import lk.ijse.hostel_management_hibernate.repository.custom.StudentRepository;
import lk.ijse.hostel_management_hibernate.service.custom.StudentService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.STUDENT);
    @Override
    public boolean saveStudent(StudentDTO student) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            studentRepository.setSession(session);
            studentRepository.save(student.toEntity());

            transaction.commit();
            return true;
        }catch (ConstraintViolationException id) {
            AlertController.errormessage("Duplicate ID");
            return false;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            System.out.println("student save = "+e);

            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            studentRepository.setSession(session);
            studentRepository.update(studentDTO.toEntity());
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            System.out.println("student update = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean deleteStudent(StudentDTO studentDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            studentRepository.setSession(session);
            studentRepository.delete(studentDTO.toEntity());
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            System.out.println("student getAll = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public ObservableList<StudentDTO> getDetailsToTableView() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            studentRepository.setSession(session);
            List<Student> students = studentRepository.getDetailsToTableView();
            ObservableList<StudentDTO> studentDTOS = FXCollections.observableArrayList();

            for (Student st : students) {
                studentDTOS.add(new StudentDTO(
                        st.getStudentId(),
                        st.getStudentAddress(),
                        st.getStudentDOB(),
                        st.getStudentGender(),
                        st.getStudentName(),
                        st.getStudentContact()
                ));
            }

            transaction.commit();
            return studentDTOS;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            System.out.println("student getAll = "+e);

            return null;
        }finally {
            session.close();
        }
    }
}
