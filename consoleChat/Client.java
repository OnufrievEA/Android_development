package consoleChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        final String IP_ADDRESS = "localhost";
        final int PORT = 8189;

        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            //поток для чтения сообщений, пришедших от сервера
            new Thread(() -> {
                utils.readMessage(in, out, socket);
            }).start();

            //поток для отправки сообщений на сервер
            new Thread(() -> {
                utils.sendMessage(out, scanner);
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
