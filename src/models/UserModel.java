package models;

import Dao.DBConnect;
import com.jfoenix.controls.JFXButton;
import com.mysql.cj.jdbc.exceptions.SQLError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Siddhi Naik
 * UserModel retives data from the snaik_users table
 * the data is used for login, signup
 * To add, modify, delete user
 */
public class UserModel extends DBConnect {

    /**
     * all the required fields, buttons have been defined below
     */

    private int id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phone;
    private String password;
    private Boolean admin;
    private JFXButton details;



    public JFXButton getDetails() {
        return details;
    }

    public void setDetails(JFXButton details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * The getCredentials fucntion retrieves user with sepcified emailId, password
     * @param username
     * @param password
     * @return
     */
    public Boolean getCredentials(String username, String password) {

        String query = "SELECT * FROM snaik_users WHERE email = ? and password = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                setId(rs.getInt("user_id"));
                setFirstName(rs.getString("first_name"));
                setLastName(rs.getString("last_name"));
                setEmailId(rs.getString("email"));
                setAdmin(rs.getString("is_admin").equalsIgnoreCase("yes"));
                setPhone(rs.getString("phone"));
                setPassword(rs.getString("password"));
                return true;
            }
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The getUser function retrieves user with specified email id
     * @returns null
     */
    public UserModel getUser(String id) {

        String query = "SELECT * FROM snaik_users WHERE email = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserModel user = new UserModel();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmailId(rs.getString("email"));
                user.setAdmin(rs.getString("is_admin").equalsIgnoreCase("yes"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The getUserForId function retrives the user with the particular mentioned id
     * @return user The User with the required Id is returned to the model from the database
     */
    public UserModel getUserForId(int id) {

        String query = "SELECT * FROM snaik_users WHERE user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserModel user = new UserModel();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmailId(rs.getString("email"));
                user.setAdmin(rs.getString("is_admin").equalsIgnoreCase("yes"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The getAllUsers function retrieves all user from the database
     * @returns list of users
     */
    public List<UserModel> getAllUsers() {
        String query = "SELECT * FROM snaik_users;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            List<UserModel> userModelList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmailId(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setAdmin(rs.getString("is_admin").equalsIgnoreCase("yes"));
                userModelList.add(user);
            }
            return userModelList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * The addUser function
     * Adds User into the database
     * @return boolean value
     * if user is added successfully it returns true else false
     */
    public Boolean addUser(String firstName, String lastName, String emailId, String phone, String password, Boolean isAdmin) {
        String state = "Insert INTO snaik_users(first_name,last_name,email,password,phone,is_admin) Values(?,?,?,?,?,?);";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setString(1, firstName);
            sql.setString(2, lastName);
            sql.setString(3, emailId);
            sql.setString(4, password);
            sql.setString(5, phone);
            sql.setString(6, isAdmin.compareTo(Boolean.TRUE) == 0 ? "yes" : "no");
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The updateUser function updates the user if any of the following parameters is updated
     * @param userId
     * @param firstName
     * @param lastName
     * @param emailId
     * @param phone
     * @param password
     * @param isAdmin
     * @return boolean true if updation successful else boolean false
     */

    public Boolean updateUser(Integer userId, String firstName, String lastName, String emailId, String phone, String password, Boolean isAdmin) {
        String state = "UPDATE snaik_users SET first_name=?,last_name=?,email=?,password=?,phone=?,is_admin=? WHERE user_id=?;";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setString(1, firstName);
            sql.setString(2, lastName);
            sql.setString(3, emailId);
            sql.setString(4, password);
            sql.setString(5, phone);
            sql.setString(6, isAdmin.compareTo(Boolean.TRUE) == 0 ? "yes" : "no");
            sql.setInt(7, userId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The deleteuser Function
     * @param userId The user with selected userId is deleted
     * @return boolean true if deletion successful else return false
     */
    public Boolean deleteUser(int userId) {
        String state = "Delete from snaik_users where user_id=?;";
        try (PreparedStatement sql = connection.prepareStatement(state)) {
            sql.setInt(1, userId);
            sql.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
	


