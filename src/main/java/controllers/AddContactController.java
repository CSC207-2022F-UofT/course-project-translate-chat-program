package controllers;

import contact_usecases.add_contact_use_case.AddContactData;
import contact_usecases.add_contact_use_case.AddContactInputBoundary;

import java.util.concurrent.ExecutionException;

public class AddContactController {
    AddContactInputBoundary interactor;

    public AddContactController(AddContactInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void addContact(AddContactData data) {
        try {
            this.interactor.addContact(data);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
