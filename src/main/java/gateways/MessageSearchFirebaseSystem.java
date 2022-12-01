package gateways;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import entities.Message;
import entities.User;
import message_search_use_case.MessageSearchData;
import message_search_use_case.MessageSearchGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the MessageSearchGateway interface that searches through the remote Firebase database and gets
 * a list of Message entities matching some input data.
 * @author Muhammad Muzammil
 */
public class MessageSearchFirebaseSystem implements MessageSearchGateway {

    /**
     * Searches for messages in Google Cloud database that match text in data
     * @param data MessageSearchData object that tells method which messages to look for
     * @return list of Message objects that match data
     */
    @Override
    public List<Message> search(MessageSearchData data) {
        String chatId = "id" + data.getChatId(); // to know which chat Document to search in
        String query = data.getText().toLowerCase(); // to know what to search for
        Firestore dbFirestore = FirestoreClient.getFirestore(); // getting Firestore instance
        // Getting the document reference of the chat associated with chatId
        DocumentReference chatDocumentReference = dbFirestore.collection("chats").document(chatId);

        try {
            DocumentSnapshot chatDocument = chatDocumentReference.get().get(); // getting the actual chat document
            Map<String, Object> chatDocumentData = chatDocument.getData(); // getting data from the document
            // Getting the value associated with "messages", which is a List of Document References to the messages in
            // the chat
            assert chatDocumentData != null;
            ArrayList<DocumentReference> references = (ArrayList<DocumentReference>) chatDocumentData.get("messages");
            List<Message> listMsg = new ArrayList<>();
            for (DocumentReference ref : references) {
                DocumentSnapshot msgDoc = ref.get().get(); // getting the message Document
                String msgText = (String) Objects.requireNonNull(msgDoc.getData()).get("message"); // getting the text of the message
                if (msgText.toLowerCase().contains(query)) { // if query is a substring of message
                    // Creating the Message object based on the data from msgDoc
                    Message msg = createMessageFromDoc(msgDoc);
                    listMsg.add(msg);
                }
            }
            return listMsg;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Message createMessageFromDoc(DocumentSnapshot msgDoc) throws ExecutionException, InterruptedException {
        // CREATING USER ENTITY THAT IS THE RECEIVER OF THE MESSAGE
        DocumentReference receiverRef = (DocumentReference) Objects.requireNonNull(msgDoc.getData()).get("receiver");
        DocumentSnapshot receiverDoc = receiverRef.get().get();
        User receiver = new User((String) Objects.requireNonNull(receiverDoc.getData()).get("name"),
                (String) receiverDoc.getData().get("default_lang"),
                (String) receiverDoc.getData().get("email"),
                (String) receiverDoc.getData().get("password"),
                ((Long) receiverDoc.getData().get("user_id")).intValue());

        //CREATING A USER ENTITY THAT IS THE RECIPIENT OF THE MESSAGE
        DocumentReference recipientRef = (DocumentReference) msgDoc.getData().get("recipient");
        DocumentSnapshot recipientDoc = recipientRef.get().get();
        User recipient = new User((String) Objects.requireNonNull(recipientDoc.getData()).get("name"),
                (String) receiverDoc.getData().get("default_lang"),
                (String) receiverDoc.getData().get("email"),
                (String) receiverDoc.getData().get("password"),
                ((Long) receiverDoc.getData().get("user_id")).intValue());

        //CREATING THE ACTUAL MESSAGE ENTITY
        return new Message(((Long) msgDoc.getData().get("id")).intValue(),
                (String) msgDoc.getData().get("message"),
                receiver,
                recipient,
                msgDoc.getDate("timestamp"));
    }
}
