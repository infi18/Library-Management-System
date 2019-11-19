package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Dao.DBConnect;
import interfaces.ControlledScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AdminController implements Initializable, ControlledScreen {

    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;

    ScreensController controller;

    // Declare DB objects
    DBConnect conn = null;
    Statement stmt = null;

    public AdminController() {
        conn = new DBConnect();
    }

    public void viewAccounts() {

    }

    public void updateRec() {

        pane3.setVisible(false);
        pane2.setVisible(false);
        pane1.setVisible(true);

    }

    public void deleteRec() {

        pane1.setVisible(false);
        pane2.setVisible(true);
        pane3.setVisible(false);
    }

    public void addBankRec() {

        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(true);

    }

    public void submitBank() {

        // INSERT INTO BANK TABLE
        try {
            // Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.getConnection().createStatement();
            String sql = null;

            // Include all object data to the database table

            sql = "insert into jpapa_bank(name,address) values ('" + txtName.getText() + "','" + txtAddress.getText()
                    + "')";
            stmt.executeUpdate(sql);
            System.out.println("Bank Record created");

            conn.getConnection().close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void submitUpdate() {

        System.out.println("Update Submit button pressed");

    }

    public void submitDelete() {

        System.out.println("Delete Submit button pressed");

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[LOG] Intialized");
    }
}
