package app.novacodex.novacodex;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FirstButton {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome!");
    }
}