package presenters;

import contact_usecases.add_contact_use_case.AddContactFailed;
import contact_usecases.add_contact_use_case.AddContactOutputBoundary;
import contact_usecases.add_contact_use_case.AddContactResponse;
public class AddContactPresenter implements AddContactOutputBoundary {
    @Override
    public AddContactResponse prepareSuccessView(AddContactResponse response) {
        return response;
    }

    @Override
    public AddContactResponse prepareFailView(String error) {
        throw new AddContactFailed(error);
    }
}
