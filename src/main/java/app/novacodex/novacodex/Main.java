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
    private static ConfigurableApplicationContext springContext;
    private static Stage primaryStage;

    @Override
    public void init() {
        // Уведомляем прелоадер о начале инициализации
        notifyPreloader(new Preloader.StateChangeNotification(
                Preloader.StateChangeNotification.Type.BEFORE_INIT, null));

        // Запускаем Spring контекст
        springContext = new SpringApplicationBuilder(Main.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        // Уведомляем прелоадер, что можно закрываться
        notifyPreloader(new Preloader.StateChangeNotification(
                Preloader.StateChangeNotification.Type.BEFORE_START, null));

        // Показываем главное окно
        Platform.runLater(this::showMainApp);
    }

    private void showMainApp() {
        Label label = new Label("Добро пожаловать в NovaCodex!");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("NovaCodex");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", Loader.class.getName());
        Application.launch(Main.class, args);
    }
}