package message_edit_delete_use_case;
/**
 * The input boundary for the message edit use case.
 */
public interface MessageEditInputBoundary {
    MessageEditResponse editMessage(MessageEditData data);
}
