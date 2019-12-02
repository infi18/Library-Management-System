package controllers;
/**
 *
 * @author Siddhi Naik
 * @since 2019-11-16
 */

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
 * UserLoginController controls user login screen as per the mvc framework
 * The login screen is displayed to the user with option to signup to the system
 *
 *
 */

public class UserLoginController implements Initializable, ControlledScreen {
    /**
     * all the required fields are initialized
     * all the required  models for retriving data are called
     */

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

    ScreensController controller; //screens controller

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    /**
     * The initialize function
     * initializes login screen
     */
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inside the Login Controller for User");
        this.txtUsername.setLabelFloat(true);
        this.txtPassword.setLabelFloat(true);
        this.lblError.setText("");
    }

    /**
     * the loginuser function
     * checks credentials for login
     * it checks if there is empty space
     * or do the credentials match
     */
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

    /**
     * The goHome function
     * if user succeeds in logging in the displays next screen
     */
    public void goHome() {
        clear();
        controller.setScreen(LibrarySystem.screen1ID);
    }

    /**
     * the userSignup function
     * if user clicks signup button
     * take user to sign up page
     */
    public void userSignUp() {
        clear();
        controller.setScreen(LibrarySystem.screen3ID);
    }

    /**
     * the clear function
     * clear up all fields
     */
    public void clear() {
        this.txtUsername.clear();
        this.txtPassword.clear();
        this.lblError.setText("");
    }
}
