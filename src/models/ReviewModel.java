package models;

import Dao.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewModel extends DBConnect {

    private int Review_id;
    private String BookReview;
    private int BookId;
    private int UserId;
    DBConnect conn = null;

    public ReviewModel() {
        conn = new ReviewModel();
    }

    public String getBookReview() {
        return BookReview;
    }

    public void setBookReview(String bookReview) {
        BookReview = bookReview;
    }

    public int getReview_id() {
        return Review_id;
    }

    public void setReview_id(int review_id) {
        Review_id = review_id;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }


    public Boolean addReview(int bookId, int userId, String bookReview) {
        String state = "Insert INTO snaik_reviews(book_id,user_id, review) Values(?,?,?);";
        try (PreparedStatement sql = conn.getConnection().prepareStatement(state)) {
            sql.setInt(1, bookId);
            sql.setInt(2, userId);
            sql.setString(3, bookReview);
            sql.executeUpdate();
            conn.getConnection().close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ReviewModel> getReviewsForBook(int bookId) {

        String query = "SELECT * FROM snaik_reviews where book_id =?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            List<ReviewModel> ReviewModelList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReviewModel review = new ReviewModel();
                review.setReview_id(rs.getInt("review_id"));
                review.setBookId(rs.getInt("book_id"));
                review.setUserId(rs.getInt("user_id"));
                review.setBookReview(rs.getString("review"));
                ReviewModelList.add(review);
            }
            return ReviewModelList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Boolean deleteReview(int reviewId) {
        String state = "Delete from snaik_reviews where review_id=?;";
        try (PreparedStatement sql = conn.getConnection().prepareStatement(state)) {
            sql.setInt(1, reviewId);
            sql.executeUpdate();
            conn.getConnection().close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
