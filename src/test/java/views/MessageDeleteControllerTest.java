package views;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import controllers.MessageDeleteController;
import gateways.MessageDeleteFirebaseSystem;
import message_edit_delete_use_case.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DBInitializer;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class MessageDeleteControllerTest {
    private MessageDeleteController controller;
    DBInitializer initializer = new DBInitializer();

    @BeforeEach
    void setUp() {
        MessageDeleteGateway gateway = new MessageDeleteFirebaseSystem();
        MessageDeleteOutputBoundary presenter = new MessageDeletePresenter();
        MessageDeleteInputBoundary interactor = new MessageDeleteInteractor(gateway, presenter);
        this.controller = new MessageDeleteController(interactor);
    }


    @Test
    void deleteSuccess() throws FileNotFoundException, ExecutionException, InterruptedException {
        initializer.init();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        controller.delete(new MessageDeleteData(5, 4));
        DocumentReference chatref = dbFirestore.collection("chats").document("id"+4);
        assertEquals(0, ( (List<DocumentReference>) Objects.requireNonNull(chatref.get().get().getData()).get("messages")).size());

    }
}