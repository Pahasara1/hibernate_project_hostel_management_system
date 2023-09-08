package lk.ijse.hostel_management_hibernate.service.custom;

import lk.ijse.hostel_management_hibernate.dto.UserDTO;
import lk.ijse.hostel_management_hibernate.service.SuperService;

public interface SignUpService extends SuperService {
    boolean saveUser(UserDTO userDto);
}
