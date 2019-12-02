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

/**
 * AdminLoginController acts as a Controller for the admin login view
 * Controls the admin's login screen
 * Implements initializabl, ControlledScreen
 *
  */

public class AdminLoginController implements Initializable, ControlledScreen {

    /**
     * all required fields are initialized
     * all the required models for retriving data are called
     */
    @FXML
    private JFXTextField adminUsername;

    @FXML
    private JFXPasswordField adminPassword;

    @FXML
    private Label lblError;

    private UserModel model;

    ScreensController controller;

    public AdminLoginController() {
        model = new UserModel();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    /**
     * The initialize function
     * loads admin login screen
     */
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Inside the Login Controller for Admin");
        this.adminUsername.setLabelFloat(true);
        this.adminPassword.setLabelFloat(true);
        this.lblError.setText("");
    }

    /**
     * The loginAdmin function
     * below function checks for credentials entered
     * if the values match or not
     * if the credentials are present in database or not
     */

    public void loginAdmin() {
        String username = this.adminUsername.getText();
        String password = this.adminPassword.getText();
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
                System.out.println(model.getFirstName() + " " + model.getLastName() + " has logged in successfully as an Admin");
                this.adminPassword.clear();
                this.adminUsername.clear();
                this.lblError.setText("");
                clear();
                BookViewController.setUserId(model.getId());
                controller.loadScreen(LibrarySystem.screen7ID, LibrarySystem.screen7File);
                controller.setScreen(LibrarySystem.screen7ID);

            } else {
                lblError.setText("User is not a Admin");
                return;
            }


        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }

    /**
     * The clear function
     *  clears all the  fields
     */

    public void clear() {
        this.adminPassword.clear();
        this.adminUsername.clear();
        this.lblError.setText("");
    }


    /**
     * The goHome function
     * if login is successful loads home screen
     */
    public void goHome() {
        clear();
        controller.setScreen(LibrarySystem.screen1ID);
    }
}
