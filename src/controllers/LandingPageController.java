package controllers;

import application.LibrarySystem;
import com.jfoenix.controls.JFXButton;
import interfaces.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private JFXButton user_login;

    @FXML
    private JFXButton admin_login;


    /**
     * @param screenPage This will set the screen to the main screen.
     */
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("The program has successfully reached the Landing Page");

    }


    /**
     * @param event the login button event where it loads the next screen upon
     *              button clicked.
     */
    @FXML
    void admin_login(ActionEvent event) {
        myController.setScreen(LibrarySystem.screen4ID);

    }

    /**
     * @param event event the login button event where it loads the next screen upon
     *              button clicked.
     */
    @FXML
    void user_login(ActionEvent event) {
        myController.setScreen(LibrarySystem.screen2ID);
    }

}
