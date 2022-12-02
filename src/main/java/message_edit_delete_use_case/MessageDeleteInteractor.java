package message_edit_delete_use_case;

import java.util.concurrent.ExecutionException;
/**
 * The interactor class for the message delete use case.
 */
public class MessageDeleteInteractor  implements MessageDeleteInputBoundary{
    /**
     * The gateway class for the message delete use case.
     */
    private final MessageDeleteGateway gateway;
    /**
     * The output boundary for the message delete use case.
     */
    private final MessageDeleteOutputBoundary presenter;
    /**
     * Constructor for MessageDeleteInteractor.
     *
     * @param gateway  gateway class to access repository.
     * @param presenter presenter class to present result.
     */
    public MessageDeleteInteractor(MessageDeleteGateway gateway, MessageDeleteOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }
    /**
     * Message deleting method for MessageDeleteInteractor.
     *
     * @param data   the data class of the message to be deleted
     * @return  the response of deletion.
     */
    @Override
    public MessageDeleteResponse messageDelete(MessageDeleteData data) throws ExecutionException, InterruptedException {
        gateway.delete(data);
        MessageDeleteResponse response = new MessageDeleteResponse(data.getMessageID(), true, null);
        return presenter.prepareSuccessView(response);
    }
}
