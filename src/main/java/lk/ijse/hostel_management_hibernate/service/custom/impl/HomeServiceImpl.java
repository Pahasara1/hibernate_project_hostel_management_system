package lk.ijse.hostel_management_hibernate.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import lk.ijse.hostel_management_hibernate.projection.RoomProjection;
import lk.ijse.hostel_management_hibernate.repository.RepositoryFactory;
import lk.ijse.hostel_management_hibernate.repository.custom.RoomRepository;
import lk.ijse.hostel_management_hibernate.service.custom.HomeService;
import lk.ijse.hostel_management_hibernate.service.custom.RoomService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HomeServiceImpl implements HomeService {
    private RoomRepository roomRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.ROOM);

    @Override
    public ObservableList<RoomProjection> getDetailsToRoomAvaTableView() {
        Session session = SessionFactoryConfig.getInstance().getSession();

        try {
            roomRepository.setSession(session);
            List<RoomProjection> roomProjectionList = roomRepository.getDetailsForRoomAvailabily();
            ObservableList<RoomProjection> obList = FXCollections.observableArrayList(roomProjectionList);

            session.close();
            return obList;
        }catch (Exception e){
            System.out.println("getDetailsToRoomAvaTableView = "+e);
            session.close();
            return null;
        }
    }
}
