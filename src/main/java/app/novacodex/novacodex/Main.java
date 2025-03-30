package app.novacodex.novacodex;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {
    private Stage primaryStage;
    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        notifyPreloader(new Preloader.StateChangeNotification(
                Preloader.StateChangeNotification.Type.BEFORE_INIT, null));

        springContext = new SpringApplicationBuilder(Main.class)
                .headless(false)
                .run();

        notifyPreloader(new Preloader.StateChangeNotification(
                Preloader.StateChangeNotification.Type.BEFORE_START, null));
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showMainApp();
    }

    private void showMainApp() {
        // Создаем сцену без FXML
        Label label = new Label("Добро пожаловать в NovaCodex!");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("NovaCodex");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", "app.novacodex.novacodex.Loader");
        launch(args);
    }
}