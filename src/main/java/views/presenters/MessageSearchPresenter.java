package views.presenters;

import message_search_use_case.MessageSearchFailed;
import message_search_use_case.MessageSearchOutputBoundary;
import message_search_use_case.MessageSearchResponse;

public class MessageSearchPresenter implements MessageSearchOutputBoundary {

    /**
     * Method called when search was successful.
     * @param response the MessageSearchResponse that needs to be mutated and returned
     * @return the mutated MessageSearchResponse
     */
    @Override
    public MessageSearchResponse prepareSuccessView(MessageSearchResponse response) {
        return response;
    }

    /**
     * Method called when search fails.
     * @param error error message that was raised
     * @throws MessageSearchFailed if...
     */
    @Override
    public MessageSearchResponse prepareFailView(String error) {
        throw new MessageSearchFailed(error);
    }
}
