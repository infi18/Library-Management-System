package controllers;

import application.LibrarySystem;
import application.Main;
import interfaces.ControlledScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.LoginModel;
import models.UserModel;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable, ControlledScreen {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblError;

    private UserModel model;

    ScreensController controller;


    public UserController() {
        model = new UserModel();
    }


    public void login() {

        lblError.setText("");
        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();

        // Validations
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

        // authentication check
        checkCredentials(username, password);

    }

    public void checkCredentials(String username, String password) {
        Boolean isValid = model.getCredentials(username, password);
        if (!isValid) {
            lblError.setText("User does not exist!");
            return;
        }
        try {
            AnchorPane root;
            if (model.getAdmin() && isValid) {
                // If user is admin, inflate admin view
                System.out.println("U have logged in successfully");
                controller.setScreen(LibrarySystem.screen4ID);

            } else {
                // If user is customer, inflate customer view
//                root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/ClientView.fxml"));
                // ***Set user ID acquired from db****
                int user_id = model.getId();
                ClientController.setUserid(user_id);
                controller.setScreen(LibrarySystem.screen2ID);
            }


        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[LOG] Intialized");
    }
}
