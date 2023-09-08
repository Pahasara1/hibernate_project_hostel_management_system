package lk.ijse.hostel_management_hibernate.repository.custom.impl;

import lk.ijse.hostel_management_hibernate.entity.User;
import lk.ijse.hostel_management_hibernate.repository.custom.UserRepository;
import org.hibernate.Session;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private Session session;
    @Override
    public void save(User entity) {
        session.save(entity);
    }

    @Override
    public void update(User entity) {
        session.update(entity);
    }

    @Override
    public void delete(User entity) {
        session.delete(entity);
    }

    @Override
    public User getById(String id) {
        User user=session.get(User.class,id);
        return user;
    }

    @Override
    public List<User> getDetailsToTableView() {
        return null;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }
}
