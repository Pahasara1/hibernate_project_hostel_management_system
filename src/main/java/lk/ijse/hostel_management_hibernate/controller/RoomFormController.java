package lk.ijse.hostel_management_hibernate.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.controller.util.ValidateFields;
import lk.ijse.hostel_management_hibernate.dto.RoomDTO;
import lk.ijse.hostel_management_hibernate.dto.StudentDTO;
import lk.ijse.hostel_management_hibernate.service.ServiceFactory;
import lk.ijse.hostel_management_hibernate.service.custom.RoomService;
import lk.ijse.hostel_management_hibernate.view.tm.RoomTM;

public class RoomFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ancPaneRoomForm;

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
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colPersonsQty;

    @FXML
    private TableColumn<?, ?> colRoomQty;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<RoomTM> tableRoom;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtKeyMoney;

    @FXML
    private TextField txtPersonQty;

    @FXML
    private TextField txtRoomQty;

    @FXML
    private TextField txtType;

    private RoomService roomService = ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.ROOM);

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/home_form.fxml"));
        ancPaneRoomForm.getChildren().clear();
        ancPaneRoomForm.getChildren().add(load);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTxtFields();
        btnSave.setDisable(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        RoomDTO roomDTO = getDetailsInTextFields();
        boolean isEmpty = noEmptyTextFields();
        if(isEmpty){
            boolean isDelete = roomService.deleteRoomType(roomDTO);
            if(isDelete){
                setDataToTableView();
                clearTxtFields();
                btnSave.setDisable(false);
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
        RoomDTO roomDTO = getDetailsInTextFields();
        boolean isEmpty = noEmptyTextFields();
        if(isEmpty){
            boolean isSaved = roomService.saveRoomType(roomDTO);
            if(isSaved){
                setDataToTableView();
                clearTxtFields();
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
        RoomDTO roomDTO = getDetailsInTextFields();
        boolean isEmpty = noEmptyTextFields();
        if(isEmpty){
            boolean isUpdate = roomService.updateRoomType(roomDTO);
            if(isUpdate){
                setDataToTableView();
                clearTxtFields();
                btnSave.setDisable(false);
                AlertController.successfulMessage("update successfully");
            }else {
                AlertController.errormessage("update unsuccessful");
            }
        }else {
            AlertController.errormessage("please make sure to fill out all the required fields");
        }
    }

    @FXML
    void tableStudentOnMouseClicked(MouseEvent event) {
        TablePosition pos = tableRoom.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<RoomTM, ?>> columns = tableRoom.getColumns();

        txtId.setText(columns.get(0).getCellData(row).toString());
        txtType.setText(columns.get(1).getCellData(row).toString());
        txtPersonQty.setText(columns.get(2).getCellData(row).toString());
        txtKeyMoney.setText(columns.get(3).getCellData(row).toString());
        txtRoomQty.setText(columns.get(4).getCellData(row).toString());

        txtId.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
        txtRoomQty.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
        txtPersonQty.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
        txtKeyMoney.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
        txtType.setStyle("-fx-text-fill: black; -fx-background-radius: 10");

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    @FXML
    void initialize() {
        setDataToTableView();
        setCellValueFactory();
    }

    private void clearTxtFields() {
        txtPersonQty.clear();
        txtRoomQty.clear();
        txtKeyMoney.clear();
        txtId.clear();
        txtType.clear();
    }

    private boolean noEmptyTextFields() {
        String roomTypeId = txtId.getText();
        String roomType = txtType.getText();
        String roomQty = txtRoomQty.getText();
        String keyMoney = txtKeyMoney.getText();
        String maxPer = txtPersonQty.getText();
        if (!roomTypeId.isEmpty() && !roomType.isEmpty() && !roomQty.isEmpty() && !keyMoney.isEmpty() &&
                !maxPer.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private RoomDTO getDetailsInTextFields() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setPerRoom(Integer.parseInt(txtPersonQty.getText()));
        roomDTO.setRoomQty(Integer.parseInt(txtRoomQty.getText()));
        roomDTO.setKeyMoney(txtKeyMoney.getText());
        roomDTO.setRoomTypeId(txtId.getText());
        roomDTO.setRoomType(txtType.getText());
        return roomDTO;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colPersonsQty.setCellValueFactory(new PropertyValueFactory<>("perRoom"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colRoomQty.setCellValueFactory(new PropertyValueFactory<>("roomQty"));
    }

    private void setDataToTableView() {
        ObservableList<RoomDTO> roomDTOS = roomService.getDetailsToTableView();
        ObservableList<RoomTM> roomTMS = FXCollections.observableArrayList();
        for (RoomDTO dto : roomDTOS) {
            roomTMS.add(
                    new RoomTM(
                            dto.getRoomTypeId(),
                            dto.getRoomType(),
                            dto.getPerRoom(),
                            dto.getKeyMoney(),
                            dto.getRoomQty()
                    )
            );
        }
        tableRoom.setItems(roomTMS);
    }

    public void btnEnable(){
        if(ValidateFields.roomIdCheck(txtId.getText()) && ValidateFields.moneyCheck(txtKeyMoney.getText()) &&
                ValidateFields.numberCheck(txtRoomQty.getText()) && ValidateFields.numberCheck(txtPersonQty.getText())
        ){
            btnSave.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    void txtIdOnKeyTypedAction(KeyEvent event) {
        String txt = txtId.getText();
        if(ValidateFields.roomIdCheck(txt)){
            txtId.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        }else{
            txtId.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void txtKeyMoneyOnKeyTypedAction(KeyEvent event) {
        String txt = txtKeyMoney.getText();
        if(ValidateFields.moneyCheck(txt)){
            txtKeyMoney.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        }else{
            txtKeyMoney.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void txtPersonQtyOnKeyTypedAction(KeyEvent event) {
        String txt = txtPersonQty.getText();
        if(ValidateFields.numberCheck(txt)){
            txtPersonQty.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        }else{
            txtPersonQty.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void txtRoomQtyOnKeyTypedAction(KeyEvent event) {
        String txt = txtRoomQty.getText();
        if(ValidateFields.numberCheck(txt)){
            txtRoomQty.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        }else{
            txtRoomQty.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void txtTypeOnKeyTypedAction(KeyEvent event) {

    }

}
