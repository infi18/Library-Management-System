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

    public void setBookId(int bookId) {
        BookId = bookId;
    }

}
