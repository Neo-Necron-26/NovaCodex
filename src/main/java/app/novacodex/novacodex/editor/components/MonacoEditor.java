package app.novacodex.novacodex.editor.components;

import javafx.concurrent.Worker;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class MonacoEditor extends BorderPane {
    private final WebView webView = new WebView();
    private final WebEngine webEngine = webView.getEngine();
    private JSObject bridge;

    public MonacoEditor() {
        // Загружаем HTML-обертку для Monaco
        webEngine.load(getClass().getResource("/monaco-wrapper.html").toExternalForm());

        // Настраиваем мост между Java и JS
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                bridge = (JSObject) webEngine.executeScript("window");
                bridge.setMember("javaBridge", new JavaBridge());
            }
        });

        setCenter(webView);
    }

    // Методы для взаимодействия с редактором
    public void setText(String code) {
        webEngine.executeScript("editor.setValue(`" + escapeJsString(code) + "`)");
    }

    public String getText() {
        return (String) webEngine.executeScript("editor.getValue()");
    }

    private String escapeJsString(String str) {
        return str.replace("\\", "\\\\")
                .replace("`", "\\`")
                .replace("$", "\\$");
    }

    // Класс-мост для вызовов из JavaScript
    public class JavaBridge {
        public void handleContentChange(String newContent) {
            // Обработка изменений содержимого
            System.out.println("Content changed: " + newContent.substring(0, 50) + "...");
        }
    }
}