package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleAuthService implements AuthService {

    private static Connection connection;
    private static PreparedStatement psGetNick;
    private static PreparedStatement psAddNewClient;

    public SimpleAuthService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {
            psGetNick = connection.prepareStatement("SELECT nick FROM Clients WHERE login = ? AND password = ?");
            psGetNick.setString(1, login);
            psGetNick.setString(2, password);
            ResultSet rs = psGetNick.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }


    @Override
    public boolean registration(String login, String password, String nickname) {
        try {
            psAddNewClient = connection.prepareStatement("INSERT INTO Clients (login, password, nick) VALUES (?, ?, ?)");
            psAddNewClient.setString(1, login);
            psAddNewClient.setString(2, password);
            psAddNewClient.setString(3, nickname);
            psAddNewClient.executeUpdate();
            return true;
        } catch (SQLException e) {
            return  false;
        }
    }
}
