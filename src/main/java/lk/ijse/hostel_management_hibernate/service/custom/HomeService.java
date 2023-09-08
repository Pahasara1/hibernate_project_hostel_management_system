package lk.ijse.hostel_management_hibernate.service.custom;

import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.projection.RoomProjection;
import lk.ijse.hostel_management_hibernate.service.SuperService;

public interface HomeService extends SuperService {
    ObservableList<RoomProjection>  getDetailsToRoomAvaTableView();
}
