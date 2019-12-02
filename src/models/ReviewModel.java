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
 * ReviewModel retives data from the snaik_review table
 * the data is used for adding or deleting the review
 *
 */


public class ReviewModel extends DBConnect {


    /**
     * all teh required fields, buttons have been defined below
     */

    private int Review_id;
    private String BookReview;
    private int BookId;
    private int UserId;
    private JFXButton deleteReview;

    public JFXButton getDeleteReview() {
        return deleteReview;
    }

    public void setDeleteReview(JFXButton deleteReview) {
        this.deleteReview = deleteReview;
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



    /**
     * The addReview function
     * Adds Review into the database
     * @return boolean value
     * if review is added successfully it returns true else false
     */

    public Boolean addReview(int bookId, int userId, String bookReview) {
        String state = "Insert INTO snaik_reviews(book_id,user_id, review) Values(?,?,?);";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setInt(1, bookId);
            sql.setInt(2, userId);
            sql.setString(3, bookReview);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The getReviewsForBook function fetches reviews for the selected book_id
     * @param bookId
     * @return boolean value
     */

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

    /**
     * The deleteReview Function
     * @param reviewId The review with selected reviewId is deleted
     * @return boolean true if deletion successful else return false
     */

    public Boolean deleteReview(int reviewId) {
        String state = "Delete from snaik_reviews where review_id=?;";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setInt(1, reviewId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
