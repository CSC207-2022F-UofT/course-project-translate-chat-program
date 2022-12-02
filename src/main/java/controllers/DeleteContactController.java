package controllers;

import contact_usecases.delete_contact_use_case.DeleteContactData;
import contact_usecases.delete_contact_use_case.DeleteContactInputBoundary;

public class DeleteContactController {
    DeleteContactInputBoundary interactor;

    public DeleteContactController(DeleteContactInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void deleteContact(DeleteContactData data) {
        this.interactor.deleteContact(data);
    }
}
