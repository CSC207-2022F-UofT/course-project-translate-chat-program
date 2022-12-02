package message_edit_delete_use_case;

import java.util.concurrent.ExecutionException;
/**
 * The input boundary for the message delete use case.
 */
public interface MessageDeleteInputBoundary {
    MessageDeleteResponse messageDelete(MessageDeleteData data) throws ExecutionException, InterruptedException;
}
