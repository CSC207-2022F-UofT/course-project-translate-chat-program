package presenters;

import user_login_use_case.LoginFailed;
import user_login_use_case.LoginOutputBoundary;
import user_login_use_case.LoginResponse;

public class LoginPresenter implements LoginOutputBoundary {
    @Override
    public LoginResponse prepareFailView(String error) throws RuntimeException {
        throw new LoginFailed(error);
    }

    @Override
    public LoginResponse prepareSuccessView(LoginResponse response) {
        return response;
    }
}
