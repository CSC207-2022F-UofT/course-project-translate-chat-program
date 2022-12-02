package presenters;

import message_edit_delete_use_case.MessageEditFailed;
import message_edit_delete_use_case.MessageEditResponse;
import message_edit_delete_use_case.MessageEditOutputBoundary;
/**
 * The presenter class for the message edit use case.
 */
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
