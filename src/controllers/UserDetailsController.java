package controllers;

import Dao.AlertDao;
import application.LibrarySystem;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import interfaces.ControlledScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.UserModel;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable, ControlledScreen {
    static int userId;
    static boolean newUser = false;
    ScreensController controller;
    private UserModel userModel;

    public UserDetailsController() {
        userModel = new UserModel();
    }

    public static void setNewUser(boolean newUser) {
        UserDetailsController.newUser = newUser;
    }

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXRadioButton isAdmin;

    @FXML
    private JFXRadioButton notAdmin;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnModify;

    @FXML
    private Label lblError;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[LOG] Inside User Details");
        firstName.setLabelFloat(true);
        lastName.setLabelFloat(true);
        email.setLabelFloat(true);
        password.setLabelFloat(true);
        phone.setLabelFloat(true);
        if (!newUser) {
            btnAdd.setDisable(true);
            setDetailsForId();
        } else {
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            setAsUser();
        }

    }

    public static void setUserId(int Id) {
        userId = Id;
        System.out.println("User ID inside user details " + userId);
    }

    public void addUser() {
        this.lblError.setText("");
        if (!validate()) {
            return;
        }
        UserModel existingUser = userModel.getUser(this.email.getText());
        if (existingUser != null) {
            this.lblError.setText("User already exists! Try logging In");
            return;
        }
        Boolean result = userModel.addUser(this.firstName.getText(), this.lastName.getText(), this.email.getText(), this.phone.getText(), this.password.getText(),this.isAdmin.isSelected());
        if (!result) {
            this.lblError.setText("There is an error please try again later");
            return;
        }
        System.out.println("User add successful");
        AlertDao.Display("Add User", "User successfully added");
        clear();
        controller.loadScreen(LibrarySystem.screen5ID, LibrarySystem.screen5File);
        controller.unloadScreen(LibrarySystem.screen6ID);
        controller.setScreen(LibrarySystem.screen5ID);
    }

    public void deleteUser() {
        Boolean result = userModel.deleteUser(userId);
        if (!result) {
            this.lblError.setText("There is an error please try again later");
            return;
        }
        System.out.println("User delete successful");
        AlertDao.Display("Delete User", "User successfully deleted");
        clear();
        controller.loadScreen(LibrarySystem.screen5ID, LibrarySystem.screen5File);
        controller.unloadScreen(LibrarySystem.screen6ID);
        controller.setScreen(LibrarySystem.screen5ID);
    }

    public void modifyUser() {
        this.lblError.setText("");
        if (!validate()) {
            return;
        }
        Boolean result = userModel.updateUser(userId,this.firstName.getText(),this.lastName.getText(),this.email.getText(),this.phone.getText(),this.password.getText(),this.isAdmin.isSelected());
        if (!result) {
            this.lblError.setText("There is an error please try again later");
            return;
        }
        System.out.println("User modify successful");
        AlertDao.Display("Modify User", "User successfully modified");
    }

    public Boolean validate() {
        if (this.firstName.textProperty().isEmpty()
                .or(this.lastName.textProperty().isEmpty())
                .or(this.email.textProperty().isEmpty())
                .or(this.password.textProperty().isEmpty())
                .or(this.phone.textProperty().isEmpty())
                .get()
        ) {
            this.lblError.setText("All the fields are required");
            return false;
        }
        return true;
    }

    public void logout() {
        clear();
        System.out.println("Logging Out");
        UserViewController.setUserId(0);
        BookViewController.setUserId(0);
        controller.unloadScreen(LibrarySystem.screen6ID);
        controller.setScreen(LibrarySystem.screen1ID);
    }

    public void goBack() {
        clear();
        System.out.println("Back to Books View from Users");
        controller.loadScreen(LibrarySystem.screen5ID, LibrarySystem.screen5File);
        controller.unloadScreen(LibrarySystem.screen6ID);
        controller.setScreen(LibrarySystem.screen5ID);
    }

    public void setDetailsForId() {
        UserModel user = userModel.getUserForId(userId);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmailId());
        password.setText(user.getPassword());
        phone.setText(user.getPhone());
        if (user.getAdmin()) {
            isAdmin.setSelected(true);
        } else {
            notAdmin.setSelected(true);
        }
    }

    public void setAsAdmin() {
        isAdmin.setSelected(true);
        notAdmin.setSelected(false);
    }

    public void setAsUser() {
        isAdmin.setSelected(false);
        notAdmin.setSelected(true);
    }

    public void clear() {
        userId = 0;
        newUser = false;
        firstName.clear();
        lastName.clear();
        email.clear();
        password.clear();
        phone.clear();
        lblError.setText("");
        isAdmin.setSelected(false);
        notAdmin.setSelected(false);
    }
}
