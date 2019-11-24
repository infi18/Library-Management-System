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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BooksModel;
import models.UserModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookViewController implements Initializable, ControlledScreen {

    static int userId;
    private BooksModel booksModel;
    private UserModel userModel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton btnViewUsers;

    @FXML
    private TableView<BooksModel> booksView;

    @FXML
    private TableColumn<BooksModel, Integer> bookId;

    @FXML
    private TableColumn<BooksModel, String> bookTitle;

    @FXML
    private TableColumn<BooksModel, String> bookAuthor;

    @FXML
    private TableColumn<BooksModel, Integer> bookYear;

    @FXML
    private TableColumn<BooksModel, String> bookISBN;

    @FXML
    private TableColumn<BooksModel, Integer> bookQuantity;

    @FXML
    private TableColumn<BooksModel, JFXButton> bookDetails;

    public BookViewController() {
        booksModel = new BooksModel();
        userModel = new UserModel();
    }

    ScreensController controller;

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
        System.out.println("[LOG] Inside Books View Controller");
        initTable();
        UserModel model = userModel.getUserForId(userId);
        if (!model.getAdmin()) {
            btnViewUsers.setVisible(false);
        }
    }

    public void initTable() {
        initColumns();
        loadBooks();
    }

    public void initColumns() {
        bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        bookYear.setCellValueFactory(new PropertyValueFactory<>("bookYear"));
        bookISBN.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        bookQuantity.setCellValueFactory(new PropertyValueFactory<>("bookQuantity"));
        bookDetails.setCellValueFactory(new PropertyValueFactory<>("bookDetails"));
    }

    public void loadBooks() {
        ObservableList<BooksModel> observableBooksList = FXCollections.observableArrayList();
        List<BooksModel> bookList = booksModel.getAllBooks();
        bookList.forEach(book -> {
            JFXButton detailsButton = new JFXButton("Details");
            detailsButton.setButtonType(JFXButton.ButtonType.RAISED);
            detailsButton.setPrefSize(100, 30);
            detailsButton.setStyle("-fx-background-color:  #09ba19");
            detailsButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    BookDetailsController.setBookId(book.getBookId());
                    BookDetailsController.setUserId(userId);
                    controller.loadScreen(LibrarySystem.screen8ID, LibrarySystem.screen8File);
                    controller.setScreen(LibrarySystem.screen8ID);
                }
            });
            book.setBookDetails(detailsButton);
        });
        observableBooksList.addAll(bookList);
        booksView.setItems(observableBooksList);
    }

    public void addBook() {

    }

    public void searchBook() {

    }

    public void logout() {
        clear();
        System.out.println("Logging Out");
        controller.unloadScreen(LibrarySystem.screen7ID);
        controller.setScreen(LibrarySystem.screen1ID);

    }

    public void viewUsers() {
        searchField.clear();
        UserViewController.setUserId(userId);
        controller.loadScreen(LibrarySystem.screen5ID, LibrarySystem.screen5File);
        controller.unloadScreen(LibrarySystem.screen7ID);
        controller.setScreen(LibrarySystem.screen5ID);
    }

    public void clear() {
        userId = 0;
    }
}


