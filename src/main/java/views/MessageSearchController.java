package views;

import message_search_use_case.MessageSearchData;
import message_search_use_case.MessageSearchInputBoundary;
import message_search_use_case.MessageSearchResponse;

/**
 * Controller class that is used by the view to get the result of a search.
 * @author Muhammad Muzammil
 */
public class MessageSearchController {
    final MessageSearchInputBoundary messageSearchInput;

    public MessageSearchController(MessageSearchInputBoundary messageSearchInput) {
        this.messageSearchInput = messageSearchInput;
    }

    MessageSearchResponse search(MessageSearchData data) {
        return this.messageSearchInput.search(data);
    }
}
