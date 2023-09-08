package lk.ijse.hostel_management_hibernate.projection;

import java.time.LocalDate;

public class CustomProjection {
    private String resId;
    private String studentId;
    private String studentName;
    private String address;
    private String studentContact;
    private LocalDate resDate;
    private String keyMoney;

    public CustomProjection() {
    }

    public CustomProjection(String resId, String studentId, String studentName, String address, String studentContact, LocalDate resDate, String keyMoney) {
        this.resId = resId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.address = address;
        this.studentContact = studentContact;
        this.resDate = resDate;
        this.keyMoney = keyMoney;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStudentContact() {
        return studentContact;
    }

    public void setStudentContact(String studentContact) {
        this.studentContact = studentContact;
    }

    public LocalDate getResDate() {
        return resDate;
    }

    public void setResDate(LocalDate resDate) {
        this.resDate = resDate;
    }

    public String getKeyMoney() {
        return keyMoney;
    }

    public void setKeyMoney(String keyMoney) {
        this.keyMoney = keyMoney;
    }

    @Override
    public String toString() {
        return "CustomProjection{" +
                "resId='" + resId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", StudentName='" + studentName + '\'' +
                ", address='" + address + '\'' +
                ", studentContact='" + studentContact + '\'' +
                ", resDate=" + resDate +
                ", keyMoney='" + keyMoney + '\'' +
                '}';
    }
}
