package message_edit_delete_use_case;
/**
 * A exception class for catching failure.
 */


public class MessageEditFailed extends RuntimeException{
    public MessageEditFailed(String error){
        super(error);
    }
}
