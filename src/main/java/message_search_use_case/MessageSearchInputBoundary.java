package message_search_use_case;

public interface MessageSearchInputBoundary {
    /**
     * Search for messages that match data and return a MessageSearchResponse object.
     * @param data data to match messages with
     * @return MessageSearchResponse object indicating what to show the user
     */
    MessageSearchResponse search(MessageSearchData data);
}
