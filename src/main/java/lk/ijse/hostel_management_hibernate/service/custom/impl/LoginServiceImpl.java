package lk.ijse.hostel_management_hibernate.service.custom.impl;

import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import lk.ijse.hostel_management_hibernate.dto.UserDTO;
import lk.ijse.hostel_management_hibernate.entity.User;
import lk.ijse.hostel_management_hibernate.repository.RepositoryFactory;
import lk.ijse.hostel_management_hibernate.repository.custom.UserRepository;
import lk.ijse.hostel_management_hibernate.service.custom.LoginService;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoginServiceImpl implements LoginService {
    private UserRepository userRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.USER);
    @Override
    public UserDTO searchByUserName(String userName) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            userRepository.setSession(session);
            User user = userRepository.getById(userName);
            UserDTO userDTO = new UserDTO(
                    user.getUsername(),
                    user.getPassword()
            );
            transaction.commit();
            return userDTO;
        }catch (Exception e){
            transaction.rollback();
//            e.printStackTrace();
            System.out.println("userName = "+e);
            return null;
        }finally {
            session.close();
        }
    }
}
