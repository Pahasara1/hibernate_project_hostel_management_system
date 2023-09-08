package lk.ijse.hostel_management_hibernate.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.dto.ReservationDTO;
import lk.ijse.hostel_management_hibernate.entity.Reservation;
import lk.ijse.hostel_management_hibernate.repository.RepositoryFactory;
import lk.ijse.hostel_management_hibernate.repository.custom.ReservationRepository;
import lk.ijse.hostel_management_hibernate.repository.custom.RoomRepository;
import lk.ijse.hostel_management_hibernate.repository.custom.StudentRepository;
import lk.ijse.hostel_management_hibernate.service.custom.ReservationService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.RESERVATION);
    private RoomRepository roomRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.ROOM);
    private StudentRepository studentRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.STUDENT);

    @Override
    public List<String> loadStudentIds() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            studentRepository.setSession(session);
            List<String> stIds = studentRepository.loadStudentIds();
            transaction.commit();
            return stIds;
        }catch (Exception e){
            transaction.rollback();
            //e.printStackTrace();
            System.out.println("loadStuIds = "+e);
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public List<String> loadRoomTypeIds() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            roomRepository.setSession(session);
            List<String> ids = roomRepository.loadRoomTypeIds();
            transaction.commit();
            return ids;
        }catch (Exception e){
            transaction.rollback();
            //e.printStackTrace();
            System.out.println("loadRoomIds = "+e);
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean saveReservation(ReservationDTO reservationDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            reservationRepository.setSession(session);
            reservationRepository.save(reservationDTO.toEntity());

            updateAvailableRooms(reservationDTO);

            transaction.commit();
            return true;
        }catch (ConstraintViolationException exception){
            AlertController.errormessage("Duplicate ID");
            return false;
        }catch (Exception e){
            transaction.rollback();
            System.out.println("saveReservation = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    public void updateAvailableRooms(ReservationDTO reservationDTO){
        String roomTypeId = reservationDTO.toEntity().getReservationPK().getRoomTypeId();

        int count = reservationRepository.getReservationCount(roomTypeId); //4

        System.out.println("getReservationCount = "+count);

        List<Object[]> list = reservationRepository.getMaxPersonsPerRoom(roomTypeId);
        Object[] result = list.get(0);
        int perRoom = (Integer) result[0];  //2
        int roomQuantity = (Integer) result[1];  //5

        System.out.println("perRoom = "+perRoom);
        System.out.println("roomQuantity = "+roomQuantity);

        int unavailable_rooms = count / perRoom;
        int available_rooms = roomQuantity - unavailable_rooms;

        System.out.println("unaviRoom s= "+unavailable_rooms);
        System.out.println("aviRooms = "+available_rooms);

        reservationRepository.updateAvailableRooms(available_rooms, roomTypeId);

    }

    @Override
    public boolean updateReservation(ReservationDTO reservationDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            reservationRepository.setSession(session);
            reservationRepository.update(reservationDTO.toEntity());
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            System.out.println("updateReservation = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean deleteReservation(ReservationDTO reservationDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            reservationRepository.setSession(session);
            reservationRepository.delete(reservationDTO.toEntity());

            updateAvailableRooms(reservationDTO);

            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            System.out.println("deleteReservation = "+e);
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public ObservableList<ReservationDTO> getDetailsToTableView() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            reservationRepository.setSession(session);
            List<Reservation> allData = reservationRepository.getDetailsToTableView();
            ObservableList<ReservationDTO> obList = FXCollections.observableArrayList();
            for (Reservation data : allData) {
                obList.add(new ReservationDTO(
                        data.getReservationDate(),
                        data.getReservationId(),
                        data.getReservationPK().getRoomTypeId(),
                        data.getReservationPK().getStudentId(),
                        data.getReservationStatus()
                ));
            }
            transaction.commit();
            return obList;
        }catch (Exception e){
            transaction.rollback();
            System.out.println("getDetailsToTableView = "+e);
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public ReservationDTO searchByResId(String id) {
       Session session = SessionFactoryConfig.getInstance().getSession();
       Transaction transaction = session.beginTransaction();

       try{
           reservationRepository.setSession(session);
           Reservation entity = reservationRepository.getById(id);
           ReservationDTO dto = new ReservationDTO(
                   entity.getReservationDate(),
                   entity.getReservationId(),
                   entity.getReservationPK().getRoomTypeId(),
                   entity.getReservationPK().getStudentId(),
                   entity.getReservationStatus()
           );
           transaction.commit();
           return dto;
       }catch (Exception e){
           transaction.rollback();
           System.out.println("searchByResId = "+e);
           return null;
       }finally {
           session.close();
       }
    }
}
