package app.novacodex.novacodex;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loader extends Preloader {
    private static final Logger LOGGER = Logger.getLogger(Loader.class.getName());
    private Stage preloaderStage;

    @Override
    public void start(Stage primaryStage) {
        this.preloaderStage = primaryStage;

        try {
            // Load from FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loader.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load preloader FXML", e);
            // If preloader fails, try to launch main app directly
            launchMainApp();
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
            // Start delay in a separate thread
            new Thread(() -> {
                try {
                    // Wait for 1.5 seconds
                    Thread.sleep(1500);

                    // Close preloader and open main application
                    javafx.application.Platform.runLater(() -> {
                        preloaderStage.close();
                        launchMainApp();
                    });
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARNING, "Preloader delay interrupted", e);
                    Thread.currentThread().interrupt();
                    launchMainApp();
                }
            }).start();
        }
    }

    private void launchMainApp() {
        try {
            Main.showMainApp();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to launch main application", e);
            // If everything fails, at least exit gracefully
            javafx.application.Platform.exit();
        }
    }
}