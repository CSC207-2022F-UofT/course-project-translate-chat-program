package presenters;

import message_edit_delete_use_case.MessageDeleteOutputBoundary;
import message_edit_delete_use_case.MessageDeleteResponse;


public class MessageDeletePresenter implements MessageDeleteOutputBoundary {
    @Override
    public MessageDeleteResponse prepareSuccessView(MessageDeleteResponse response){
        return response;
    }
}
