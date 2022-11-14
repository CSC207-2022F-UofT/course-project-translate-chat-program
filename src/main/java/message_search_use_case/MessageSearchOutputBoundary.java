package message_search_use_case;

public interface MessageSearchOutputBoundary {

    /**
     * Method called when search was successful.
     * @param response the MessageSearchResponse that needs to be mutated and returned
     * @return the mutated MessageSearchResponse
     */
    MessageSearchResponse prepareSuccessView(MessageSearchResponse response);

    /**
     * Method called when search fails.
     * @param error error message that was raised
     * @throws MessageSearchFailed if search could not be made
     */
    MessageSearchResponse prepareFailView(String error);
}
