package lk.ijse.hostel_management_hibernate.service.custom.impl;

import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.dto.UserDTO;
import lk.ijse.hostel_management_hibernate.repository.RepositoryFactory;
import lk.ijse.hostel_management_hibernate.repository.custom.UserRepository;
import lk.ijse.hostel_management_hibernate.service.custom.SignUpService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

public class SignUpServiceImpl implements SignUpService {
    private UserRepository userRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.USER);
    @Override
    public boolean saveUser(UserDTO userDto) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            userRepository.setSession(session);
            userRepository.save(userDto.toEntity());
            transaction.commit();
            return true;
        }catch (ConstraintViolationException exception){
            AlertController.errormessage("This Username is already exits");
            return false;
        }catch (Exception e){
            transaction.rollback();
            System.out.println("user Save = "+e);
            return false;
        }finally {
            session.close();
        }
    }
}
