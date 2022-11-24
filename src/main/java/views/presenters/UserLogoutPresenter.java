package views.presenters;

import user_logout_use_case.UserLogoutOutputBoundary;
import views.Navigator;

public class UserLogoutPresenter implements UserLogoutOutputBoundary {
    private final Navigator nav;

    public UserLogoutPresenter(Navigator nav) {
        this.nav = nav;
    }

    @Override
    public void prepareLoginScreen() {
        nav.showScreen("login");
    }
}
