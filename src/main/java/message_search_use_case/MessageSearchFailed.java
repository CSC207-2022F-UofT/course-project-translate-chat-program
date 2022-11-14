package message_search_use_case;

public class MessageSearchFailed extends RuntimeException {
    /**
     * Constructor for MessageSearchFailed
     * @param error error message
     */
    public MessageSearchFailed(String error) {
        super(error);
    }
}
