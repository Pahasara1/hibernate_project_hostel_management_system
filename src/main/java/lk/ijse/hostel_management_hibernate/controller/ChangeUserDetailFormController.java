package lk.ijse.hostel_management_hibernate.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.dto.UserDTO;
import lk.ijse.hostel_management_hibernate.service.ServiceFactory;
import lk.ijse.hostel_management_hibernate.service.custom.ChangeUserDetailService;

public class ChangeUserDetailFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ancrPaneChangeDetails;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnConfirm;

    @FXML
    private Group demoPasswordGroup;

    @FXML
    private Label forgotPasswordLbl1;

    @FXML
    private Label forgotPasswordLbl11;

    @FXML
    private Label forgotPasswordLbl111;

    @FXML
    private Group groupChangeDetails;

    @FXML
    private Group groupConfirmPassword;

    @FXML
    private ImageView imageHideIcon;

    @FXML
    private ImageView imageUnHideIcon;

    @FXML
    private Group originalPasswordGroup;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassword1;

    @FXML
    private TextField txtPasswordDemo;

    @FXML
    private TextField txtUsername;

    public static String currentUserName;
    public static String currentPassword;

    private ChangeUserDetailService changeUserDetailService = ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.CHANGE_USER_DETAILS);
    @FXML
    void btnChangeOnAction(ActionEvent event) {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if(!userName.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
            if(password.equals(confirmPassword)){
                boolean isDelete = changeUserDetailService.deleteUserDetails(currentUserName);
                if(isDelete){
                    boolean isUpdate = changeUserDetailService.changeUserDetails(new UserDTO(
                            userName,
                            password
                    ));
                    if(isUpdate){
                        currentUserName = userName;
                        currentPassword = password;
                        clearTxtField();
                        AlertController.animationMesseage("assets/images/wdoneIcon.png", "Details", "Details change successful");

                    }else {
                        AlertController.errormessage("Detail change unsuccessful");
                    }
                }else {
                    AlertController.errormessage("Detail change unsuccessful");
                }
            }else {
                AlertController.errormessage("Password not same");
            }
        }else {
            AlertController.errormessage("please make sure to fill out\nall the required fields");
        }
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        if(txtPassword1.getText().equals(currentPassword)){
            groupConfirmPassword.setVisible(false);
            groupChangeDetails.setVisible(true);
        }else {
            AlertController.errormessage("Wrong Password");
        }
    }

    @FXML
    void passwordHideOnMouseExited(MouseEvent event) {
        demoPasswordGroup.setVisible(false);
        originalPasswordGroup.setVisible(true);
    }

    @FXML
    void passwordShowOnMouseEntered(MouseEvent event) {
        demoPasswordGroup.setVisible(true);
        txtPasswordDemo.setText(txtPassword1.getText());
        originalPasswordGroup.setVisible(false);
    }

    @FXML
    void initialize() {
        System.out.println(currentPassword);
        System.out.println(currentUserName);
    }
    public void clearTxtField(){
        txtUsername.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
    }
}
