package message_edit_delete_use_case;
import shared.Response;

/**
 * The response class for the message delete use case.
 */
public class MessageDeleteResponse extends Response {
    /**
     * Message ID to be deleted.
     */
    private final int messageID;

    /**
     * Constructor for MessageDeleteInteractor.
     *
     * @param messageID  Message ID to be deleted.
     * @param success result of deletion process
     * @param e exception
     */
    public MessageDeleteResponse(int messageID, boolean success, Exception e) {
        this.messageID = messageID;
        this.success = success;
        this.e = e;
    }
    /**
     * Getter method for Message ID.
     *
     * @return id of message to be deleted.
     */

    public int getMessageID() {
        return messageID;
    }
    /**
     * Getter method for exception.
     *
     * @return exception.
     */
    @Override
    public Exception getException(){
        return super.e;
    }
}
