package kursinis.viljamas.fxControllers;

import kursinis.viljamas.HelloApplication;
import kursinis.viljamas.hibernate.ShopHibernate;
import kursinis.viljamas.fxControllers.FxUtils;
import kursinis.viljamas.model.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static kursinis.viljamas.fxControllers.FxUtils.generateAlert;

public class LoginForm implements Initializable {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;

    private EntityManagerFactory entityManagerFactory;

    public void validateAndLoadMain() throws IOException {
        // Get the password entered by the user
        String enteredPassword = pswField.getText();

        // Retrieve the user from the database based on the username
        ShopHibernate hibernateShop = new ShopHibernate(entityManagerFactory);
        User user = hibernateShop.getUserByLogin(loginField.getText());

        // Validate login and password fields
        if (loginField == null) {
            generateAlert(Alert.AlertType.WARNING, "Login cannot be empty.", "Please fill login field");
            return;
        }

        if (pswField == null) {
            generateAlert(Alert.AlertType.WARNING, "Password cannot be empty.", "Please fill Password field");
            return;
        }

        // If user exists and passwords match, open the main window
        if (user != null && isPasswordCorrect(enteredPassword, user.getPassword())) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-window.fxml"));
            Parent parent = fxmlLoader.load();
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setData(entityManagerFactory, (User) user);
            Stage stage = (Stage) loginField.getScene().getWindow();
            Scene scene = new Scene(parent);
            FxUtils.setStageParameters(stage, scene, false);
        } else {
            // Passwords don't match or user not found, show an alert
            generateAlert(Alert.AlertType.INFORMATION, "Login error", "No such user or wrong credentials");
        }
    }

    private boolean isPasswordCorrect(String enteredPassword, String hashedPasswordFromDatabase) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(enteredPassword, hashedPasswordFromDatabase);
    }

    private String hashPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainPassword);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("Kursinis");
    }

    public void openRegistration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        //Access controller of main window. Each form has its own controller, so make sure that you make no mistake here
        RegistrationController registrationController = fxmlLoader.getController();
        registrationController.setData(entityManagerFactory, false);
        Stage stage = (Stage) loginField.getScene().getWindow();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, false);
    }
}
