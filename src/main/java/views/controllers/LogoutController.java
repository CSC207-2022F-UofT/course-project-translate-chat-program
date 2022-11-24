package views.controllers;

import user_logout_use_case.UserLogoutInputBoundary;

public class LogoutController {
    private final UserLogoutInputBoundary interactor;

    public LogoutController(UserLogoutInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void logout() {
        interactor.logout();
    }
}
