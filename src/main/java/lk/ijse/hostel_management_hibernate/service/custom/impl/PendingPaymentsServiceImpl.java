package lk.ijse.hostel_management_hibernate.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import lk.ijse.hostel_management_hibernate.dto.CustomEntityDTO;
import lk.ijse.hostel_management_hibernate.projection.CustomProjection;
import lk.ijse.hostel_management_hibernate.repository.RepositoryFactory;
import lk.ijse.hostel_management_hibernate.repository.custom.QueryRepository;
import lk.ijse.hostel_management_hibernate.repository.custom.ReservationRepository;
import lk.ijse.hostel_management_hibernate.service.custom.PendingPaymentsService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PendingPaymentsServiceImpl implements PendingPaymentsService {
    private QueryRepository queryRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.QUERY);
    private ReservationRepository reservationRepository = RepositoryFactory.getRepositoryFactory().getRepository(RepositoryFactory.RepositoryTypes.RESERVATION);
    @Override
    public ObservableList<CustomProjection> getDetailsToTableView() {
        Session session = SessionFactoryConfig.getInstance().getSession();

        try{
            queryRepository.setSession(session);
            List<CustomProjection> list = queryRepository.getDetailsOfStudentsWithoutKeyMoney();
            ObservableList<CustomProjection> obList = FXCollections.observableArrayList(list);

            for (CustomProjection cp : obList) {
                System.out.println(cp.getResId());
            }

            session.close();
            return obList;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("getDetailsToPendingPaymentTable = "+e);
            session.close();
            return null;
        }
    }

    @Override
    public boolean updatePendingPayment(CustomEntityDTO customEntityDTO) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            reservationRepository.setSession(session);
            reservationRepository.updatePendingPayment(customEntityDTO);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println("updatePendingPayment = "+e);
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }
}
