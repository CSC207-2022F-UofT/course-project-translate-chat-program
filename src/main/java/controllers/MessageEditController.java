package controllers;

import message_edit_delete_use_case.MessageEditData;
import message_edit_delete_use_case.MessageEditInputBoundary;

public class MessageEditController {
    private final MessageEditInputBoundary messageEditInputBoundary;
    public MessageEditController(MessageEditInputBoundary messageEditInputBoundary){
        this.messageEditInputBoundary = messageEditInputBoundary;

    }
    public void editMessage(MessageEditData data){
        messageEditInputBoundary.editMessage(data);
    }
}
