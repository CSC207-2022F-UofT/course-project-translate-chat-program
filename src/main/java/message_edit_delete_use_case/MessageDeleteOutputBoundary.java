package message_edit_delete_use_case;
/**
 * The output boundary for the message delete use case.
 */
public interface MessageDeleteOutputBoundary {
    MessageDeleteResponse prepareSuccessView(MessageDeleteResponse response);


}
