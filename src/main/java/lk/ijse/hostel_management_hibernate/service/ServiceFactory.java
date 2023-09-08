package lk.ijse.hostel_management_hibernate.service;

import lk.ijse.hostel_management_hibernate.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory(){

    }

    public static ServiceFactory getServiceFactory(){
        return (null==serviceFactory) ? serviceFactory = new ServiceFactory() : serviceFactory;
    }

    public enum ServiceTypes{
        STUDENT,ROOM,RESERVATION,KEY_MONEY_STUDENT,HOME,LOGIN,SIGN_UP,CHANGE_USER_DETAILS
    }

    public <T extends SuperService> T getService(ServiceTypes types){
        switch (types){
            case STUDENT:
                return (T) new StudentServiceImpl();
            case ROOM:
                return (T) new RoomServiceImpl();
            case RESERVATION:
                return (T) new ReservationServiceImpl();
            case KEY_MONEY_STUDENT:
                return (T) new PendingPaymentsServiceImpl();
            case HOME:
                return (T) new HomeServiceImpl();
            case LOGIN:
                return (T) new LoginServiceImpl();
            case SIGN_UP:
                return (T) new SignUpServiceImpl();
            case CHANGE_USER_DETAILS:
                return (T) new ChangeUserDetailServiceImpl();
            default:
                return null;
        }
    }
}
