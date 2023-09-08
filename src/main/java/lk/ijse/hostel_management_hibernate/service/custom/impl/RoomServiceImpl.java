package lk.ijse.hostel_management_hibernate.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.dto.RoomDTO;
import lk.ijse.hostel_management_hibernate.entity.Room;
import lk.ijse.hostel_management_hibernate.repository.RepositoryFactory;
import lk.ijse.hostel_management_hibernate.repository.custom.RoomRepository;
import lk.ijse.hostel_management_hibernate.service.custom.RoomService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.ROOM);
    @Override
    public boolean updateRoomType(RoomDTO roomDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            roomRepository.setSession(session);
            roomRepository.update(roomDTO.toEntity());
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            System.out.println("room  update = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean saveRoomType(RoomDTO roomDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            roomRepository.setSession(session);
            Room room = roomDTO.toEntity();
            room.setAvailableRooms(roomDTO.getRoomQty());
            roomRepository.save(room);
            transaction.commit();
            return true;
        }catch (ConstraintViolationException id) {
            AlertController.errormessage("Duplicate ID");
            return false;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            System.out.println("room save = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean deleteRoomType(RoomDTO roomDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            roomRepository.setSession(session);
            roomRepository.delete(roomDTO.toEntity());
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            System.out.println("room delete = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public ObservableList<RoomDTO> getDetailsToTableView() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            roomRepository.setSession(session);
            List<Room> roomList = roomRepository.getDetailsToTableView();
            ObservableList<RoomDTO> roomDTOS = FXCollections.observableArrayList();

            for (Room r : roomList) {
                roomDTOS.add(new RoomDTO(
                        r.getPerRoom(),
                        r.getRoomQuantity(),
                        r.getKeyMoney(),
                        r.getRoomTypeId(),
                        r.getRoomType()
                ));
            }
            transaction.commit();
            return roomDTOS;
        }catch (Exception e){
            transaction.rollback();
            System.out.println("getDetailsToTableView failed");
            System.out.println(e);
            return null;
        }finally {
            session.close();
        }
    }
}
