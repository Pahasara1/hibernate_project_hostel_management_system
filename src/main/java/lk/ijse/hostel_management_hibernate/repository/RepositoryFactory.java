package lk.ijse.hostel_management_hibernate.repository;

import lk.ijse.hostel_management_hibernate.repository.custom.impl.*;

public class RepositoryFactory {
    private static RepositoryFactory repositoryFactory;

    private RepositoryFactory(){

    }

    public static RepositoryFactory getRepositoryFactory(){
        return (null==repositoryFactory) ? repositoryFactory = new RepositoryFactory() : repositoryFactory;
    }

    public enum RepositoryTypes{
        STUDENT,ROOM,RESERVATION,QUERY,USER
    }

    public <T extends SuperRepository>T getRepository(RepositoryTypes type){
        switch (type){
            case STUDENT:
                return (T) new StudentRepositoryImpl();
            case ROOM:
                return (T) new RoomRepositoryImpl();
            case RESERVATION:
                return (T) new ReservationRepositoryImpl();
            case QUERY:
                return (T) new QueryRepositoryImpl();
            case USER:
                return (T) new UserRepositoryImpl();
            default:
                return null;
        }
    }
}
