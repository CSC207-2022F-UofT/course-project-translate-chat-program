package message_search_use_case;

/**
 * Interface for the MessageSearchInteractor to use to inform the view of either a success or failure.
 * @author Muhammad Muzammil
 */
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
