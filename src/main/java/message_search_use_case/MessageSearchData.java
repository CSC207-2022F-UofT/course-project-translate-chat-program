package message_search_use_case;

public class MessageSearchData {
    private final String text;
    private final int chatId;

    /**
     * Constructor for MessageSearchData.
     *
     * @param text   the text to be searched
     * @param chatId the id of the chat being searched in
     */
    public MessageSearchData(String text, int chatId) {
        this.text = text;
        this.chatId = chatId;
    }

    /**
     * Getter method for text.
     * @return text attribute for this MessageSearchData object.
     */
    public String getText() {
        return text;
    }

    /**
     * Getter method for chatId.
     * @return chatId attribute for this MessageSearchData object
     */
    public int getChatId() {
        return chatId;
    }
}
