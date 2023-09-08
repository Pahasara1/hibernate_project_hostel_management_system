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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.dto.UserDTO;
import lk.ijse.hostel_management_hibernate.entity.User;
import lk.ijse.hostel_management_hibernate.service.ServiceFactory;
import lk.ijse.hostel_management_hibernate.service.custom.LoginService;

public class LoginFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ancPaneLogin;

    @FXML
    private Button btnLogin;

    @FXML
    private Group demoPasswordGroup;

    @FXML
    private Label forgotPasswordLbl1;

    @FXML
    private Label forgotPasswordLbl11;

    @FXML
    private Label forgotPasswordLbl12;

    @FXML
    private Hyperlink hyprlnkSignUp;

    @FXML
    private ImageView imageHideIcon;

    @FXML
    private ImageView imageUnHideIcon;

    @FXML
    private Group originalPasswordGroup;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPasswordDemo;

    @FXML
    private TextField txtUsername;

    private LoginService loginService = ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.LOGIN);

    @FXML
    void passwordHideOnMouseExited(MouseEvent event) {
        demoPasswordGroup.setVisible(false);
        originalPasswordGroup.setVisible(true);
    }

    @FXML
    void passwordShowOnMouseEntered(MouseEvent event) {
        demoPasswordGroup.setVisible(true);
        txtPasswordDemo.setText(txtPassword.getText());
        originalPasswordGroup.setVisible(false);
    }

    @FXML
    void signUpOnAction(ActionEvent event) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/signUp_form.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignUp Page");
        ancPaneLogin.getScene().getWindow().hide();
        stage.show();
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        if(!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()){
            String isValidUserName = txtUsername.getText();
            UserDTO userDTO = loginService.searchByUserName(isValidUserName);
            if(userDTO != null){
                if(txtPassword.getText().equals(userDTO.getPassword())){

                    ChangeUserDetailFormController.currentPassword = txtPassword.getText();
                    ChangeUserDetailFormController.currentUserName = txtUsername.getText();

                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/view/home_form.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(HomeFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Home Page");
                    ancPaneLogin.getScene().getWindow().hide();
                    stage.show();

                    AlertController.animationMesseage("assets/images/wdoneIcon.png", "Login", "login successful");
                }else {
                    AlertController.errormessage("incorrect password");
                }

            }else {
                AlertController.errormessage("Invalid UserName");
            }
        }else {
            AlertController.errormessage("please make sure to fill out\nall the required fields");
        }
    }

    @FXML
    void initialize() {

    }
}
