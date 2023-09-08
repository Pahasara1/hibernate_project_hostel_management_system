import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.hostel_management_hibernate.config.SessionFactoryConfig;
import org.hibernate.Session;

import java.net.URL;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = Launcher.class.getResource("/view/loginPage_form.fxml");
        Parent load = FXMLLoader.load(resource);
        stage.setScene(new Scene(load));
        stage.setTitle("Login Page");
        stage.centerOnScreen();
        stage.show();

        Session session = SessionFactoryConfig.getInstance().getSession();
    }
}
