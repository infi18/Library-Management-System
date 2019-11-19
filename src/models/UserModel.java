package models;

import Dao.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Siddhi Naik
 */
public class UserModel extends DBConnect {

    private int id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phone;
    private String password;
    private Boolean admin;
    DBConnect conn = null;
    private PreparedStatement sql = null;

    public UserModel() {
        conn = new DBConnect();
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
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return
     */
    public UserModel getUser(int id) {

        String query = "SELECT * FROM snaik_users WHERE email = ?;";
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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */
    public List<UserModel> getUsers() {

        String query = "SELECT * FROM snaik_users;";
        try (PreparedStatement stmt = conn.getConnection().prepareStatement(query)) {
            List<UserModel> userModelList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmailId(rs.getString("email"));
                user.setEmailId(rs.getString("phone"));
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
     * @param model
     * @return
     */
    public Boolean AddUser(UserModel model) {
        String state = "Insert INTO snaik_users(Fname,Lname,email,password,isAdmin) Values(?,?,?,?,'no');";
        try {
            sql = conn.getConnection().prepareStatement(state);
            sql.setString(1, model.getFirstName());
            sql.setString(2, model.getLastName());
            sql.setString(3, model.emailId);
            sql.setString(4, password);
            sql.executeUpdate();
            conn.getConnection().close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean DeleteUser(UserModel model) {
        String state = "Delete from snaik_users where id=?;";
        try {
            sql = conn.getConnection().prepareStatement(state);
            sql.setInt(1, model.getId());
            sql.executeUpdate();
            conn.getConnection().close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
	


