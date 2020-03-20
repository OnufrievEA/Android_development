package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler {
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    Server server;
    private String nick;
    private String login;

    public ClientHandler(Socket socket, Server server, Logger logger) {
        try {
            this.socket = socket;
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    logger.log(Level.INFO, "Аутентификация");
                    socket.setSoTimeout(120000);
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/reg ")) {
                            String[] token = str.split(" ");
                            boolean b = server
                                    .getAuthService()
                                    .registration(token[1], token[2], token[3]);
                            if (b) {
                                logger.log(Level.ALL, "Регистрация прошла успешно");
                                sendMsg("Регистрация прошла успешно");
                            } else {
                                logger.log(Level.ALL, "Пользователь не может быть зарегистрирован");
                                sendMsg("Пользователь не может быть зарегистрирован");
                            }
                        }

                        if (str.equals("/end")) {
                            logger.log(Level.ALL, "сами ");
                            throw new RuntimeException("сами ");
                        }
                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
                            if (token.length < 3) {
                                continue;
                            }
                            String newNick = server
                                    .getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null) {
                                login = token[1];
                                if (!server.isLoginAuthorized(login)) {
                                    sendMsg("/authok " + newNick);
                                    nick = newNick;
                                    server.subscribe(this);
                                    logger.log(Level.INFO, "Клиент " + nick + " подключился");
//                                    System.out.println("Клиент " + nick + " подключился");
                                    socket.setSoTimeout(0);
                                    break;
                                } else {
                                    logger.log(Level.INFO, "С этим логином уже авторизовались");
                                    sendMsg("С этим логином уже авторизовались");
                                }
                            } else {
                                logger.log(Level.INFO, "Неверный логин / пароль");
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/")) {
                            logger.log(Level.INFO, "Сервер получил команду " + str);
                            if (str.equals("/end")) {
                                out.writeUTF("/end");
                                break;
                            }
                            if (str.startsWith("/w ")) {
                                String[] token = str.split(" ", 3);
                                if (token.length == 3) {
                                    logger.log(Level.INFO, "Клиент " + nick + " отправил личное сообщение " + token[1] + ": " + token[2]);
                                    server.privateMsg(this, token[1], token[2]);
                                }
                            }

                            if (str.startsWith("/chnick ")) {
                                String[] token = str.split(" ", 2);
                                if (token[1].contains(" ")) {
                                    sendMsg("Ник не может содержать пробелов");
                                    continue;
                                }
                                if (server.getAuthService().changeNick(this.nick, token[1])) {
                                    sendMsg("/yournickis " + token[1]);
                                    sendMsg("Ваш ник изменен на " + token[1]);
                                    this.nick = token[1];
                                    server.broadcastClientList();
                                } else {
                                    sendMsg("Не удалось изменить ник. Ник " + token[1] + " уже существует");
                                }
                            }
                        } else {
                            logger.log(Level.INFO, "Клиент " + nick + " отправил сообщение всем: " + str);
                            server.broadcastMsg(nick, str);
                        }


                    }
                }catch (SocketTimeoutException e){
                    logger.log(Level.INFO, "Клиент отключился по таймауту");
//                    System.out.println("Клиент отключился по таймауту");
                } catch (RuntimeException e) {
                    logger.log(Level.INFO, "сами вызвали исключение.");
//                    System.out.println("сами вызвали исключение.");
                } catch (IOException e) {
                    logger.log(Level.INFO, "Ошибка");
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
                    logger.log(Level.INFO, "Клиент " + nick + " отключился");
//                    System.out.println("Клиент отключился");
                }
            }).start();


        } catch (IOException e) {
            logger.log(Level.INFO, "Ошибка", e);
//            e.printStackTrace();
        }

    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    public String getLogin() {
        return login;
    }
}
