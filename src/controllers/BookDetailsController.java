package controllers;

import application.LibrarySystem;
import com.jfoenix.controls.JFXTextField;
import interfaces.ControlledScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class BookDetailsController implements Initializable, ControlledScreen {

    static int userId;
    static int bookId;
    ScreensController controller;

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField Author;
    @FXML
    private JFXTextField Year;
    @FXML
    private JFXTextField ISBN;

    public static void setUserId(int Id) {
        userId = Id;
        System.out.println("Book Details for User " + userId);
    }

    public static void setBookId(int Id) {
        bookId = Id;
        System.out.println("Book Details for book " + bookId);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Inside book details controller");
    }

    public static void init() {

    }

    public void checkout() {
    }

    public void checkin() {
    }

    public void delete() {
    }

    public void update() {
    }

    public void addReview() {
    }

    public void logout() {
        clear();
        System.out.println("Logging Out");
        controller.unloadScreen(LibrarySystem.screen8ID);
        controller.setScreen(LibrarySystem.screen1ID);
    }

    public void goBack() {
    }

    public void clear() {
        userId = 0;
        bookId = 0;
    }

}
