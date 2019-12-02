package controllers;
/**
 *
 * @author Siddhi Naik
 * @since 2019-11-16
 */

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * UserDetailsController acts as a Controller of the mvc framework
 * It controls screen with all user's details that admin can modify/delete
 * implements initializable, controlledScreen
 */

public class UserDetailsController implements Initializable, ControlledScreen {

    /**
     * all the required fields are initialized
     * all the required models  for retriving data are called
     */

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

    /**
     *The initialize function initializes the user details screen
     */
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

    /**
     *The adduser function
     * admin can add user to the system using this function
     */
    public void addUser() {
        this.lblError.setText("");
        if (!validate() || !validateEmail()) {
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

    /**
     * The deleteuser function
     * admin can delete user with this function
     */
    public void deleteUser() {
        this.lblError.setText("");
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

    /**
     * The modifyUser function
     * admin can modify user details using this function
     */
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


    /**
     * The validate function
     * validate function checks for validations
     * @returns boolean value
     */

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

    /**
     * The validateEmail function
     * Checks for email validation
     * @return boolean value
     */

    public Boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email.getText());
        if (m.find() && m.group().equals(email.getText())) {
            return true;
        } else {
            this.lblError.setText("Enter a valid email !!");
            return false;
        }
    }

    /**The logout function
     * logs the admin  out of the system
     */

    public void logout() {
        clear();
        System.out.println("Logging Out");
        UserViewController.setUserId(0);
        BookViewController.setUserId(0);
        controller.unloadScreen(LibrarySystem.screen6ID);
        controller.setScreen(LibrarySystem.screen1ID);
    }

    /**
     * The goBack function
     * goback takes user back to previous page
     */
    public void goBack() {
        clear();
        System.out.println("Back to Books View from Users");
        controller.loadScreen(LibrarySystem.screen5ID, LibrarySystem.screen5File);
        controller.unloadScreen(LibrarySystem.screen6ID);
        controller.setScreen(LibrarySystem.screen5ID);
    }

    /**
     * The setDetailsForId  function
     * sets the value of user after the addition/deletion/modification of details by the admin
     * initials it loads the value of the selected particular user
     */
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


    /**
     * The setAdmin function
     * sets any user to admin if selected by current admin
     */

    public void setAsAdmin() {
        isAdmin.setSelected(true);
        notAdmin.setSelected(false);
    }

    /**
     * The setAsUser function
     * sets inserted user as a normal user
     *
     */

    public void setAsUser() {
        isAdmin.setSelected(false);
        notAdmin.setSelected(true);
    }

    /**
     * clears all fields
     */
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
