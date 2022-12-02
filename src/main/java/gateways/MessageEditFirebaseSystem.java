package gateways;

import message_edit_delete_use_case.MessageEditData;
import message_edit_delete_use_case.MessageEditGateway;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.Map;
import java.util.concurrent.ExecutionException;
/**
 * The firebase system class for the message edit use case.
 */
public class MessageEditFirebaseSystem implements MessageEditGateway {
    /**
     * Message editing method for MessageEditFirebaseSystem.
     *
     * @param data the data class of the message to be edited.
     */

    @Override
    public void edit(MessageEditData data) {
        String messageID = "id" + data.getID();
        String text = data.getEditText();
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference messageDocumentReference = dbFireStore.collection("messages").document(messageID);
        try {
            DocumentSnapshot messageDoc = messageDocumentReference.get().get();
            Map<String, Object> msgData = messageDoc.getData();
            assert msgData != null;
            msgData.put("message", text);
            messageDocumentReference.set(msgData);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
