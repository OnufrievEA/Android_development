package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;
    @FXML
    public HBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passField;
    @FXML
    public HBox msgPanel;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;

    private boolean authentificated;
    private String nickName;

    public void setAuthentificated(boolean authentificated) {
        this.authentificated = authentificated;
        authPanel.setVisible(!authentificated);
        authPanel.setManaged(!authentificated);
        msgPanel.setVisible(authentificated);
        msgPanel.setManaged(authentificated);
        if (!authentificated) {
            nickName = "";
        }
        textArea.clear();
        setTitle("chat 2020");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        authentificated = false;
    }

    public void sendMsg() {
        if (textField.getText().trim().length() > 0) {
            try {
                out.writeUTF(textField.getText());
                textField.clear();
                textField.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void connect() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/authok ")) {
                            setAuthentificated(true);
                            nickName = str.split(" ")[1];
                            break;
                        }
                        textArea.appendText(str + "\n");
                    }

                    setTitle("chat 2020 : " + nickName);

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();
                        if (str.equals("/end")) {
                            setAuthentificated(false);
                            break;
                        }
                        textArea.appendText(str + "\n");

                    }
                } catch (SocketException e) {
                    System.out.println("Сервер отключился");
                    setAuthentificated(false);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }

        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            passField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        Platform.runLater(() -> {
            ((Stage) textField.getScene().getWindow()).setTitle(title);
        });
    }
}
