package message_search_use_case;

/**
 * Represents the exception that will be thrown whenever a message search fails
 * @author Muhamamd Muzammil
 */
public class MessageSearchFailed extends RuntimeException {
    /**
     * Constructor for MessageSearchFailed
     * @param error error message
     */
    public MessageSearchFailed(String error) {
        super(error);
    }
}
