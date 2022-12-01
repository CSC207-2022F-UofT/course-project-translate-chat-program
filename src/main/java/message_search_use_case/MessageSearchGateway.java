package message_search_use_case;

import entities.Message;

import java.util.List;

/**
 * Interface for the gateway which will allow the MessageSearchInteractor to get the messages that match the search data.
 * @author Muhammad Muzammil
 */
public interface MessageSearchGateway {
    /**
     * Searches for messages in database that match text in data
     * @param data MessageSearchData object that tells method which messages to look for
     * @return list of Message objects that match data
     */
    List<Message> search(MessageSearchData data);
}
