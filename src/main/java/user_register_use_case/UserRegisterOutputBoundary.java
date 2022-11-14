package user_register_use_case;

public interface UserRegisterOutputBoundary {
    RegisterResponse prepareSuccessView(RegisterResponse response);
    RegisterResponse prepareFailView(String message);
}
