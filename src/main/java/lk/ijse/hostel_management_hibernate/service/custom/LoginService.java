package lk.ijse.hostel_management_hibernate.service.custom;

import lk.ijse.hostel_management_hibernate.dto.UserDTO;
import lk.ijse.hostel_management_hibernate.service.SuperService;

public interface LoginService extends SuperService {
    UserDTO searchByUserName(String userName);
}
