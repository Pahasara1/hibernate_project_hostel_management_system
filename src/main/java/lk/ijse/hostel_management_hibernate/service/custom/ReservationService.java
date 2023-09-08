package lk.ijse.hostel_management_hibernate.service.custom;

import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.dto.ReservationDTO;
import lk.ijse.hostel_management_hibernate.service.SuperService;

import java.util.List;

public interface ReservationService extends SuperService {
    List<String> loadStudentIds();

    List<String> loadRoomTypeIds();

    boolean saveReservation(ReservationDTO reservationDTO);

    boolean updateReservation(ReservationDTO reservationDTO);

    boolean deleteReservation(ReservationDTO reservationDTO);

    ObservableList<ReservationDTO> getDetailsToTableView();

    ReservationDTO searchByResId(String id);
}
