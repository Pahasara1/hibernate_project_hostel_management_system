package lk.ijse.hostel_management_hibernate.controller;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.dto.CustomEntityDTO;
import lk.ijse.hostel_management_hibernate.projection.CustomProjection;
import lk.ijse.hostel_management_hibernate.service.ServiceFactory;
import lk.ijse.hostel_management_hibernate.service.custom.PendingPaymentsService;
import lk.ijse.hostel_management_hibernate.view.tm.StudentKeyMoneyTM;
import lk.ijse.hostel_management_hibernate.view.tm.StudentTM;

public class PendingPaymentsFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ancPanePendingPayments;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContacts;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colResDate;

    @FXML
    private TableColumn<?, ?> colResId;

    @FXML
    private TableColumn<?, ?> colStId;

    @FXML
    private Label lblKeyMoney;

    @FXML
    private Label lblReservationId;

    @FXML
    private Label lblStudentId;

    @FXML
    private Label lblStudentName;

    @FXML
    private RadioButton radioBtnPaid;

    @FXML
    private JFXButton btnUpdate;


    @FXML
    private TableView<StudentKeyMoneyTM> tableStudentKeyMoney;

    private PendingPaymentsService pendingPaymentsService = ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.KEY_MONEY_STUDENT);
    private CustomEntityDTO customEntityDTO;

    @FXML
    void tablePendingPayOnMouseClicked(MouseEvent event) {

        try{
            TablePosition pos = tableStudentKeyMoney.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            ObservableList<TableColumn<StudentKeyMoneyTM, ?>> columns = tableStudentKeyMoney.getColumns();

            lblReservationId.setText(columns.get(0).getCellData(row).toString());
            lblStudentId.setText(columns.get(1).getCellData(row).toString());
            lblStudentName.setText(columns.get(2).getCellData(row).toString());
            lblKeyMoney.setText(columns.get(6).getCellData(row).toString());
        }catch (RuntimeException exception){
            AlertController.errormessage("This row is Empty");
            System.out.println("tablePendingPayOnMouseClicked = "+exception);
        }catch (Exception e){
            System.out.println("tablePendingPayOnMouseClicked = "+e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(radioBtnPaid.isSelected()){
            customEntityDTO.setReservationId(lblReservationId.getText());
            boolean isUpdate = pendingPaymentsService.updatePendingPayment(customEntityDTO);
            if(isUpdate){
                setDataToTableView();
                clearTxtFields();
                AlertController.successfulMessage("update successfully");
            }else {
                AlertController.errormessage("update unsuccessful");
            }
        }else {
            AlertController.errormessage("Please tick the paid button");
        }

    }

    private void clearTxtFields() {
        lblReservationId.setText(null);
        lblStudentId.setText(null);
        lblStudentName.setText(null);
        lblKeyMoney.setText(null);
        radioBtnPaid.setSelected(false);
    }

    @FXML
    void radioBtnPaidOnAction(ActionEvent event) {
        customEntityDTO.setStatus("PAID");
    }

    @FXML
    void initialize() {
        customEntityDTO = new CustomEntityDTO();
        setDataToTableView();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colStId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContacts.setCellValueFactory(new PropertyValueFactory<>("studentContact"));
        colResId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        colResDate.setCellValueFactory(new PropertyValueFactory<>("resDate"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));

    }

    private void setDataToTableView() {
        ObservableList<CustomProjection> customProjections =pendingPaymentsService.getDetailsToTableView();
        ObservableList<StudentKeyMoneyTM> obList = FXCollections.observableArrayList();

        for (CustomProjection cp : customProjections) {
            obList.add(new StudentKeyMoneyTM(
                    cp.getResId(),
                    cp.getStudentId(),
                    cp.getStudentName(),
                    cp.getAddress(),
                    cp.getStudentContact(),
                    cp.getResDate(),
                    cp.getKeyMoney()
            ));
        }
        tableStudentKeyMoney.setItems(obList);
    }
}
