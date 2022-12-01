package message_search_use_case;

import entities.Message;
import shared.Response;

import java.util.List;

/**
 * Response model that will be the return value of the MessageSearchController's search method. This will allow the view
 * to have a list of Message entities to display as a search result.
 * @author Muhammad Muzammil
 */
public class MessageSearchResponse extends Response {
    private final String text;
    private final List<Message> messages;

    /**
     * Constructor for MessageSearchResponse.
     * @param text text that was searched
     * @param success whether message search was successful
     * @param e exception that was thrown if success is false, null otherwise
     */
    public MessageSearchResponse(String text, List<Message> messages, boolean success, Exception e) {
        this.text = text;
        this.messages = messages;
        this.success = success;
        this.e = e;
    }

    /**
     * Getter method for text.
     * @return the text attribute of this MessageSearchResponse object\
     */
    public String getText() {
        return this.text;
    }

    /**
     * Getter method for e.
     * @return the e (exception) attribute for this MessageSearchResponse object
     */
    public Exception getException() {
        return super.e;
    }

    /**
     * Getter method for messages.
     * @return the messages attribute of this MessageSearchResponse object\
     */
    public List<Message> getMessages() {
        return messages;
    }
}
