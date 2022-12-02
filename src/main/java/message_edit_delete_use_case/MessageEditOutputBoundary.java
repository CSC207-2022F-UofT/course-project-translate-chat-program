package message_edit_delete_use_case;
/**
 * The output boundary for the message edit use case.
 */

public interface MessageEditOutputBoundary {
    MessageEditResponse prepareSuccessView(MessageEditResponse response);

    MessageEditResponse prepareFailureView(String error);
}
