package views.controllers;


import user_login_use_case.LoginData;
import user_login_use_case.LoginInputBoundary;
import user_login_use_case.LoginResponse;

public class LoginController {
    /**
     * The interactor object.
     */
    final LoginInputBoundary interactor;

    public LoginController(LoginInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Attempts to log in to an account with the given username and password.
     * @param username The username used to log in
     * @param password The password used to log in
     * @return The response given by the interactor object.
     */
    public LoginResponse login(String username, String password) {
        LoginData data = new LoginData(username, password);
        return interactor.login(data);
    }
}
