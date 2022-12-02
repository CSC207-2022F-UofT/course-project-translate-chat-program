package message_edit_delete_use_case;

/**
 * The interactor class for the message edit use case.
 */

public class MessageEditInteractor implements MessageEditInputBoundary {
    /**
     * The gateway class for the message edit use case.
     */
    private final MessageEditGateway gateway;
    /**
     * The output boundary for the message edit use case.
     */
    private final MessageEditOutputBoundary presenter;
    /**
     * Constructor for MessageEditInteractor.
     *
     * @param gateway  gateway class to access repository.
     * @param presenter presenter class to present result.
     */

    public MessageEditInteractor(MessageEditGateway gateway, MessageEditOutputBoundary presenter){
        this.gateway = gateway;
        this.presenter = presenter;
        }
    /**
     * Message editing method for MessageEditInteractor.
     *
     * @param data   the date class of the message to be edited
     * @return  the response of editing.
     */
    @Override
    public MessageEditResponse editMessage(MessageEditData data){
        if (data.getEditText().isEmpty()){
            return presenter.prepareFailureView("Can't edit with empty text");
        }
        gateway.edit(data);
        MessageEditResponse response = new MessageEditResponse(data.getEditText(), data.getID(), true, null);
        return presenter.prepareSuccessView(response);

    }
}