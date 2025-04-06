package app.novacodex.novacodex;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Loader extends Preloader {
    private Stage preloaderStage;

    @Override
    public void init() {
        // Явная инициализация JavaFX
        System.setProperty("javafx.version", "21.0.2");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;

        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loader.fxml")));
        Scene scene = new Scene(root);

        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.setScene(scene);
        preloaderStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {

        if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
        }
    }
}