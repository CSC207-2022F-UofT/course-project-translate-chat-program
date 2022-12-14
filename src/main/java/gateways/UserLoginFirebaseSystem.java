package gateways;

import entities.User;
import services.DBService;
import shared.UserDetails;
import user_login_use_case.LoginData;
import user_login_use_case.LoginResponse;
import user_login_use_case.UserLoginGateway;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class UserLoginFirebaseSystem implements UserLoginGateway {
    private final DBService db;
    public UserLoginFirebaseSystem(DBService db) {
        this.db = db;
    }

    @Override
    public LoginResponse login(LoginData data) throws IOException {
        String username = data.getUsername();
        String password = data.getPassword();
        User user;
        try {
            user = db.getByUsername(username);
        } catch (ExecutionException | InterruptedException e) {
            return new LoginResponse(null, data, false, e);
        }
        if (user == null) {
            return new LoginResponse(null, data, false, new RuntimeException("User not found"));
        } else if (!user.getPassword().equals(password)) {
            UserDetails details = new UserDetails(username, user.getUser_id(), user.getDefault_lang(), user.getContacts());
            return new LoginResponse(details, data, false, new RuntimeException("Password doesn't match"));
        } else {
            UserDetails details = new UserDetails(username, user.getUser_id(), user.getDefault_lang(), user.getContacts());
            return new LoginResponse(details, data, true, null);
        }
    }
}
