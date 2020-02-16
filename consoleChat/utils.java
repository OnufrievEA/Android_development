package consoleChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class utils {

    public static void readMessage(DataInputStream in, DataOutputStream out, Socket socket) {
        try {
            while (true) {
                String str = in.readUTF();
                if (str.equals("/end")) {
                    //отправляем сообщение о завершении работы собеседнику
                    System.out.println(str);
                    out.writeUTF("/end");
                    break;
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //закрываем streamы и сокет, вырубаем приложение
            utils.finish_app(in, out, socket);
        }
    }

    public static void sendMessage(DataOutputStream out, Scanner scanner) {
        try {
            while (true) {
                out.writeUTF(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void finish_app(DataInputStream in, DataOutputStream out, Socket socket) {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
