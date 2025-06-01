package app.novacodex.novacodex.loader;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RoleSelectionWindow {

    @FXML
    private AnchorPane rootPane; // Основной контейнер для эффектов

    @FXML
    private void handleStudentClick(ActionEvent event) {
        loadNewScene("StudentView.fxml");
    }

    @FXML
    private void handleTeacherClick(ActionEvent event) {
        loadNewScene("TeacherView.fxml");
    }

    private void loadNewScene(String fxmlPath) {
        try {
            // 1. Создаем эффект исчезновения
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), rootPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(e -> {
                try {
                    // 2. Загружаем новую сцену
                    Stage stage = (Stage) rootPane.getScene().getWindow();
                    double width = stage.getWidth();
                    double height = stage.getHeight();

                    Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                    Scene scene = new Scene(root);

                    // 3. Сохраняем размеры окна
                    stage.setWidth(width);
                    stage.setHeight(height);
                    stage.setScene(scene);

                    // 4. Эффект появления
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}