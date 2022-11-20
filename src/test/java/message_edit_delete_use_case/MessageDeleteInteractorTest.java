package message_edit_delete_use_case;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import gateways.MessageDeleteFirebaseSystem;
import message_search_use_case.MessageSearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DBInitializer;
import views.MessageDeletePresenter;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class MessageDeleteInteractorTest {
    DBInitializer initializer = new DBInitializer();
    MessageDeleteGateway gateway = new MessageDeleteFirebaseSystem();
    MessageDeleteOutputBoundary presenter = new MessageDeletePresenter();
    MessageDeleteInteractor interactor = new MessageDeleteInteractor(gateway, presenter);

    @Test
    void messageDelete() throws ExecutionException, InterruptedException, FileNotFoundException {
        initializer.init();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        interactor.messageDelete(new MessageDeleteData(5, 4));
        DocumentReference chatref = dbFirestore.collection("chats").document("id"+4);
        assertEquals(0, ( (List<DocumentReference>)chatref.get().get().getData().get("messages")).size());

    }
}