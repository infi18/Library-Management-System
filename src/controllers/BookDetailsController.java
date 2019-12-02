package controllers;

import Dao.AlertDao;
import application.LibrarySystem;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import interfaces.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BooksModel;
import models.CheckoutModel;
import models.ReviewModel;
import models.UserModel;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * BooksDetailsController acts as a Controller of the mvc framework
 * Controls the Book's Detail Screen
 * User/Admin can view all the books
 * Implements initializable, ControlledScreen
 */

public class BookDetailsController implements Initializable, ControlledScreen {

    /**
     * all the required fields are initialized
     * all the required models for data retrival are called
     */

    static int userId;
    static int bookId;
    static boolean newBook = false;
    ScreensController controller;
    private BooksModel booksModel;
    private UserModel userModel;
    private ReviewModel reviewModel;
    private CheckoutModel checkoutModel;

    public BookDetailsController() {
        booksModel = new BooksModel();
        userModel = new UserModel();
        reviewModel = new ReviewModel();
        checkoutModel = new CheckoutModel();
    }

    public static void setNewBook(boolean newBook) {
        BookDetailsController.newBook = newBook;
    }

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField year;
    @FXML
    private JFXTextField isbn;
    @FXML
    private JFXTextField quantity;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnModify;

    @FXML
    private JFXButton btnReview;

    @FXML
    private JFXButton btnCheckout;

    @FXML
    private JFXButton btnCheckin;

    @FXML
    private JFXTextArea txtReview;

    @FXML
    private TableView<ReviewModel> reviewList;

    @FXML
    private TableColumn<ReviewModel, String> review;

    @FXML
    private TableColumn<ReviewModel, JFXButton> deleteReview;

    @FXML
    private Label lblError;

    @FXML
    private Label lblErrorReview;

    @FXML
    private Label lblErrorCheckout;


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

    /**
     * The initializefunction
     * initializing the page and loading all the content
     */
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Inside book details controller");
        this.title.setLabelFloat(true);
        this.author.setLabelFloat(true);
        this.year.setLabelFloat(true);
        this.isbn.setLabelFloat(true);
        this.txtReview.setLabelFloat(true);
        setDetailsForUserId();
        if (!newBook) {
            this.btnAdd.setDisable(true);         //if book is not new add button disabled
            this.btnReview.setVisible(true);
            this.txtReview.setVisible(true);
            this.reviewList.setVisible(true);
            this.setDetailsForId();
            initReviewTable();
        } else {
            this.btnCheckin.setVisible(false);     //Checkin button initially disabled
            this.btnCheckout.setVisible(false);
            this.btnDelete.setDisable(true);    //delete button disabled for the user
            this.btnModify.setDisable(true);    //modify button disabled for the user
            this.btnReview.setVisible(false);
            this.txtReview.setVisible(false);
            this.reviewList.setVisible(false);
            this.title.setDisable(false);
            this.year.setDisable(false);
            this.isbn.setDisable(false);
            this.quantity.setDisable(false);
            this.author.setDisable(false);
        }

    }

    /**
     * The setDetailsForUserId function
     * below is functionality of admin on bookdetails page
     * Admin cannot checkin, checkout book hence these functions are disabled to the admin
     */

    public void setDetailsForUserId() {
        UserModel model = userModel.getUserForId(userId);
        if (model.getAdmin()) {
            this.btnCheckin.setVisible(false);
            this.btnCheckout.setVisible(false);
            this.title.setDisable(false);
            this.year.setDisable(false);
            this.isbn.setDisable(false);
            this.quantity.setDisable(false);
            this.author.setDisable(false);
            this.btnDelete.setVisible(true);
            this.btnModify.setVisible(true);
            this.deleteReview.setVisible(true);
            initReviewTable();
        } else {
            this.btnCheckin.setVisible(true);
            this.btnCheckout.setVisible(true);
            this.btnDelete.setVisible(false);
            this.btnModify.setVisible(false);
            this.title.setDisable(true);
            this.year.setDisable(true);
            this.isbn.setDisable(true);
            this.quantity.setDisable(true);
            this.author.setDisable(true);
            this.deleteReview.setVisible(false);
        }
    }

    /**
     *The setDetailsForId function
     * Book details cannot be edited by user, user can only add book
     * if user succeeds in book checkout checkin function becomes available
     */

    public void setDetailsForId() {
        BooksModel book = booksModel.getBookForId(bookId);
        title.setText(book.getBookTitle());
        author.setText(book.getBookAuthor());
        year.setText(String.valueOf(book.getBookYear()));
        isbn.setText(book.getBookISBN());
        quantity.setText(String.valueOf(book.getBookQuantity()));
        CheckoutModel checkout = checkoutModel.getCheckoutsForUser(userId, bookId);
        if (checkout != null) {
            this.btnCheckout.setDisable(true);
            this.btnCheckin.setDisable(false);
        } else {
            this.btnCheckout.setDisable(false);
            this.btnCheckin.setDisable(true);
        }
    }

    /**
     * The checkout function
     * checkout function defined
     */
    public void checkout() {
        this.lblErrorCheckout.setText("");
        Integer currentQuantity = Integer.parseInt(this.quantity.getText());
        if (currentQuantity == 0) {
            this.lblErrorCheckout.setText("No further quantity available");
            return;
        }
        currentQuantity--;
        Boolean updateResult = booksModel.updateBookQuantity(bookId, currentQuantity);
        if (!updateResult) {
            this.lblErrorCheckout.setText("Error checking out try again later");
            return;
        }
        Boolean result = checkoutModel.addCheckOutRecord(bookId, userId);
        if (!result) {
            this.lblErrorCheckout.setText("Error checking out try again later");
            return;
        }
        this.quantity.setText(String.valueOf(currentQuantity));
        this.btnCheckout.setDisable(true);
        this.btnCheckin.setDisable(false);
    }

    /**
     * The checkin function
     * checkin fucntion defined
     */
    public void checkin() {
        this.lblErrorCheckout.setText("");
        Integer currentQuantity = Integer.parseInt(this.quantity.getText());
        currentQuantity++;
        Boolean updateResult = booksModel.updateBookQuantity(bookId, currentQuantity);
        if (!updateResult) {
            this.lblErrorCheckout.setText("Error checking out try again later");
            return;
        }
        Boolean result = checkoutModel.deleteCheckout(userId, bookId);
        if (!result) {
            this.lblErrorCheckout.setText("Error checking out try again later");
            return;
        }
        this.quantity.setText(String.valueOf(currentQuantity));
        this.btnCheckout.setDisable(false);
        this.btnCheckin.setDisable(true);
    }

    /**
     * The delete function
     * admin can delete book using this function
     *
     */
    public void delete() {
        this.lblError.setText("");
        Boolean result = booksModel.deleteBook(bookId);
        if (!result) {
            this.lblError.setText("There is an error please try again later");
            return;
        }
        System.out.println("Book delete successful");
        AlertDao.Display("Delete Book", "Book successfully deleted");
        clear();
        controller.loadScreen(LibrarySystem.screen7ID, LibrarySystem.screen7File);
        controller.unloadScreen(LibrarySystem.screen8ID);
        controller.setScreen(LibrarySystem.screen7ID);
    }

    /**
     * The update function
     * update function
     * only admin can perform this
     */
    public void update() {
        this.lblError.setText("");
        if (!validate()) {
            return;
        }
        Boolean result = booksModel.updateBook(bookId, this.title.getText(), this.author.getText(),
                this.isbn.getText(), Integer.parseInt(this.year.getText()), Integer.parseInt(this.quantity.getText()));

        if (!result) {
            this.lblError.setText("There is an error please try again later");
            return;
        }
        System.out.println("Book modify successful");
        AlertDao.Display("Modify Book", "Book successfully modified");
    }


    /**
     * The addReview function
     * adds review to the database
     */
    public void addReview() {
        this.lblErrorReview.setText("");
        if (this.txtReview.textProperty().isEmpty().get()) {
            this.lblErrorReview.setText("Review Field cannot be empty");
            return;
        }
        Boolean result = reviewModel.addReview(bookId, userId, this.txtReview.getText());
        if (!result) {
            this.lblErrorReview.setText("Error adding review please try again");
            return;
        }
        this.txtReview.clear();
        loadReviews();
    }

    /**
     * The removeReview function
     * only admin can use this function
     * @param reviewId The reviewId is used for the same
     */
    public void removeReview(int reviewId) {
        this.lblErrorReview.setText("");
        Boolean result = reviewModel.deleteReview(reviewId);
        if (!result) {
            this.lblErrorReview.setText("Error deleting Review please try again later");
            return;
        }
        loadReviews();
    }

    /**
     *The initReviewTable function initializes table with review  details
     */

    public void initReviewTable() {
        initReviewColumns();
        loadReviews();
    }

    /**
     *The initReview function initializes column with review details
     */
    public void initReviewColumns() {
        this.review.setCellValueFactory(new PropertyValueFactory<>("BookReview"));
        this.deleteReview.setCellValueFactory(new PropertyValueFactory<>("deleteReview"));
    }


    /**
     *The loadReview function loads table with reviews
     */
    public void loadReviews() {
        ObservableList<ReviewModel> reviewModelObservableList = FXCollections.observableArrayList();
        List<ReviewModel> reviewModelList = reviewModel.getReviewsForBook(bookId);
        reviewModelList.forEach(reviewModel1 -> {
            JFXButton deleteButton = new JFXButton("Delete");
            deleteButton.setButtonType(JFXButton.ButtonType.RAISED);
            deleteButton.setPrefSize(100, 30);
            deleteButton.setStyle("-fx-background-color:  #09ba19");
            deleteButton.setOnAction(event -> {
                removeReview(reviewModel1.getReview_id());
            });
            reviewModel1.setDeleteReview(deleteButton);
        });
        reviewModelList.sort(Comparator.comparing(ReviewModel::getReview_id).reversed());
        reviewModelObservableList.addAll(reviewModelList);
        reviewList.setItems(reviewModelObservableList);
    }

    /**
     *
     * The addBook function
     * both user and admin can add books using this function
     */
    public void addBook() {
        this.lblError.setText("");
        if (!validate()) {
            return;
        }
        BooksModel existingBook = booksModel.getExistingBook(this.isbn.getText());
        if (existingBook != null) {
            this.lblError.setText("Book with ISBN already exists!");
            return;
        }

        Boolean result = booksModel.addBook(this.title.getText(), this.author.getText(), this.isbn.getText(), Integer.parseInt(this.year.getText()), Integer.parseInt(this.quantity.getText()));
        if (!result) {
            this.lblError.setText("There is an error please try again later");
            return;
        }
        System.out.println("Book add successful");
        AlertDao.Display("Add Book", "Book successfully added");
        clear();
        controller.loadScreen(LibrarySystem.screen7ID, LibrarySystem.screen7File);
        controller.unloadScreen(LibrarySystem.screen8ID);
        controller.setScreen(LibrarySystem.screen7ID);
    }

    /**
     * The validate function
     * checks for validation
     */


    public Boolean validate() {
        if (this.title.textProperty().isEmpty()
                .or(this.author.textProperty().isEmpty())
                .or(this.year.textProperty().isEmpty())
                .or(this.isbn.textProperty().isEmpty())
                .or(this.quantity.textProperty().isEmpty())
                .get()
        ) {
            this.lblError.setText("All the fields are required");
            return false;
        }
        try {
            int val = Integer.parseInt(this.year.getText());
            int val2 = Integer.parseInt(this.quantity.getText());
        } catch (Exception e) {
            this.lblError.setText("Year and quantity needs to be integers");
            return false;
        }
        return true;
    }

    /**
     * The logout function
     * logs user/admin out of the system
     */

    public void logout() {
        clear();
        System.out.println("Logging Out");
        BookViewController.setUserId(0);
        controller.unloadScreen(LibrarySystem.screen8ID);
        controller.setScreen(LibrarySystem.screen1ID);
    }

    /**
     * The goback  function takes user back to previous page
     */
    public void goBack() {
        clear();
        System.out.println("Back to Books View from Book Details");
        controller.loadScreen(LibrarySystem.screen7ID, LibrarySystem.screen7File);
        controller.unloadScreen(LibrarySystem.screen8ID);
        controller.setScreen(LibrarySystem.screen7ID);
    }

    /**
     * the clear function
     * clears all fields
     */
    public void clear() {
        userId = 0;
        bookId = 0;
        newBook = false;
        this.lblError.setText("");
        this.lblErrorReview.setText("");
        this.lblErrorCheckout.setText("");
        this.title.clear();
        this.author.clear();
        this.year.clear();
        this.isbn.clear();
        this.quantity.clear();
    }

}
