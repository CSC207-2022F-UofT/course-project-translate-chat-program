package user_register_use_case;

public class RegisterFailed extends RuntimeException{
    public RegisterFailed(String message) {
        super(message);
    }
}
