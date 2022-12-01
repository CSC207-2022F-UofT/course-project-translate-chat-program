package views;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import controllers.MessageEditController;
import gateways.MessageEditFirebaseSystem;
import message_edit_delete_use_case.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.MessageEditPresenter;
import services.DBInitializer;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class MessageEditControllerTest {
    private MessageEditController controller;
    DBInitializer initializer = new DBInitializer();

    @BeforeEach
    void setUp() {
        MessageEditGateway gateway = new MessageEditFirebaseSystem();
        MessageEditOutputBoundary presenter = new MessageEditPresenter();
        MessageEditInputBoundary interactor = new MessageEditInteractor(gateway, presenter);
        this.controller = new MessageEditController(interactor);
    }


    @Test
    void editMessage() throws FileNotFoundException, ExecutionException, InterruptedException {
        initializer.init();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        controller.editMessage(new MessageEditData("bye", 3));
        DocumentReference messageref = dbFirestore.collection("messages").document("id"+3);
        assertEquals("bye", Objects.requireNonNull(messageref.get().get().getData()).get("message"));
    }



}