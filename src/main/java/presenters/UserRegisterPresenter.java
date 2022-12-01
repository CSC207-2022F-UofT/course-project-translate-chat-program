package presenters;

import user_register_use_case.RegisterFailed;
import user_register_use_case.RegisterResponse;
import user_register_use_case.UserRegisterOutputBoundary;

public class UserRegisterPresenter implements UserRegisterOutputBoundary {

    @Override
    public RegisterResponse prepareSuccessView(RegisterResponse response) {
        return response;
    }

    @Override
    public RegisterResponse prepareFailView(String message) throws RuntimeException {
        throw new RegisterFailed(message);
    }
}
