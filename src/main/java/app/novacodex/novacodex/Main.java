package app.novacodex.novacodex;

import app.novacodex.novacodex.editor.controller.EditorController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication(scanBasePackages = "app.novacodex.novacodex")
public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static ConfigurableApplicationContext springContext;
    private static Stage primaryStage;
    private WebView webView;

    @Override
    public void init() {
        notifyPreloader(new Preloader.StateChangeNotification(
                Preloader.StateChangeNotification.Type.BEFORE_INIT, null));

        // Копируем ресурсы Monaco здесь
        copyMonacoResources();

        springContext = new SpringApplicationBuilder(Main.class)
                .headless(false)
                .run();
    }


    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        notifyPreloader(new Preloader.StateChangeNotification(
                Preloader.StateChangeNotification.Type.BEFORE_START, null));

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/app/novacodex/novacodex/editor-view.fxml"));
            loader.setControllerFactory(springContext::getBean);

            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 700);

            // Получаем WebView из контроллера
            EditorController controller = loader.getController();
            webView = controller.getMonacoWebView();

            // Настраиваем WebView после отображения сцены
            primaryStage.setScene(scene);
            primaryStage.setTitle("NovaCodex");
            primaryStage.setOnShown(event -> initializeMonacoEditor());
            primaryStage.show();

        } catch (IOException e) {
            logger.error("Failed to load editor-view.fxml", e);
            showErrorScreen("FXML loading error: " + e.getMessage());
        }
    }

    // Инициализация Monaco Editor
    private void initializeMonacoEditor() {
        if (webView == null) {
            logger.error("WebView is not initialized!");
            showErrorScreen("WebView component not found");
            return;
        }

        WebEngine engine = webView.getEngine();

        // Обработка ошибок загрузки
        engine.getLoadWorker().exceptionProperty().addListener((obs, oldExc, newExc) -> {
            if (newExc != null) {
                logger.error("WebView load error", newExc);
                Platform.runLater(() ->
                        showErrorScreen("Monaco load failed: " + newExc.getMessage())
                );
            }
        });

        // Загружаем редактор после полной инициализации
        URL htmlUrl = getClass().getResource("/monaco-wrapper.html");
        if (htmlUrl == null) {
            logger.error("Monaco wrapper HTML not found");
            showErrorScreen("Missing editor resources");
            return;
        }

        engine.load(htmlUrl.toExternalForm());
        logger.info("Monaco editor initialized successfully");
    }

    private void showMainApp() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/app/novacodex/novacodex/editor-view.fxml"));
            loader.setControllerFactory(springContext::getBean);

            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 700);

            // Получаем ссылку на WebView из FXML
            WebView webView = (WebView) scene.lookup("#monacoWebView");
            WebEngine engine = webView.getEngine();

            // Загружаем HTML с Monaco
            webView.getEngine().load(Paths.get("monaco-resources/monaco-wrapper.html").toUri().toString());

            primaryStage.setTitle("NovaCodex");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            logger.error("Failed to load editor-view.fxml", e);
            // Обработка ошибки...
        }
    }
    private void showErrorScreen(String message) {
        StackPane errorPane = new StackPane();
        errorPane.getChildren().add(new Label("ERROR: " + message));
        primaryStage.setScene(new Scene(errorPane, 600, 400));
        primaryStage.show();
    }

    private void copyMonacoResources() {
        try {
            Path monacoDest = Paths.get("monaco-resources");
            if (!Files.exists(monacoDest)) {
                Files.createDirectories(monacoDest);

                // Копируем node_modules/monaco-editor/min/vs
                FileUtils.copyDirectory(
                        new File("node_modules/monaco-editor/min/vs"),
                        new File("monaco-resources/vs")
                );
            }
        } catch (IOException e) {
            logger.error("Failed to copy Monaco resources", e);
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
        System.setProperty("prism.order", "sw");
        System.setProperty("javafx.preloader", Loader.class.getName());
        Application.launch(Main.class, args);
    }
}