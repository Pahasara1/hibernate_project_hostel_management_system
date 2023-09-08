package lk.ijse.hostel_management_hibernate.service.custom;

import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.dto.RoomDTO;
import lk.ijse.hostel_management_hibernate.service.SuperService;

public interface RoomService extends SuperService {

    boolean updateRoomType(RoomDTO roomDTO);

    boolean saveRoomType(RoomDTO roomDTO);

    boolean deleteRoomType(RoomDTO roomDTO);

    ObservableList<RoomDTO> getDetailsToTableView();
}
