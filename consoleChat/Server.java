package consoleChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket server;
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        final int PORT = 8189;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запустился");

            socket = server.accept();
            System.out.println("Клиент подключился");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            //поток для чтения сообщений, пришедших от клиента
            new Thread(() -> {
                utils.readMessage(in, out, socket);
            }).start();

            //поток для отправки сообщений клиенту
            new Thread(() -> {
                utils.sendMessage(out, scanner);
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
