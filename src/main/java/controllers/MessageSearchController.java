package controllers;

import message_search_use_case.MessageSearchData;
import message_search_use_case.MessageSearchInputBoundary;
import message_search_use_case.MessageSearchResponse;

public class MessageSearchController {
    final MessageSearchInputBoundary messageSearchInput;

    public MessageSearchController(MessageSearchInputBoundary messageSearchInput) {
        this.messageSearchInput = messageSearchInput;
    }

    public MessageSearchResponse search(MessageSearchData data) {
        return this.messageSearchInput.search(data);
    }
}
