package app.novacodex.novacodex.loader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RoleSelectionWindow {

    @FXML
    private void handleStudentClick(ActionEvent event) {
        System.out.println("Выбрана роль: Студент");
        // Действия при выборе студента (например, загрузка нового окна)
    }

    @FXML
    private void handleTeacherClick(ActionEvent event) {
        System.out.println("Выбрана роль: Преподаватель");
        // Действия при выборе преподавателя
    }
}