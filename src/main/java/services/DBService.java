package services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import entities.User;
import java.util.HashMap;
import java.util.Map;
import entities.Chat;
import entities.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBService {

    /** Write the data of a given user to Firestore.
     *
     * @param user The User to write to the database
     * @return The time at which the data was written
     * @throws ExecutionException If execution is interrupted
     * @throws InterruptedException If execution is interrupted
     */
    public String saveUserDetails(User user) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String docName = "id" + user.getUser_id();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("users").document(docName).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }


    /**
     * Returns the given User object corresponding to the given user id if
     * one exists, otherwise returns null.
     *
     * @param userID The user id which corresponds to the user.
     * @return The User object corresponding to the given user id
     * @throws ExecutionException If execution is interrupted
     * @throws InterruptedException If execution is interrupted
     */
    public User getUserDetails(int userID) throws ExecutionException, InterruptedException {
        // First get document reference from specified collection and document
        // Then get the APIFuture of that document
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String documentName = "id" + userID;
        DocumentReference documentReference = dbFirestore.collection("users").document(documentName);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        // Extract DocumentSnapShot from ApiFuture object
        DocumentSnapshot document = future.get();

        User user;
        if (document.exists()) {
            user = document.toObject(User.class);
            return user;
        } else {
            return null;
        }

    }

    public void updateDefaultLang(User user, String newDefaultLang) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String docInfo = "id" + user.getUser_id();
        user.setDefault_lang(newDefaultLang);
        DocumentReference docRef = dbFirestore.collection("users").document(docInfo);

        docRef.update("default_lang", user.getDefault_lang());

    }
    public void updateName(User user, String name) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String docInfo = "id" + user.getUser_id();
        user.setName(name);
        DocumentReference docRef = dbFirestore.collection("users").document(docInfo);
        docRef.update("name", user.getName());
    }
    public void updatePassword(User user, String password) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String docInfo = "id" + user.getUser_id();
        user.setPassword(password);
        DocumentReference docRef = dbFirestore.collection("users").document(docInfo);
        docRef.update("password", user.getPassword());
    }

    public boolean existName(User user) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference usersReference = dbFirestore.collection("users");

        try {
            for (DocumentReference ref : usersReference.listDocuments()) {
                if (Objects.requireNonNull(ref.get().get().getData()).get("name").equals(user.getName())) {
                    return true;
                }
            }
            return false;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }




    /**
     * Returns a list of all the user ids that are currently registered for the app.
     * @return a list of all user ids currently registered for the app.
     * @throws ExecutionException If firebase cannot execute properly
     * @throws InterruptedException If the firebase request gets interrupted
     */
    public List<Integer> getAllUserId() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ArrayList<Integer> ret = new ArrayList<>();
        // asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = db.collection("users").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot query : documents) {
            int uid = Integer.parseInt(Objects.requireNonNull(query.get("user_id")).toString());
            ret.add(uid);
        }
        return ret;
    }

    /**
     * Returns the User with a given username, or returns null if no User has this username.
     */
    public User getByUsername(String username) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference users = db.collection("users");
        // Create a query against the collection.
        Query query = users.whereEqualTo("name", username);
        // retrieve  query results asynchronously using query.get()
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        // Usernames are unique so the querySnapshot should only contain 1 object
        // Create a user object using that information and return the User
        if (!querySnapshot.get().getDocuments().isEmpty()) {
            return querySnapshot.get().getDocuments().get(0).toObject(User.class);
        }
        return null;
    }

    public void addContact(User user, Long contactID) {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        String docName = "id" + user.getUser_id();
        user.getContacts().add(contactID);
        DocumentReference docRef = dbFireStore.collection("users").document(docName);
        docRef.update("contacts", user.getContacts());

    }


    public void deleteContact(User user, Long contactID) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        String docName = "id" + user.getUser_id();
        user.getContacts().remove(contactID);
        DocumentReference docRef = dbFireStore.collection("users").document(docName);
        docRef.update("contacts", user.getContacts());
    }

    public Chat getChatDetails(int chatID) throws ExecutionException, InterruptedException, ParseException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String documentName = "id" + chatID;
        DocumentReference documentReference = dbFirestore.collection("chats").document(documentName);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Convert documentReference to class
            ArrayList<DocumentReference> messageRefs = (ArrayList<DocumentReference>) Objects.requireNonNull(document.getData()).get("messages");
            ArrayList<Message> messages = new ArrayList<>();
            for (DocumentReference messageRef: messageRefs) {
                // Get the document reference of the message's receiver object instance
                DocumentReference receiverRef = (DocumentReference) Objects.requireNonNull(messageRef.get().get().getData()).get("receiver");
                // Convert it into a User class
                User receiver = receiverRef.get().get().toObject(User.class);

                // Get the document reference of the message's receiver object instance
                DocumentReference recipientRef = (DocumentReference) Objects.requireNonNull(messageRef.get().get().getData()).get("recipient");
                // Convert it into a User class
                User recipient = recipientRef.get().get().toObject(User.class);

                int id = ((Long) Objects.requireNonNull(messageRef.get().get().getData()).get("id")).intValue();
                String messageText = (String) Objects.requireNonNull(messageRef.get().get().getData()).get("message");

                String timestamp = Objects.requireNonNull(messageRef.get().get().getData()).get("timestamp").toString();
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(timestamp);

                Message message = new Message(id, messageText, receiver, recipient, date);
                messages.add(message);
            }
            ArrayList<DocumentReference> usersRef = (ArrayList<DocumentReference>) document.getData().get("users");
            ArrayList<User> users = new ArrayList<>();
            for (DocumentReference userRef : usersRef) {
                users.add(userRef.get().get().toObject(User.class));
            }

            Chat targetChat = new Chat(chatID, users);
            targetChat.setMessages(messages);
            return targetChat;

        } else {
            return null;
        }
    }

    public List<Integer> getAllIDs(String collection) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ArrayList<Integer> ret = new ArrayList<>();
        // asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = db.collection(collection).get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot query : documents) {
            int uid = Integer.parseInt(Objects.requireNonNull(query.get("id")).toString());
            ret.add(uid);
        }
        return ret;
    }



    public ArrayList<Message> getAllMessages(int chatID) throws ExecutionException, InterruptedException, ParseException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        String documentName = "id" + chatID;
        DocumentReference documentReference = dbFireStore.collection("chats").document(documentName);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();


        if (document.exists()) {
            // Convert document reference to class
            ArrayList<DocumentReference> messageRefs = (ArrayList<DocumentReference>) Objects.requireNonNull(document.getData()).get("messages");
            ArrayList<Message> messages = new ArrayList<>();
            for (DocumentReference messageRef: messageRefs) {
                // Get the document reference of the message's receiver object instance
                DocumentReference receiverRef = (DocumentReference) Objects.requireNonNull(messageRef.get().get().getData()).get("receiver");
                // Convert it into a User class
                User receiver = receiverRef.get().get().toObject(User.class);

                // Get the document reference of the message's receiver object instance
                DocumentReference recipientRef = (DocumentReference) Objects.requireNonNull(messageRef.get().get().getData()).get("recipient");
                // Convert it into a User class
                User recipient = recipientRef.get().get().toObject(User.class);

                int id = ((Long) Objects.requireNonNull(messageRef.get().get().getData()).get("id")).intValue();
                String messageText = (String) Objects.requireNonNull(messageRef.get().get().getData()).get("message");

                String timestamp = Objects.requireNonNull(messageRef.get().get().getData()).get("timestamp").toString();
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(timestamp);

                Message message = new Message(id, messageText, receiver, recipient, date);
                messages.add(message);
            }

            return messages;
        } else {
            return null;
        }
    }

    public void addChat(Chat chat) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();

        Map<String, Object> docData = new HashMap<>();
        docData.put("id", String.valueOf(chat.getId()));

        ArrayList<DocumentReference> listMessagePaths = new ArrayList<>();
        for (Message message: chat.getMessages()) {
            String messageID = "id" + message.getId();
            DocumentReference messageRef = dbFireStore.collection("messages").document(messageID);
            listMessagePaths.add(messageRef);
        }
        ArrayList<DocumentReference> listUserPaths = new ArrayList<>();
        for (User user : chat.getUsers()) {
            String userID = "id" + user.getUser_id();
            DocumentReference userRef = dbFireStore.collection("users").document(userID);
            listUserPaths.add(userRef);
        }

        docData.put("users", listUserPaths);
        docData.put("messages", listMessagePaths);
        String docName = "id" + chat.getId();
        ApiFuture<WriteResult> collectionsApiFuture = dbFireStore.collection("chats").document(docName).set(docData);
        collectionsApiFuture.get();
    }

    public void addMessage(Message message, Chat chat) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Map<String, Object> docData = new HashMap<>();
        docData.put("id", message.getId());

        String receiverID = "id" + message.getReceiver().getUser_id();
        String recipientID = "id" + message.getRecipient().getUser_id();

        DocumentReference receiverRef = dbFirestore.collection("users").document(receiverID);
        DocumentReference recipientRef = dbFirestore.collection("users").document(recipientID);

        docData.put("message", message.getMessage());
        docData.put("receiver", receiverRef);
        docData.put("recipient", recipientRef);
        docData.put("timestamp", message.getTimestamp());

        String docName = "id" + message.getId();
        DocumentReference messageRef = dbFirestore.collection("messages").document(docName);

        ApiFuture<WriteResult> collectionsApiFuture = messageRef.set(docData);
        collectionsApiFuture.get();

        // Add the message to the chat
        String chatDocName = "id" + chat.getId();
        DocumentReference chatRef = dbFirestore.collection("chats").document(chatDocName);

        chatRef.update("messages", FieldValue.arrayUnion(messageRef));
    }

    public void deleteChat(Integer userID, Integer contactID) {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            List<QueryDocumentSnapshot> docs = dbFirestore.collection("chats").get().get().getDocuments();
            for (QueryDocumentSnapshot query : docs) {
                List<Integer> ids = this.getUserIDsOfChat(query);
                if (ids.contains(userID) && ids.contains(contactID)) {
                    query.getReference().delete();
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Integer> getUserIDsOfChat(QueryDocumentSnapshot query) {
        List<DocumentReference> userRefs = (List<DocumentReference>) query.get("users");
        List<Integer> ids = new ArrayList<>();
        assert userRefs != null;
        for (DocumentReference userRef : userRefs) {
            int id;
            try {
                id = ((Long) Objects.requireNonNull(userRef.get().get().getData()).get("user_id")).intValue();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            ids.add(id);
        }
        return ids;
    }

    public int getChatIDByUsers(int userID, int contactID) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            List<QueryDocumentSnapshot> docs = dbFirestore.collection("chats").get().get().getDocuments();
            for (QueryDocumentSnapshot query : docs) {
                List<Integer> ids = this.getUserIDsOfChat(query);
                if (ids.contains(userID) && ids.contains(contactID)) {
                    return Integer.parseInt((String) Objects.requireNonNull(query.get("id")));
                }
            }
            return -1;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
