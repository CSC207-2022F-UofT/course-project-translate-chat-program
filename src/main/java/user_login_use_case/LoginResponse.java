package user_login_use_case;

import shared.Response;

public class LoginResponse extends Response {
    private LoginData data;

    public Exception getException() {
        return super.e;
    }

    public LoginData getData() {
        return data;
    }

    public LoginResponse(LoginData data, boolean success, Exception e) {
        this.data = data;
        this.success = success;
        this.e = e;
    }

}