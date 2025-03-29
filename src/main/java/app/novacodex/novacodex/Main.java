package app.novacodex.novacodex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        // Don't show main window immediately - it will be shown by the preloader
    }

    public static void showMainApp() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 290);
            primaryStage.setTitle("NovaCodex");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load main application FXML", e);
            throw e;
        }
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", "app.novacodex.novacodex.Loader");
        launch(args);
    }
}