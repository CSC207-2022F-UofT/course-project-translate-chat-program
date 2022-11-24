package views.controllers;


import user_register_use_case.CreationData;
import user_register_use_case.UserRegisterInputBoundary;
import user_register_use_case.RegisterResponse;

public class UserRegisterController {
    /**
     * The interactor object.
     */
    private final UserRegisterInputBoundary interactor;

    public UserRegisterController(UserRegisterInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Attempts to log in to an account with the given username and password.
     * @param username The username used to log in
     * @param password The password used to log in
     * @return The response given by the interactor object.
     */
    public RegisterResponse register(String username, String password, String email, String default_lang) {
        CreationData data = new CreationData(username, password, email, default_lang);
        return interactor.create(data);
    }
}
