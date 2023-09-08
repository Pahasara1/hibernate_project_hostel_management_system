package lk.ijse.hostel_management_hibernate.service.custom;

import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.dto.CustomEntityDTO;
import lk.ijse.hostel_management_hibernate.projection.CustomProjection;
import lk.ijse.hostel_management_hibernate.service.SuperService;

public interface PendingPaymentsService extends SuperService {
    ObservableList<CustomProjection> getDetailsToTableView();

    boolean updatePendingPayment(CustomEntityDTO customEntityDTO);
}
