package controllers;

import application.LibrarySystem;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import interfaces.ControlledScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.UserModel;

import java.net.URL;
import java.util.ResourceBundle;

public class UserLoginController implements Initializable, ControlledScreen {

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private Label lblError;

    private UserModel model;

    public UserLoginController() {
        model = new UserModel();
    }

    ScreensController controller;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inside the Login Controller for User");
        this.txtUsername.setLabelFloat(true);
        this.txtPassword.setLabelFloat(true);
        this.lblError.setText("");
    }

    public void loginUser() {
        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();
        if (username == null || username.trim().equals("")) {
            lblError.setText("Username Cannot be empty or spaces");
            return;
        }
        if (password == null || password.trim().equals("")) {
            lblError.setText("Password Cannot be empty or spaces");
            return;
        }
        if (username == null || username.trim().equals("") && (password == null || password.trim().equals(""))) {
            lblError.setText("User name / Password Cannot be empty or spaces");
            return;
        }

        Boolean isValid = model.getCredentials(username, password);
        if (!isValid) {
            lblError.setText("User does not exist!");
            return;
        }
        try {
            if (model.getAdmin()) {
                System.out.println(model.getFirstName() + " " + model.getLastName() + " has logged in successfully as a User");
                this.txtUsername.clear();
                this.txtPassword.clear();
                this.lblError.setText("");
                clear();
                BookViewController.setUserId(model.getId());
                controller.loadScreen(LibrarySystem.screen7ID, LibrarySystem.screen7File);
                controller.setScreen(LibrarySystem.screen7ID);

            } else {
                clear();
                BookViewController.setUserId(model.getId());
                controller.loadScreen(LibrarySystem.screen7ID, LibrarySystem.screen7File);
                controller.setScreen(LibrarySystem.screen7ID);
            }


        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }


    public void goHome() {
        clear();
        controller.setScreen(LibrarySystem.screen1ID);
    }

    public void userSignUp() {
        clear();
        controller.setScreen(LibrarySystem.screen3ID);
    }

    public void clear() {
        this.txtUsername.clear();
        this.txtPassword.clear();
        this.lblError.setText("");
    }
}
