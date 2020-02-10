package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {

    @FXML
    public TextField messageText;
    @FXML
    public TextArea chatText;

    public void btnClicked() {
        String message = messageText.getText();
        if (message.length() != 0) {
            chatText.appendText(message + "\n");
            messageText.clear();
        }
        messageText.requestFocus();
    }

    public void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            btnClicked();
        }

    }
}
