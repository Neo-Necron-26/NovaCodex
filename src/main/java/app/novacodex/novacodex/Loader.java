package app.novacodex.novacodex;

import javafx.application.Platform;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loader.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load preloader FXML", e);
            Platform.runLater(this::closeAndLaunchMain);
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification notification) {
        if (notification.getType() == StateChangeNotification.Type.BEFORE_START) {
            new Thread(() -> {
                try {
                    Thread.sleep(2000);  // Искусственная задержка для демонстрации
                    Platform.runLater(this::closeAndLaunchMain);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARNING, "Preloader delay interrupted", e);
                    Thread.currentThread().interrupt();
                    Platform.runLater(this::closeAndLaunchMain);
                }
            }).start();
        }
    }

    private void closeAndLaunchMain() {
        preloaderStage.close();
        // Больше не нужно вызывать Main.showMainApplication()
        // Главное окно откроется автоматически через start() в Main
    }
}