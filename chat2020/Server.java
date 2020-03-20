package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.*;

public class Server {
    private Vector<ClientHandler> clients;
    private AuthService authService;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        try {
            setUpLogger(logger);
        } catch (IOException e) {
            logger.log(Level.INFO, "Ошибка", e);
        }
        clients = new Vector<>();
//        authService = new SimpleAuthService();
        if (!SQLHandler.connect()) {
            logger.log(Level.INFO, "Не удалось подключиться к БД");
            throw new RuntimeException("Не удалось подключиться к БД");
        }
        authService = new DBAuthServise();

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            logger.log(Level.INFO, "Сервер запустился");
//            System.out.println("Сервер запустился");

            while (true) {
                socket = server.accept();
//                logger.log(Level.ALL, "Клиент подключился");
//                System.out.println("Клиент подключился");
                new ClientHandler(socket, this, logger);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Произошла ошибка", e);
//            e.printStackTrace();
        } finally {
            SQLHandler.disconnect();
            try {
                logger.log(Level.ALL, "Сервер выключился");
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(String nick, String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(nick + " : " + msg);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[ %s ] private [ %s ] : %s", sender.getNick(), receiver, msg);

        for (ClientHandler c : clients) {
            if (c.getNick().equals(receiver)) {
                c.sendMsg(message);
                if (!sender.getNick().equals(receiver)) {
                    sender.sendMsg(message);
                }
                return;
            }
        }

        logger.log(Level.INFO, "not found user :" + receiver);
        sender.sendMsg("not found user :" + receiver);
    }


    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }


    public boolean isLoginAuthorized(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder("/clientlist ");

        for (ClientHandler c : clients) {
            sb.append(c.getNick()).append(" ");
        }

        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

    private static void setUpLogger(Logger logger) throws IOException {
        logger.setUseParentHandlers(false);
        Handler handler = new FileHandler("srv_log.log", true);
        handler.setLevel(Level.INFO);
        handler.setFormatter(new SimpleFormatter());
        logger.setLevel(Level.INFO);
        logger.addHandler(handler);
    }
}
