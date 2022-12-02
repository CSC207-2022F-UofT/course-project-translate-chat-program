package message_edit_delete_use_case;
/**
 * A data for message to be deleted with a message id and chat id.
 */

public class MessageDeleteData {
    /**
     * Message id to be deleted
     */
    private final int messageID;
    /**
     * Chat id  that message to be deleted is in.
     */
    private final int chatID;
    /**
     * Constructor for MessageDeleteData.
     *
     * @param messageID   the message ID to be deleted
     * @param chatID the id of the chat that message to be deleted is in.
     */

    public MessageDeleteData(int messageID, int chatID) {
        this.messageID = messageID;
        this.chatID = chatID;
    }
    /**
     * Getter method for message ID
     *
     * @return id of the message ID to be deleted.
     */

    public int getMessageID() {
        return messageID;
    }
    /**
     * Getter method for chat ID.
     *
     * @return id of chat the message to be deleted is in.
     */

    public int getChatID() {
        return chatID;
    }
}
