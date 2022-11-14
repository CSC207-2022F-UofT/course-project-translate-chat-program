package gateways;

import message_search_use_case.MessageSearchData;
import message_search_use_case.MessageSearchGateway;



import java.util.List;

public class MessageSearchFirebaseSystem implements MessageSearchGateway {
    /**
     * Searches for messages in Google Cloud database that match text in data
     * @param data MessageSearchData object that tells method which messages to look for
     * @return list of Message objects that match data
     */
    @Override
    public List<String> search(MessageSearchData data) {

        return null;
    }
}
