package models;

import Dao.DBConnect;
import com.jfoenix.controls.JFXButton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksModel extends DBConnect {
    private int bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookISBN;
    private int bookYear;
    private int bookQuantity;
    private JFXButton bookDetails;

    DBConnect conn = null;

    public BooksModel() {
        conn = new DBConnect();
    }

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


    public List<BooksModel> getAllBooks() {

        String query = "SELECT * FROM snaik_books;";
        try (PreparedStatement stmt = conn.getConnection().prepareStatement(query)) {
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


    public List<BooksModel> searchBooks(String searchParameter) {

        String query = "SELECT * FROM snaik_books WHERE  book_title=? or book_author=?;";
        try (PreparedStatement stmt = conn.getConnection().prepareStatement(query)) {
            stmt.setString(1, searchParameter);
            stmt.setString(2, searchParameter);
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


    public Boolean addBook(String bookTitle, String author, String ISBN, int bookYear, int bookQuantity) {
        String state = "Insert INTO snaik_books(book_title,book_author,book_isbn,book_year,book_quantity) Values(?,?,?,?,?);";
        try (PreparedStatement sql = conn.getConnection().prepareStatement(state)) {
            sql.setString(1, bookTitle);
            sql.setString(2, author);
            sql.setString(3, ISBN);
            sql.setInt(4, bookYear);
            sql.setInt(5, bookQuantity);
            sql.executeUpdate();
            conn.getConnection().close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean deleteBook(int bookId) {
        String state = "Delete from snaik_books where id=?;";
        try (PreparedStatement sql = conn.getConnection().prepareStatement(state)) {
            sql.setInt(1, bookId);
            sql.executeUpdate();
            conn.getConnection().close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean updateBook(int bookId, String bookTitle, String author, String ISBN, int bookYear, int bookQuantity) {
        String state = "UPDATE snaik_books SET book_title=?,book_author=?,book_isbn=?,book_year=?,book_quantity=? WHERE book_id=?; ";
        try (PreparedStatement sql = conn.getConnection().prepareStatement(state)) {
            sql.setString(1, bookTitle);
            sql.setString(2, author);
            sql.setString(3, ISBN);
            sql.setInt(4, bookYear);
            sql.setInt(5, bookQuantity);
            sql.setInt(6, bookId);
            sql.executeUpdate();
            conn.getConnection().close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

