package lk.ijse.hostel_management_hibernate.service.custom.impl;

import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import lk.ijse.hostel_management_hibernate.dto.UserDTO;
import lk.ijse.hostel_management_hibernate.entity.User;
import lk.ijse.hostel_management_hibernate.repository.RepositoryFactory;
import lk.ijse.hostel_management_hibernate.repository.custom.UserRepository;
import lk.ijse.hostel_management_hibernate.service.custom.ChangeUserDetailService;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ChangeUserDetailServiceImpl implements ChangeUserDetailService {
    private UserRepository userRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.USER);
    @Override
    public boolean changeUserDetails(UserDTO userDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            userRepository.setSession(session);
            userRepository.save(userDTO.toEntity());
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            System.out.println("UserDetail update = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean deleteUserDetails(String currentUserName) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            User user = new User();
            user.setUsername(currentUserName);

            userRepository.setSession(session);
            userRepository.delete(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            System.out.println("UserDetail delete = "+e);
            return false;
        }finally {
            session.close();
        }
    }
}
