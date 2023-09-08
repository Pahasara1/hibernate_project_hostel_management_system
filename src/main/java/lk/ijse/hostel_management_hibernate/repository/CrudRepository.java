package lk.ijse.hostel_management_hibernate.repository;

import java.util.List;

public interface CrudRepository<T,ID> extends SuperRepository{
    void save(T entity);

    void update(T entity);

    void delete(T entity);

    T getById(ID id);

    List<T> getDetailsToTableView();
}
