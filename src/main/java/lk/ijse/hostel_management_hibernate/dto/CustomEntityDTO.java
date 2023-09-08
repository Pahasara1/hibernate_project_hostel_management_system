package lk.ijse.hostel_management_hibernate.dto;

public class CustomEntityDTO {
    private String reservationId;
    private String status;

    public CustomEntityDTO() {
    }

    public CustomEntityDTO(String reservationId, String status) {
        this.reservationId = reservationId;
        this.status = status;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomEntityDTO{" +
                "reservationId='" + reservationId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
