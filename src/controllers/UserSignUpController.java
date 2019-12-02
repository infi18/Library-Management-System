package controllers;
/**
 * @author : Siddhi Naik
 * UserSignUpController
 * The user signup controllers are defined here
 *
 */

import Dao.AlertDao;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * All required fields are defined
 * Extends ControlledScreen, Framework
 * Acts like a Controller in the mvc framework
 */

public class UserSignUpController implements Initializable, ControlledScreen {

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confirmPassword;

    @FXML
    private JFXTextField emailId;

    @FXML
    private JFXTextField phone;

    @FXML
    private Label lblError;

    private UserModel model;

    public UserSignUpController() {
        model = new UserModel();
    }

    ScreensController controller;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    /**
     *  Loads SignUp page initals
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Sign Up Page Intialized.");
        this.firstName.setLabelFloat(true);
        this.lastName.setLabelFloat(true);
        this.password.setLabelFloat(true);
        this.confirmPassword.setLabelFloat(true);
        this.emailId.setLabelFloat(true);
        this.phone.setLabelFloat(true);
        this.lblError.setText("");
    }

    /**
     * The sigup function
     * The user can have credentials to access sysytem by signing up
     * the signup fucntion checks for various validations before adding user to the system
     */
    public void signUp() {
        if (!validate() || !validateEmail())    //calls validation function
        {
            return;
        }

        UserModel existingUser = model.getUser(this.emailId.getText());
        if (existingUser != null) {
            this.lblError.setText("User already exists! Try logging In");
            return;
        }
        Boolean result = model.addUser(this.firstName.getText(), this.lastName.getText(), this.emailId.getText(), this.phone.getText(), this.password.getText(),Boolean.FALSE);
        if (!result) {
            this.lblError.setText("There is an error please try again later");
            return;
        }
        System.out.println("User sign up successful");
        AlertDao.Display("User Sign Up", "User sign up successful ! Login and have fun");
        clear();
        controller.setScreen(LibrarySystem.screen2ID);
    }

    /**
     * The validate function
     * validate function checks for validations
     * @returns boolean value
     */

    public Boolean validate() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailId.getText());

        if (this.firstName.textProperty().isNotEmpty().not()
                .or(this.lastName.textProperty().isNotEmpty().not())
                .or(this.emailId.textProperty().isNotEmpty().not())
                .or(this.password.textProperty().isNotEmpty().not())
                .or(this.confirmPassword.textProperty().isNotEmpty().not())
                .or(this.phone.textProperty().isNotEmpty().not())
                .get()
        ) {
            this.lblError.setText("All the fields are required");
            return false;
        } else if ( !this.password.textProperty().get().equals(this.confirmPassword.textProperty().get())){
            this.lblError.setText("Password and Confirm Password do not match");
            return false;
        }

       return true;

    }

    /**
     * The validateEmail function
     * Checks for email validation
     * @return boolean value
     */

    public Boolean validateEmail(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailId.getText());
      if( m.find() && m.group().equals(emailId.getText()))
        {
            return true;
        }
      else {
          this.lblError.setText("Enter a valid email !!");
          return false;
      }

    }

    /**
     * The login function
     * clears all fields loads login screen
     */
    public void goLogin() {
        clear();
        controller.setScreen(LibrarySystem.screen2ID);
    }

    /**
     * The clear function
     * clears all fields
     */

    public void clear() {
        this.firstName.clear();
        this.lastName.clear();
        this.emailId.clear();
        this.phone.clear();
        this.password.clear();
        this.confirmPassword.clear();
        this.lblError.setText("");
    }
}
