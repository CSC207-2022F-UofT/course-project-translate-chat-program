package user_send_message;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface MessageInputBoundary {
    /**
     * Creates and sends a message
     * @param senderID needed to create message instance
     * @return the response from the interactor
     */
    SendMessageResponse sendMessage(int chatID, String message, int senderID, int receiverID, Date timestamp) throws ExecutionException, InterruptedException, ParseException;

    /**
     * Fetches all the messages in a specific chat ID
     * @param chatID ID of the chat
     * @return Map of all messages in the chat, with sender's name as key and text of the message as the value
     */
    ArrayList<Map<String, Object>> getAllMessages(int chatID);

    /**
     * Fetches the chatID given two user IDs
     * @param userID ID of the main user
     * @param contactID ID of the other user
     * @return The chatID of the chat used by two users
     */
    int getChatIDByUsers(int userID, int contactID);
}