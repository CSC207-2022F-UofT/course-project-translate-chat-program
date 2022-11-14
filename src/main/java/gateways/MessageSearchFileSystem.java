package gateways;

import message_search_use_case.MessageSearchData;
import message_search_use_case.MessageSearchGateway;

import java.util.List;

public class MessageSearchFileSystem implements MessageSearchGateway {

    /**
     * Searches for messages in file system that match text in data
     * @param data MessageSearchData object that tells method which messages to look for
     * @return list of Message objects that match data
     */
    @Override
    public List<String> search(MessageSearchData data) {
        //TODO: implement method, change return type to List<Message>
        return null;
    }
}
