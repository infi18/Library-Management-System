package models;

import Dao.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckoutModel extends DBConnect {
    private int checkoutId;
    private int bookId;
    private int userId;

    public int getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(int checkoutId) {
        this.checkoutId = checkoutId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CheckoutModel getCheckoutsForUser(int UserId, int BookId) {
        String query = "SELECT * FROM snaik_checkout WHERE user_id = ? and book_id=?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, UserId);
            stmt.setInt(2, BookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CheckoutModel checkoutModel = new CheckoutModel();
                checkoutModel.setCheckoutId(rs.getInt("checkout_id"));
                checkoutModel.setUserId(rs.getInt("user_id"));
                checkoutModel.setBookId(rs.getInt("book_id"));
                return checkoutModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean deleteCheckout(int userId, int bookId) {
        String state = "Delete from snaik_checkout where user_id=? and book_id=?;";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setInt(1, userId);
            sql.setInt(2, bookId);
            sql.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean addCheckOutRecord(int userId, int bookId) {
        String state = "Insert INTO snaik_checkout(user_id,book_id) Values(?,?);";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setInt(1, bookId);
            sql.setInt(2, userId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
