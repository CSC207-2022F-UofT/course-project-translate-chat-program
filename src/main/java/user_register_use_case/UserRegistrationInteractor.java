package user_register_use_case;

public class UserRegistrationInteractor implements UserRegisterInputBoundary{
    private final UserRegistrationGateway factory;
    private final UserRegisterOutputBoundary presenter;

    public UserRegistrationInteractor(UserRegistrationGateway factory, UserRegisterOutputBoundary presenter) {
        this.factory = factory;
        this.presenter = presenter;
    }

    @Override
    public RegisterResponse create(CreationData data) {
        try {
            boolean result = factory.create(data);
            return presenter.prepareSuccessView(new RegisterResponse(data, result, null));
        } catch (Exception e) {
            return presenter.prepareFailView(e.getMessage());
        }
    }
}
