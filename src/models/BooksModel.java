package models;
import Dao.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksModel extends DBConnect{
    private int BookId;
    private String BookTitle;
    private String BookAuthor;
    private String BookISBN;
    private Boolean Availability;
    private int BookYear;

    DBConnect conn = null;
    private PreparedStatement sql = null;

    public BooksModel() {
        conn = new DBConnect();
    }

    public Boolean getAvailability() {
        return Availability;
    }

    public void setAvailability(Boolean availability) {
        Availability = availability;
    }

    public String getBookISBN() {
        return BookISBN;
    }

    public void setBookISBN(String bookISBN) {
        BookISBN = bookISBN;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public int getBookId() {
        return BookId;
    }

    public int getBookYear() {
        return BookYear;
    }

    public void setBookYear(int bookYear) {
        BookYear = bookYear;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }
    public List<BooksModel> viewbooks() {

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
                BooksModelList.add(books);
            }
            return BooksModelList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<BooksModel> searchbooks() {

        String query = "SELECT * FROM snaik_books WHERE book_id = ?, book_title=?, book_author=?, book_isbn=?, book_year=?;";
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
                BooksModelList.add(books);
            }
            return BooksModelList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Boolean AddBooks(BooksModel model) {
        String state = "Insert INTO snaik_book(book_id,book_title,book_author,book_isbn,book_year) Values(?,?,?,?,?);";
        try {
            sql = conn.getConnection().prepareStatement(state);
            sql.setInt(1, model.getBookId());
            sql.setString(2, model.getBookTitle());
            sql.setString(3, model.getBookAuthor());
            sql.setString(4, model.getBookISBN());
            sql.setInt(5, model.getBookYear());
            sql.executeUpdate();
            conn.getConnection().close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean DeleteBook(BooksModel model) {
        String state = "Delete from snaik_books where id=?;";
        try {
            sql = conn.getConnection().prepareStatement(state);
            sql.setInt(1, model.getBookId());
            sql.executeUpdate();
            conn.getConnection().close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
