package controllers;

import application.LibrarySystem;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import interfaces.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BooksModel;
import models.UserModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UserViewController implements Initializable, ControlledScreen {
    static int userId;
    ScreensController controller;
    private UserModel userModel;

    public UserViewController() {
        userModel = new UserModel();
    }

    @FXML
    private JFXTextField searchText;

    @FXML
    private Label lblError;

    @FXML
    private TableView<UserModel> userView;

    @FXML
    private TableColumn<UserModel, String> firstName;

    @FXML
    private TableColumn<UserModel, String> lastName;

    @FXML
    private TableColumn<UserModel, String> email;

    @FXML
    private TableColumn<UserModel, String> password;

    @FXML
    private TableColumn<UserModel, String> phone;

    @FXML
    private TableColumn<UserModel, JFXButton> details;

    public static void setUserId(int Id) {
        userId = Id;
        System.out.println("Welcome id " + userId);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.searchText.setLabelFloat(true);
        System.out.println("[LOG] Intialized");
        initTable();
    }

    public void initTable() {
        initColumns();
        loadUsers();
    }

    public void initColumns() {
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        details.setCellValueFactory(new PropertyValueFactory<>("details"));
    }

    public void loadUsers() {
        ObservableList<UserModel> observableUsersList = FXCollections.observableArrayList();
        List<UserModel> usersList = userModel.getAllUsers();
        usersList.forEach(user -> {
            JFXButton detailsButton = new JFXButton("Details");
            detailsButton.setButtonType(JFXButton.ButtonType.RAISED);
            detailsButton.setPrefSize(100, 30);
            detailsButton.setStyle("-fx-background-color:  #09ba19");
            detailsButton.setOnAction(event -> {
                UserDetailsController.setUserId(user.getId());
                controller.loadScreen(LibrarySystem.screen6ID, LibrarySystem.screen6File);
                controller.unloadScreen(LibrarySystem.screen5ID);
                controller.setScreen(LibrarySystem.screen6ID);
            });
            user.setDetails(detailsButton);
        });
        observableUsersList.addAll(usersList);
        userView.setItems(observableUsersList);
    }

    public JFXButton createButton(String name) {
        JFXButton detailsButton = new JFXButton(name);
        detailsButton.setButtonType(JFXButton.ButtonType.RAISED);
        detailsButton.setPrefSize(100, 30);
        detailsButton.setStyle("-fx-background-color:  #09ba19");
        return detailsButton;
    }

    public void addUser() {
        UserDetailsController.setUserId(userId);
        UserDetailsController.setNewUser(true);
        controller.loadScreen(LibrarySystem.screen6ID, LibrarySystem.screen6File);
        controller.unloadScreen(LibrarySystem.screen5ID);
        controller.setScreen(LibrarySystem.screen6ID);
    }

    public void searchUser() {
        this.lblError.setText("");
        if (this.searchText.textProperty().isEmpty().get()) {
            this.lblError.setText("SearchField must not be empty");
            return;
        }
        String search = this.searchText.getText();
        List<UserModel> userList = userModel.getAllUsers();
        if (userList.isEmpty()) {
            this.lblError.setText("No users exist in DB!");
        }

        List<UserModel> filteredList = userList.stream().filter(user -> {
            return user.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                    user.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                    user.getEmailId().toLowerCase().contains(search.toLowerCase());
        }).collect(Collectors.toList());

        ObservableList<UserModel> observableUsersList = FXCollections.observableArrayList();
        filteredList.forEach(user -> {
            JFXButton detailsButton = createButton("Details");
            detailsButton.setOnAction(event -> {
                UserDetailsController.setUserId(user.getId());
                controller.loadScreen(LibrarySystem.screen6ID, LibrarySystem.screen6File);
                controller.unloadScreen(LibrarySystem.screen5ID);
                controller.setScreen(LibrarySystem.screen6ID);
            });
            user.setDetails(detailsButton);
        });
        observableUsersList.addAll(filteredList);
        userView.setItems(observableUsersList);
    }

    public void goBack() {
        clear();
        System.out.println("Back to Books View from Users");
        controller.loadScreen(LibrarySystem.screen7ID, LibrarySystem.screen7File);
        controller.unloadScreen(LibrarySystem.screen5ID);
        controller.setScreen(LibrarySystem.screen7ID);
    }

    public void logout() {
        clear();
        System.out.println("Logging Out");
        controller.unloadScreen(LibrarySystem.screen5ID);
        controller.setScreen(LibrarySystem.screen1ID);
    }

    public void clear() {
        userId = 0;
        this.searchText.clear();
        this.lblError.setText("");
    }

}
