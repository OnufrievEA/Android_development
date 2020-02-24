package server;

import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {

    private class UserData {
        String login;
        String password;
        String nickName;

        public UserData(String login, String password, String nickName) {
            this.login = login;
            this.password = password;
            this.nickName = nickName;
        }
    }

    private List<UserData> users;

    public SimpleAuthService() {
        users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            users.add(new UserData("login" + i, "pass" + i, "nick" + i));
        }
    }

    @Override
    public String getNickNameByLoginAndPassword(String login, String password) {
        for (UserData o : users) {
            if(o.login.equals(login) && o.password.equals(password)){
                return o.nickName;
            }
        }
        return null;
    }
}
