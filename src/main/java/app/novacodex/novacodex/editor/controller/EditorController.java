package app.novacodex.novacodex.editor.controller;

import app.novacodex.novacodex.editor.components.CodeEditor;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Controller;

@Controller
public class EditorController {
    @FXML
    private BorderPane editorContainer;

    @FXML
    public void initialize() {
        CodeEditor editor = new CodeEditor();
        editorContainer.setCenter(editor);
    }
}