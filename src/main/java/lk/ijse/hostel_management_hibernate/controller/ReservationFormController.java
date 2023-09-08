package lk.ijse.hostel_management_hibernate.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.controller.util.ValidateFields;
import lk.ijse.hostel_management_hibernate.dto.ReservationDTO;
import lk.ijse.hostel_management_hibernate.service.ServiceFactory;
import lk.ijse.hostel_management_hibernate.service.custom.ReservationService;
import lk.ijse.hostel_management_hibernate.view.tm.ReservationTM;

public class ReservationFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ancPaneReservation;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbPaymentStatus;

    @FXML
    private ComboBox<String> cmbRoomTypeId;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPaymentStatus;

    @FXML
    private TableColumn<?, ?> colResId;

    @FXML
    private TableColumn<?, ?> colRoomTypeId;

    @FXML
    private TableColumn<?, ?> colStId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private ImageView imgSearch;

    @FXML
    private TableView<ReservationTM> tableReservation;

    @FXML
    private TextField txtResId;

    @FXML
    private TextField txtSearchByReservationId;

    private ReservationService reservationService = ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.RESERVATION);

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/home_form.fxml"));
        ancPaneReservation.getChildren().clear();
        ancPaneReservation.getChildren().add(load);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTxtField();
        txtResId.setDisable(false);
        cmbStudentId.setDisable(false);
        cmbRoomTypeId.setDisable(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean noEmptyFields = noEmptyValuesInTextFields();
        if(noEmptyFields){
            ReservationDTO reservationDTO = getDetailsInTextFields();
            boolean isDelete = reservationService.deleteReservation(reservationDTO);
            if(isDelete){
                setDataToTableView();
                clearTxtField();
                txtResId.setDisable(false);
                cmbStudentId.setDisable(false);
                cmbRoomTypeId.setDisable(false);
                AlertController.successfulMessage("delete successfully");
            }else {
                AlertController.errormessage("delete unsuccessful");
            }
        }else {
            AlertController.errormessage("please make sure to fill out all the required fields");
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean noEmptyFields = noEmptyValuesInTextFields();
        if(noEmptyFields){
            ReservationDTO reservationDTO = getDetailsInTextFields();
            boolean isSaved = reservationService.saveReservation(reservationDTO);
            if(isSaved){
                setDataToTableView();
                clearTxtField();
                AlertController.successfulMessage("saved successfully");
            }else {
                AlertController.errormessage("saved unsuccessful");
            }
        }else {
            AlertController.errormessage("please make sure to fill out all the required fields");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean noEmptyFields = noEmptyValuesInTextFields();
        if(noEmptyFields){
            ReservationDTO reservationDTO = getDetailsInTextFields();
            boolean isUpdate = reservationService.updateReservation(reservationDTO);
            if(isUpdate){
                setDataToTableView();
                clearTxtField();

                txtResId.setDisable(false);
                cmbStudentId.setDisable(false);
                cmbRoomTypeId.setDisable(false);
                AlertController.successfulMessage("update successfully");
            }else {
                AlertController.errormessage("update unsuccessful");
            }
        }else {
            AlertController.errormessage("please make sure to fill out all the required fields");
        }
    }

    private void loadRoomTypeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> roomTypeIds = reservationService.loadRoomTypeIds();

        for (String id : roomTypeIds) {
            obList.add(id);
        }
        cmbRoomTypeId.setItems(obList);
    }

    private void loadStudentIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> studentIds = reservationService.loadStudentIds();

        for (String id : studentIds) {
            obList.add(id);
        }
        cmbStudentId.setItems(obList);
    }

    @FXML
    void initialize() {
        cmbPaymentStatus.getItems().addAll("PAID", "NOT PAID");

        loadStudentIds();
        loadRoomTypeIds();

        setDataToTableView();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colResId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colStId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
    }

    private void setDataToTableView() {
        ObservableList<ReservationTM> obList = FXCollections.observableArrayList();
        ObservableList<ReservationDTO> allData = reservationService.getDetailsToTableView();

        for (ReservationDTO dto : allData) {
            obList.add(new ReservationTM(
                    dto.getReservationId(),
                    dto.getDate(),
                    dto.getStudentId(),
                    dto.getRoomTypeId(),
                    dto.getStatus()
            ));
        }
        tableReservation.setItems(obList);
    }

    private void clearTxtField() {
        txtSearchByReservationId.clear();
        txtResId.clear();
        dpDate.setValue(null);
        cmbRoomTypeId.setValue("");
        cmbStudentId.setValue("");
        cmbPaymentStatus.setValue("");
    }

    private boolean noEmptyValuesInTextFields() {
        String resId = txtResId.getText();
        LocalDate resDate = dpDate.getValue();
        String stId = cmbStudentId.getValue();
        String roomTypeId = cmbRoomTypeId.getValue();
        String status = cmbPaymentStatus.getValue();
        if (!resId.isEmpty() && resDate != null && stId != null && roomTypeId != null && status != null && !stId.isEmpty()
                && !roomTypeId.isEmpty() && !status.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private ReservationDTO getDetailsInTextFields() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationId(txtResId.getText());
        reservationDTO.setDate(dpDate.getValue());
        reservationDTO.setRoomTypeId(cmbRoomTypeId.getValue());
        reservationDTO.setStudentId(cmbStudentId.getValue());
        reservationDTO.setStatus(cmbPaymentStatus.getValue());
        return reservationDTO;
    }

    @FXML
    void tableStudentOnMouseClicked(MouseEvent event) {
        TablePosition pos = tableReservation.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<ReservationTM, ?>> columns = tableReservation.getColumns();

        txtResId.setText(columns.get(0).getCellData(row).toString());
        dpDate.setValue((LocalDate) columns.get(3).getCellData(row));
        cmbStudentId.setValue(columns.get(1).getCellData(row).toString());
        cmbRoomTypeId.setValue(columns.get(2).getCellData(row).toString());
        cmbPaymentStatus.setValue(columns.get(4).getCellData(row).toString());

        txtResId.setStyle("-fx-text-fill: black;-fx-background-radius: 10");

        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        txtResId.setDisable(true);
        cmbStudentId.setDisable(true);
        cmbRoomTypeId.setDisable(true);
    }

    public void btnEnable() {
        if (ValidateFields.rerservationIdCheck(txtResId.getText())) {
            btnSave.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    void txtResIdOKeytypedAction(KeyEvent event) {
        String txt = txtResId.getText();
        if (ValidateFields.rerservationIdCheck(txt)) {
            txtResId.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        } else {
            txtResId.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void imgSearchOnMouseClicked(MouseEvent event) {
        String currentId = txtSearchByReservationId.getText();

        if(!currentId.isEmpty()){
            ReservationDTO dto = reservationService.searchByResId(currentId);
            ObservableList<ReservationTM> obList = FXCollections.observableArrayList();
            if(dto != null){
                obList.add(new ReservationTM(
                        dto.getReservationId(),
                        dto.getDate(),
                        dto.getStudentId(),
                        dto.getRoomTypeId(),
                        dto.getStatus()
                ));

                tableReservation.setItems(obList);
            }else {
                AlertController.errormessage("Invalid Id");
            }
        }
    }

    @FXML
    void txtSearchByReservationIdOnAction(ActionEvent event) {
        String currentId = txtSearchByReservationId.getText();

        if(!currentId.isEmpty()){
            ReservationDTO dto = reservationService.searchByResId(currentId);
            ObservableList<ReservationTM> obList = FXCollections.observableArrayList();
            if(dto != null){
                obList.add(new ReservationTM(
                      dto.getReservationId(),
                      dto.getDate(),
                      dto.getStudentId(),
                      dto.getRoomTypeId(),
                      dto.getStatus()
                ));

                tableReservation.setItems(obList);
            }else {
                AlertController.errormessage("Invalid Id");
            }
        }
    }

}
