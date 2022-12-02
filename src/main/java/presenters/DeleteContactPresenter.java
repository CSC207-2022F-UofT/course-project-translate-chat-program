package presenters;

import contact_usecases.delete_contact_use_case.DeleteContactFailed;
import contact_usecases.delete_contact_use_case.DeleteContactOutputBoundary;
import contact_usecases.delete_contact_use_case.DeleteContactResponse;

public class DeleteContactPresenter implements DeleteContactOutputBoundary {


    @Override
    public DeleteContactResponse prepareSuccessView(DeleteContactResponse response) {
        return response;
    }

    @Override
    public DeleteContactResponse prepareFailView(String error) {
        throw(new DeleteContactFailed(error));
    }
}
