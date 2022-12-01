package message_search_use_case;

import entities.Message;

import java.util.List;

/**
 * Implementation of MessageSearchInputBoundary that searches for messages, based on some data.
 * @author Muhammad Muzammil
 */
public class MessageSearchInteractor implements MessageSearchInputBoundary {

    final MessageSearchGateway gateway;
    final MessageSearchOutputBoundary presenter;

    /**
     * Constructor for MessageSearchInteractor.
     * @param gateway database to access messages
     * @param presenter presenter to let UI know what it should do
     */
    public MessageSearchInteractor(MessageSearchGateway gateway, MessageSearchOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    /**
     * Search for messages that match data and return a MessageSearchResponse object.
     * @param data data to match messages with
     * @return MessageSearchResponse object indicating what to show the user
     */
    @Override
    public MessageSearchResponse search(MessageSearchData data) {
        if (data.getText().isBlank()) {
            return presenter.prepareFailView("Search query can't be blank.");
        } else if (data.getText().length() <= 5) {
            return presenter.prepareFailView("Search query must be more than 5 characters.");
        }
        List<Message> messages = gateway.search(data);

        if (messages.isEmpty()) {
            return presenter.prepareFailView("No messages match that search query.");
        }

        MessageSearchResponse response = new MessageSearchResponse(data.getText(), messages, true, null);
        return presenter.prepareSuccessView(response);
    }
}
