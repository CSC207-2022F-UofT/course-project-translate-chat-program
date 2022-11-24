package views.presenters;

import message_edit_delete_use_case.MessageEditFailed;
import message_edit_delete_use_case.MessageEditResponse;
import message_edit_delete_use_case.MessageEditOutputBoundary;

public class MessageEditPresenter implements MessageEditOutputBoundary {
    @Override
    public MessageEditResponse prepareSuccessView(MessageEditResponse response) {
        return response;
    }

    @Override
    public MessageEditResponse prepareFailureView(String error) {
        throw new MessageEditFailed(error);
    }
}
