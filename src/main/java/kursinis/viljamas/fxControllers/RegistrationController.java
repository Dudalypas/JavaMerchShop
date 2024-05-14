package kursinis.viljamas.fxControllers;

import javafx.application.Platform;
import kursinis.viljamas.HelloApplication;
import kursinis.viljamas.hibernate.ShopHibernate;
import kursinis.viljamas.model.Customer;
import kursinis.viljamas.model.Manager;
import kursinis.viljamas.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.time.LocalDate;

import static kursinis.viljamas.fxControllers.FxUtils.generateAlert;

public class RegistrationController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public RadioButton customerCheckbox;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton managerCheckbox;
    @FXML
    public TextField addressField;
    @FXML
    public TextField cardNoField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField employeeIdField;
    @FXML
    public TextField medCertificateField;
    @FXML
    public DatePicker employmentDateField;
    @FXML
    public CheckBox isAdminCheck;
    @FXML
    public TextField billingAddressField;
    @FXML

    public EntityManagerFactory entityManagerFactory;

    Manager EntityManager;

    public void setData(EntityManagerFactory entityManagerFactory, boolean showManagerFields) {
        this.entityManagerFactory = entityManagerFactory;
        disableFields(showManagerFields);
    }

    public void setDataManager(EntityManagerFactory entityManagerFactory, Manager manager) {
        this.entityManagerFactory = entityManagerFactory;
        toggleFields(customerCheckbox.isSelected(), manager);
        EntityManager = manager;
    }

    public boolean AccessedByAdmin(){
        return EntityManager != null;
    }

    private void disableFields(boolean showManagerFields) {
        if (!showManagerFields) {
            employeeIdField.setVisible(false);
            medCertificateField.setVisible(false);
            employmentDateField.setVisible(false);
            isAdminCheck.setVisible(false);
            managerCheckbox.setVisible(false);
        }
    }

    private void toggleFields(boolean isManager, Manager manager) {
        if (isManager) {
            addressField.setDisable(true);
            cardNoField.setDisable(true);
            employeeIdField.setDisable(false);
            medCertificateField.setDisable(false);
            employmentDateField.setDisable(false);
            customerCheckbox.setVisible(false);
            if (manager.isAdmin()) isAdminCheck.setDisable(false);
        } else {
            addressField.setDisable(false);
            cardNoField.setDisable(false);
            employeeIdField.setDisable(true);
            medCertificateField.setDisable(true);
            employmentDateField.setDisable(true);
            isAdminCheck.setDisable(true);
        }
    }

    public void createUser() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();
        String cardNo = cardNoField.getText();
        String address = addressField.getText();
        String billingAddress = billingAddressField.getText();
        LocalDate birthDate = birthDateField.getValue();

        // Validate login and password fields
        if (login == null || login.isEmpty()) {
            showAlert("Error", "Login cannot be empty.");
            return;
        }

        if (password == null || password.isEmpty()) {
            showAlert("Error", "Password cannot be empty.");
            return;
        }

        ShopHibernate shopHibernate = new ShopHibernate(entityManagerFactory);
        String hashedPassword = hashPassword(password); // Hash the password

        if (customerCheckbox.isSelected()) {
            User user = new Customer(name, surname, login, hashedPassword, cardNo, address, billingAddress, birthDate);
            shopHibernate.create(user);
        } else {
            Manager manager = new Manager(name, surname, login, hashedPassword, isAdminCheck.isSelected());
            shopHibernate.create(manager);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private String hashPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainPassword);
    }

    public void returnToLogin() throws IOException {
        if (!AccessedByAdmin()) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-form.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) loginField.getScene().getWindow();
            Scene scene = new Scene(parent);
            FxUtils.setStageParameters(stage, scene, false);
        } else {
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.close();
        }
    }
}
