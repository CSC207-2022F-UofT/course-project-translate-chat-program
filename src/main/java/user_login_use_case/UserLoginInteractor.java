package user_login_use_case;

class UserLoginInteractor implements LoginInputBoundary {
    private final UserLoginGateway auth;
    private final LoginOutputBoundary presenter;

    public UserLoginInteractor(UserLoginGateway auth, LoginOutputBoundary presenter) {
        this.presenter = presenter;
        this.auth = auth;
    }

    @Override
    public LoginResponse login(LoginData data) {
        try {
            return auth.login(data);
        } catch (RuntimeException e) {
            return presenter.prepareFailView(e.getMessage());
        }
    }
}
