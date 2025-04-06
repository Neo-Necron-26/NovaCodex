package app.novacodex.novacodex;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
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
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("editor-view.fxml"));
            loader.setControllerFactory(springContext::getBean);

            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 700);

            primaryStage.setTitle("NovaCodex");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            logger.error("Failed to load editor-view.fxml", e);
            Label label = new Label("Ошибка загрузки интерфейса. Пожалуйста, проверьте файлы приложения.");
            StackPane stackPane = new StackPane(label);
            Scene scene = new Scene(stackPane, 1000, 700);

            primaryStage.setTitle("NovaCodex - ошибка");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
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
        System.setProperty("javafx.version", "21.0.2");
        Application.launch(Main.class, args);
    }
}