package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler {
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    Server server;
    private String nickName;
    private String login;

    public String getNickName() {
        return nickName;
    }

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
                            String newNickName = server
                                    .getAuthService()
                                    .getNickNameByLoginAndPassword(token[1], token[2]);
                            if (newNickName != null) {
                                sendMsg("/authok " + newNickName);
                                nickName = newNickName;
                                login = token[1];
                                server.subscribe(this);
                                System.out.println("Клиент " + nickName + " подключился.");
                                break;
                            } else {
                                sendMsg("Неверный логин или пароль");
                            }
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();
                        if (str.equals("/end")) {
                            out.writeUTF("/end");
                            break;
                        }
                        //работа с личными сообщениями
                        //предполагаем, что для отправки личных сообщений, необходимы все три части сообщения: /w получатель сообщение. Кроме того запретим отправку сообщений клиентом самому себе
                        //в случае нарушения формата отправки личных сообщений, выводим сообщение "Неверный формат отправки личного сообщения" в консоль
                        if (str.startsWith("/w")) {
                            try {
                                String[] token = str.split(" +", 3);
                                //проверяем, что существует клиент с указанным ником, передаваемое сообщение не пусто, клиент не отправляет сам себе
                                if (!token[2].equals("") && server.isClientByNick(token[1]) && !nickName.equals(token[1])) {
                                    server.personalMsg(token[2], token[1]);
                                    server.personalMsg(token[2], nickName);
                                    continue;
                                } else {
                                    System.out.println("Неверный формат отправки личного сообщения");
                                    continue;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Неверный формат отправки личного сообщения");
                                continue;
                            }
                        }
                        server.broadcastMsg(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    server.unsubscribe(this);
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
                    System.out.println("Клиент отключился");
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
