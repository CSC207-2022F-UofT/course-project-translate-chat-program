package user_login_use_case;

public interface LoginOutputBoundary {
    LoginResponse prepareFailView(String error);
    LoginResponse prepareSuccessView(LoginResponse response);

}
