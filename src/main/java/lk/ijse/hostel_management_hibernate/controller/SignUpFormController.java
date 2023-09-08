package lk.ijse.hostel_management_hibernate.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostel_management_hibernate.controller.util.AlertController;
import lk.ijse.hostel_management_hibernate.dto.UserDTO;
import lk.ijse.hostel_management_hibernate.service.ServiceFactory;
import lk.ijse.hostel_management_hibernate.service.custom.SignUpService;

public class SignUpFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ancPaneLogin;

    @FXML
    private Button btnSignIn;

    @FXML
    private Label forgotPasswordLbl1;

    @FXML
    private Label forgotPasswordLbl11;

    @FXML
    private Label forgotPasswordLbl111;

    @FXML
    private Label forgotPasswordLbl12;

    @FXML
    private Hyperlink hyprlnkSignIn;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private SignUpService signUpService = ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.SIGN_UP);

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if(!userName.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
            if(password.equals(confirmPassword)){
                boolean isSaved = signUpService.saveUser(new UserDTO(
                        userName,
                        password
                ));
                if(isSaved){
                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/view/loginPage_form.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(HomeFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Login Form");
                    ancPaneLogin.getScene().getWindow().hide();
                    stage.show();

                    AlertController.animationMesseage("assets/images/wdoneIcon.png", "Sign Up", "New account create successful");

                }else {
//                    AlertController.errormessage("New account create unsuccessful");
                }
            }else {
                AlertController.errormessage("Password not same");
            }
        }else {
            AlertController.errormessage("please make sure to fill out\nall the required fields");
        }
    }

    @FXML
    void signInOnAction(ActionEvent event) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/loginPage_form.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login Form");
        ancPaneLogin.getScene().getWindow().hide();
        stage.show();
    }

    @FXML
    void initialize() {

    }

}
