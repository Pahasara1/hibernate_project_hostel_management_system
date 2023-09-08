package lk.ijse.hostel_management_hibernate.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.controller.util.ValidateFields;
import lk.ijse.hostel_management_hibernate.dto.StudentDTO;
import lk.ijse.hostel_management_hibernate.service.ServiceFactory;
import lk.ijse.hostel_management_hibernate.service.custom.StudentService;
import lk.ijse.hostel_management_hibernate.view.tm.StudentTM;

public class StudentFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ancPaneStudentForm;

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
    private ComboBox<String> cmbGender;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContacts;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private DatePicker dpDob;

    @FXML
    private TableView<StudentTM> tableStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact1;

    @FXML
    private TextField txtStId;

    @FXML
    private TextField txtStName;

    private StudentService studentService = ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.STUDENT);

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/home_form.fxml"));
        ancPaneStudentForm.getChildren().clear();
        ancPaneStudentForm.getChildren().add(load);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTxtFields();
        btnSave.setDisable(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        StudentDTO studentDTO = getDetailsInTextFields();
        boolean isEmpty = noEmptyTextFields();
        if(isEmpty){
            boolean isDelete = studentService.deleteStudent(studentDTO);
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
        StudentDTO studentDTO = getDetailsInTextFields();
        boolean isEmpty = noEmptyTextFields();
        if(isEmpty){
            boolean isSaved = studentService.saveStudent(studentDTO);
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
        StudentDTO studentDTO = getDetailsInTextFields();
        boolean isEmpty = noEmptyTextFields();
        if(isEmpty){
            boolean isUpdate = studentService.updateStudent(studentDTO);
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
        TablePosition pos = tableStudent.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<StudentTM, ?>> columns = tableStudent.getColumns();

        txtStId.setText(columns.get(0).getCellData(row).toString());
        txtStName.setText(columns.get(1).getCellData(row).toString());
        txtAddress.setText(columns.get(2).getCellData(row).toString());
        txtContact1.setText(columns.get(3).getCellData(row).toString());
        dpDob.setValue((LocalDate) columns.get(4).getCellData(row));
        cmbGender.setValue(columns.get(5).getCellData(row).toString());

        txtStName.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
        txtAddress.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
        txtContact1.setStyle("-fx-text-fill: black; -fx-background-radius: 10");

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    @FXML
    void initialize() {
        cmbGender.getItems().addAll("Male", "Female");

        setDataToTableView();
        setCellValueFactory();
    }

    private void setDataToTableView() {
        ObservableList<StudentDTO> studentDTOS = studentService.getDetailsToTableView();
        ObservableList<StudentTM> studentTMS = FXCollections.observableArrayList();

        for (StudentDTO dto : studentDTOS) {
            studentTMS.add(new StudentTM(
                    dto.getId(),
                    dto.getAddress(),
                    dto.getDob(),
                    dto.getGender(),
                    dto.getName(),
                    dto.getStudentContact()
            ));
        }
        tableStudent.setItems(studentTMS);
    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContacts.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }

    public StudentDTO getDetailsInTextFields(){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(txtStId.getText());
        studentDTO.setAddress(txtAddress.getText());
        studentDTO.setDob(dpDob.getValue());
        studentDTO.setGender(cmbGender.getValue());
        studentDTO.setName(txtStName.getText());
        studentDTO.setStudentContact(txtContact1.getText());

        return studentDTO;
    }

    void clearTxtFields(){
        txtStId.clear();
        txtStName.clear();
        txtContact1.clear();
        txtAddress.clear();
        dpDob.setValue(null);
        cmbGender.setValue("");
    }

    public boolean noEmptyTextFields(){
        String stId = txtStId.getText();
        String stAdd = txtAddress.getText();
        LocalDate dob = dpDob.getValue();
        String gender = cmbGender.getValue();
        String name = txtStName.getText();
        String contact = txtContact1.getText();
        if (!stId.isEmpty() && !stAdd.isEmpty() && !contact.isEmpty() && gender!=null && dob!=null &&
                !gender.isEmpty() && !name.isEmpty() ) {
            return true;
        } else {
            return false;
        }
    }

    public void btnEnable(){
        if(ValidateFields.nameCheck(txtStName.getText()) && ValidateFields.addressCheck(txtAddress.getText()) &&
                ValidateFields.contactCheck(txtContact1.getText())
        ){
            btnSave.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    void txtAddressOnMouseKeyTyped(KeyEvent event) {
        String txt = txtAddress.getText();
        if(ValidateFields.addressCheck(txt)){
            txtAddress.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        }else{
            txtAddress.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void txtContact1OnMouseKeyTyped(KeyEvent event) {
        String txt = txtContact1.getText();
        if(ValidateFields.contactCheck(txt)){
            txtContact1.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        }else{
            txtContact1.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void txtStIdOnMouseKeyTyped(KeyEvent event) {
        String txt = txtStId.getText();
        if(ValidateFields.studentIdCheck(txt)){
            txtStId.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        }else{
            txtStId.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void txtStNameOnMouseKeyTyped(KeyEvent event) {
        String txt = txtStName.getText();
        if(ValidateFields.nameCheck(txt)){
            txtStName.setStyle("-fx-text-fill: black; -fx-background-radius: 10");
            btnEnable();
        }else{
            txtStName.setStyle("-fx-text-fill: red; -fx-background-radius: 10");
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }
}
