package lk.ijse.hostel_management_hibernate.repository.custom.impl;

import lk.ijse.hostel_management_hibernate.entity.Student;
import lk.ijse.hostel_management_hibernate.repository.custom.StudentRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public List<String> loadStudentIds() {
        String sql = "SELECT st_id from Student";
        Query query = session.createNativeQuery(sql);
        List<String> list = query.list();
        return list;
    }

    @Override
    public void save(Student entity) {
        session.save(entity);
    }

    @Override
    public void update(Student entity) {
        session.update(entity);
    }

    @Override
    public void delete(Student entity) {
        session.delete(entity);
    }

    @Override
    public Student getById(String s) {
        return null;
    }

    @Override
    public List<Student> getDetailsToTableView() {
        String sql = "SELECT C FROM Student AS C";
        Query query = session.createQuery(sql);
        List<Student> studentList = query.list();
        return studentList;
    }
}
