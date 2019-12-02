package models;

import Dao.DBConnect;
import com.jfoenix.controls.JFXButton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Siddhi Naik
 * UserModel retives data from the snaik_books table
 * the data is used to add, view, search, modify, delete books
  */
public class BooksModel extends DBConnect {

    /**
     * all the required fields, buttons have been defined below
     */
    private int bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookISBN;
    private int bookYear;
    private int bookQuantity;
    private JFXButton bookDetails;

    public JFXButton getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(JFXButton bookDetails) {
        this.bookDetails = bookDetails;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookId() {
        return bookId;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }


    /**
     * The getAllBooks function retrieves all the books present in database
      * @return
     */

    public List<BooksModel> getAllBooks() {

        String query = "SELECT * FROM snaik_books;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            List<BooksModel> BooksModelList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BooksModel books = new BooksModel();
                books.setBookId(rs.getInt("book_id"));
                books.setBookAuthor(rs.getString("book_author"));
                books.setBookTitle(rs.getString("book_title"));
                books.setBookISBN(rs.getString("book_isbn"));
                books.setBookYear(rs.getInt("book_year"));
                books.setBookQuantity(rs.getInt("book_quantity"));
                BooksModelList.add(books);
            }
            return BooksModelList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    /**
     * The getExistingBook function fetches book with specified isbn
     * it basically checks for the book ig it is present in the database
     * @param isbn
     * @return
     */

    public BooksModel getExistingBook(String isbn) {

        String query = "SELECT * FROM snaik_books WHERE  book_isbn=?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BooksModel books = new BooksModel();
                books.setBookId(rs.getInt("book_id"));
                books.setBookAuthor(rs.getString("book_author"));
                books.setBookTitle(rs.getString("book_title"));
                books.setBookISBN(rs.getString("book_isbn"));
                books.setBookYear(rs.getInt("book_year"));
                books.setBookQuantity(rs.getInt("book_quantity"));
                return books;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * The getBookForId fetches book with specified book_id from the database
     * @param bookId
     * @return
     */
    public BooksModel getBookForId(int bookId) {

        String query = "SELECT * FROM snaik_books WHERE  book_id=?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BooksModel books = new BooksModel();
                books.setBookId(rs.getInt("book_id"));
                books.setBookAuthor(rs.getString("book_author"));
                books.setBookTitle(rs.getString("book_title"));
                books.setBookISBN(rs.getString("book_isbn"));
                books.setBookYear(rs.getInt("book_year"));
                books.setBookQuantity(rs.getInt("book_quantity"));
                return books;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The addBook function
     * Adds Books into the database
     * @return boolean value
     * if user is added successfully it returns true else false
     */
    public Boolean addBook(String bookTitle, String author, String ISBN, int bookYear, int bookQuantity) {
        String state = "Insert INTO snaik_books(book_title,book_author,book_isbn,book_year,book_quantity) Values(?,?,?,?,?);";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setString(1, bookTitle);
            sql.setString(2, author);
            sql.setString(3, ISBN);
            sql.setInt(4, bookYear);
            sql.setInt(5, bookQuantity);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The deleteBook function deletes book with specified book_id
     * @param bookId
     * @return
     */
    public Boolean deleteBook(int bookId) {
        String state = "Delete from snaik_books where book_id=?;";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setInt(1, bookId);
            sql.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * The updateBook function updates book if any of the below specified parameter is modified
     * @param bookId
     * @param bookTitle
     * @param author
     * @param ISBN
     * @param bookYear
     * @param bookQuantity
     * @return
     */
    public Boolean updateBook(int bookId, String bookTitle, String author, String ISBN, int bookYear, int bookQuantity) {
        String state = "UPDATE snaik_books SET book_title=?,book_author=?,book_isbn=?,book_year=?,book_quantity=? WHERE book_id=?; ";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setString(1, bookTitle);
            sql.setString(2, author);
            sql.setString(3, ISBN);
            sql.setInt(4, bookYear);
            sql.setInt(5, bookQuantity);
            sql.setInt(6, bookId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The updateBookQuantity updates the quantity of books present in database for the following parameters
     * @param bookId
     * @param quantity
     * @return
     */
    public Boolean updateBookQuantity(int bookId, int quantity) {
        String state = "UPDATE snaik_books SET book_quantity=? WHERE book_id=?; ";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setInt(1, quantity);
            sql.setInt(2, bookId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

