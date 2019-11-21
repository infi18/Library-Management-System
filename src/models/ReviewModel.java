package models;
import Dao.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewModel {

    private int Review_id;
    private String BookReview;
    private int BookId;
    private int UserId;


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


    DBConnect conn = null;
    private PreparedStatement sql = null;

    public Boolean AddReview(ReviewModel model) {
        String state = "Insert INTO snaik_reviews(review_id, book_id,user_id, review) Values(?,?,?,?);";
        try {
            sql = conn.getConnection().prepareStatement(state);
            sql.setInt(1, model.getReview_id());
            sql.setInt(2, model.getBookId());
            sql.setInt(3, model.getUserId());
            sql.setString(4, model.getBookReview());
            sql.executeUpdate();
            conn.getConnection().close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ReviewModel> viewreview() {

        String query = "SELECT * FROM snaik_reviews;";
        try (PreparedStatement stmt = conn.getConnection().prepareStatement(query)) {
            List<ReviewModel> ReviewModelList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReviewModel review = new ReviewModel();
                review.setReview_id(rs.getInt("review_id"));
                review.setBookId(rs.getInt("book_id"));
                review.setUserId(rs.getInt("user_id"));
                ReviewModelList.add(review);
            }
            return ReviewModelList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Boolean DeleteReview(ReviewModel model) {
        String state = "Delete from snaik_reviews where review_id=?;";
        try {
            sql = conn.getConnection().prepareStatement(state);
            sql.setInt(1, model.getReview_id());
            sql.executeUpdate();
            conn.getConnection().close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
