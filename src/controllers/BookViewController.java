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

/**
 * The BookView Controller Controls the book view
 * displays all the books to user/admin along with other functionality
 *implements Initializable, ControlledScreen
 */

public class BookViewController implements Initializable, ControlledScreen {

    /**
     * all the required fields initialized
     * all the required models  for data retrival are called
     */

    static int userId;
    private BooksModel booksModel;
    private UserModel userModel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private Label lblError;

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

    /**
     * The initializable function
     * screen with book details is initialized
     */
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[LOG] Inside Books View Controller");
        initTable();
        UserModel model = userModel.getUserForId(userId);
        if (!model.getAdmin()) {
            btnViewUsers.setVisible(false);
        }
    }

    /**
     * The initTable Function
     * book table is set with values
     */
    public void initTable() {
        initColumns();
        loadBooks();
    }

    /**
     * The initColumns function
     * here columns are initialized with appropriate book values
     */

    public void initColumns() {
        bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        bookYear.setCellValueFactory(new PropertyValueFactory<>("bookYear"));
        bookISBN.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        bookQuantity.setCellValueFactory(new PropertyValueFactory<>("bookQuantity"));
        bookDetails.setCellValueFactory(new PropertyValueFactory<>("bookDetails"));
    }


    /**
     * The loadBooks fucntion
     * here book values retirived using BookModel
     */

    public void loadBooks() {
        ObservableList<BooksModel> observableBooksList = FXCollections.observableArrayList();
        List<BooksModel> bookList = booksModel.getAllBooks();
        bookList.forEach(book -> {
            JFXButton detailsButton = createButton("Details");
            detailsButton.setOnAction(event -> {
                this.searchField.clear();
                this.lblError.setText("");
                BookDetailsController.setBookId(book.getBookId());
                BookDetailsController.setUserId(userId);
                controller.loadScreen(LibrarySystem.screen8ID, LibrarySystem.screen8File);
                controller.unloadScreen(LibrarySystem.screen7ID);
                controller.setScreen(LibrarySystem.screen8ID);
            });
            book.setBookDetails(detailsButton);
        });
        observableBooksList.addAll(bookList);
        booksView.setItems(observableBooksList);
    }

    /**
     *The createButton Fucntion
     * @param name The name string is passed for button
     * @return button that onclick takes to page with the specific book detail
     */
    public JFXButton createButton(String name) {
        JFXButton detailsButton = new JFXButton(name);
        detailsButton.setButtonType(JFXButton.ButtonType.RAISED);
        detailsButton.setPrefSize(100, 30);
        detailsButton.setStyle("-fx-background-color:  #09ba19");
        return detailsButton;
    }

    /**
     * The addBook fucntion
     * takes user/admin to screen where new book can be added to the system
     */
    public void addBook() {
        BookDetailsController.setUserId(userId);
        BookDetailsController.setNewBook(true);
        controller.loadScreen(LibrarySystem.screen8ID, LibrarySystem.screen8File);
        controller.unloadScreen(LibrarySystem.screen7ID);
        controller.setScreen(LibrarySystem.screen8ID);
    }

    /**
     * the searchBook function
     * user/admin can search for a particular book with this button
     */
    public void searchBook() {
        this.lblError.setText("");
        if (this.searchField.textProperty().isEmpty().get()) {
            this.lblError.setText("Search Field must not be empty");
            return;
        }
        String search = this.searchField.getText();
        List<BooksModel> bookList = booksModel.getAllBooks();
        if (bookList.isEmpty()) {
            this.lblError.setText("No books exist in DB!");
        }
        List<BooksModel> filteredList = bookList.stream().filter(book -> {
            return book.getBookTitle().toLowerCase().contains(search.toLowerCase()) ||
                    book.getBookAuthor().toLowerCase().contains(search.toLowerCase()) ||
                    String.valueOf(book.getBookYear()).contains(search.toLowerCase()) ||
                    book.getBookISBN().toLowerCase().contains(search.toLowerCase());
        }).collect(Collectors.toList());
        ObservableList<BooksModel> observableBooksList = FXCollections.observableArrayList();
        filteredList.forEach(book -> {
            JFXButton detailsButton = createButton("Details");
            detailsButton.setOnAction(event -> {
                this.searchField.clear();
                this.lblError.setText("");
                BookDetailsController.setBookId(book.getBookId());
                BookDetailsController.setUserId(userId);
                controller.loadScreen(LibrarySystem.screen8ID, LibrarySystem.screen8File);
                controller.unloadScreen(LibrarySystem.screen7ID);
                controller.setScreen(LibrarySystem.screen8ID);
            });
            book.setBookDetails(detailsButton);
        });
        observableBooksList.addAll(filteredList);
        booksView.setItems(observableBooksList);

    }

    /**
     * The logout fucntion
     * logs user/admin out of the system
     */
    public void logout() {
        clear();
        System.out.println("Logging Out");
        controller.unloadScreen(LibrarySystem.screen7ID);
        controller.setScreen(LibrarySystem.screen1ID);

    }

    /**
     * The viewUser fucntion
     * Admin can view all users by clicking on this button
     * only visible to the admin
     */
    public void viewUsers() {
        searchField.clear();
        UserViewController.setUserId(userId);
        controller.loadScreen(LibrarySystem.screen5ID, LibrarySystem.screen5File);
        controller.unloadScreen(LibrarySystem.screen7ID);
        controller.setScreen(LibrarySystem.screen5ID);
    }

    /**
     * The clear function
     * clears all fields
     */

    public void clear() {

        userId = 0;
        this.searchField.clear();
        this.lblError.setText("");
    }
}


