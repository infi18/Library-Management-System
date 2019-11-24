package controllers;

import interfaces.ControlledScreen;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable, ControlledScreen {
    static int userId;
    ScreensController controller;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[LOG] Intialized");
    }

    public static void setUserId(int Id) {
        userId = Id;
        System.out.println("Welcome id " + userId);
    }

}
