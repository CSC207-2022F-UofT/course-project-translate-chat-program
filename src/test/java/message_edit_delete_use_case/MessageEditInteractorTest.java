package message_edit_delete_use_case;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import gateways.MessageEditFirebaseSystem;
import org.junit.jupiter.api.Test;
import services.DBInitializer;
import presenters.MessageEditPresenter;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class MessageEditInteractorTest {
    DBInitializer initializer = new DBInitializer();
    MessageEditGateway gateway = new MessageEditFirebaseSystem();
    MessageEditOutputBoundary presenter = new MessageEditPresenter();
    MessageEditInteractor interactor = new MessageEditInteractor(gateway, presenter);

    @Test
    void editMessage() throws ExecutionException, InterruptedException, FileNotFoundException {
        initializer.init();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        interactor.editMessage(new MessageEditData("bye", 3));
        DocumentReference messageref = dbFirestore.collection("messages").document("id"+3);
        assertEquals("bye", messageref.get().get().getData().get("message"));
    }




}