package app.novacodex.novacodex.editor.components;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import java.io.*;

public class CodeEditor extends BorderPane {
    private final TextArea textArea;
    private File currentFile;

    public CodeEditor() {
        textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        // Создаем меню
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        MenuItem newItem = new MenuItem("New");
        newItem.setOnAction(e -> textArea.clear());

        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> openFile());

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> saveFile());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));

        fileMenu.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);

        setTop(menuBar);
        setCenter(textArea);
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(getScene().getWindow());

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                textArea.clear();
                String line;
                while ((line = br.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }
                currentFile = file;
            } catch (IOException e) {
                showAlert("Error opening file: " + e.getMessage());
            }
        }
    }

    private void saveFile() {
        File file = currentFile != null ? currentFile : getSaveFile();
        if (file != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(textArea.getText());
                currentFile = file;
            } catch (IOException e) {
                showAlert("Error saving file: " + e.getMessage());
            }
        }
    }

    private File getSaveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        return fileChooser.showSaveDialog(getScene().getWindow());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}