/**
 * <h1>Library Management System</h1>
 *
 * @author Siddhi  Naik
 * @since 2019-11-16
 */
package application;

import controllers.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * Extends the main Application class and hence load the pages.
 * Used like a framework
 *
 */
public class LibrarySystem extends Application {

    /**
     *
     */
    public static String screen1ID = "Landing Page";
    public static String screen1File = "/views/LandingPage.fxml";
    public static String screen2ID = "User Login";
    public static String screen2File = "/views/UserLogin.fxml";
    public static String screen3ID = "User SignUp";
    public static String screen3File = "/views/UserSignup.fxml";
    public static String screen4ID = "Admin Login";
    public static String screen4File = "/views/AdminLogin.fxml";
    public static String screen5ID = " User View";
    public static String screen5File = "/views/UserView.fxml";
    public static String screen6ID = "User Details";
    public static String screen6File = "/views/UserDetails.fxml";
    public static String screen7ID = "Books View";
    public static String screen7File = "/views/BooksView.fxml";
    public static String screen8ID = "Book Details";
    public static String screen8File = "/views/BookDetails.fxml";

    /**
     * @param primaryStage used to set the main stage and hence
     * load all the other screens.
     */
    @Override
    public void start(Stage primaryStage) {

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(LibrarySystem.screen1ID, LibrarySystem.screen1File);
        mainContainer.loadScreen(LibrarySystem.screen2ID, LibrarySystem.screen2File);
        mainContainer.loadScreen(LibrarySystem.screen3ID, LibrarySystem.screen3File);
        mainContainer.loadScreen(LibrarySystem.screen4ID, LibrarySystem.screen4File);
        mainContainer.setScreen(LibrarySystem.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     *
     * @param args used to launch the fx application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
