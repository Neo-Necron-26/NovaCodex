package app.novacodex.novacodex.editor.controller;

import app.novacodex.novacodex.editor.components.CodeEditor;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.stereotype.Controller;

@Controller
public class EditorController {
    @FXML
    private WebView monacoWebView; // Это должно соответствовать fx:id в FXML

    @FXML
    public void initialize() {
        // Инициализация Monaco Editor будет происходить через HTML/JS
        WebEngine engine = monacoWebView.getEngine();
        engine.load(getClass().getResource("/monaco-wrapper.html").toExternalForm());
    }
    public WebView getMonacoWebView() {
        return monacoWebView;
    }
}