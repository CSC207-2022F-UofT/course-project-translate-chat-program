package message_edit_delete_use_case;

import shared.Response;
/**
 * The response class for the message edit use case.
 */
public class MessageEditResponse extends Response {
    /**
     * Edited version of message.
     */

    private final String editedText;
    /**
     * Edited version of the message.
     */
    private final int id;
    /**
     * Constructor for MessageEditInteractor.
     *
     * @param editedText  Edited version of the Message to be edited.
     * @param id Message ID to be edited
     * @param success result of edit process
     * @param e exception
     */

    public MessageEditResponse(String editedText, int id, boolean success, Exception e) {
        this.editedText = editedText;
        this.id = id;
        this.success = success;
        this.e = e;
    }
    /**
     * Getter method for EditedText.
     *
     * @return edited version of the message to be edited.
     */
    public String getEditedText(){
        return this.editedText;
    }
    /**
     * Getter method for Message ID.
     *
     * @return id of message to be edited.
     */
    public int getID(){
        return this.id;
    }
    /**
     * Getter method for exception.
     *
     * @return exception
     */
    @Override
    public Exception getException() {
        return super.e;
    }
}
