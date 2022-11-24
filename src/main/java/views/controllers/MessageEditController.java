package views.controllers;

import message_edit_delete_use_case.MessageEditData;
import message_edit_delete_use_case.MessageEditInputBoundary;
import message_edit_delete_use_case.MessageEditResponse;

public class MessageEditController {
    private final MessageEditInputBoundary messageEditInputBoundary;
    public MessageEditController(MessageEditInputBoundary messageEditInputBoundary){
        this.messageEditInputBoundary = messageEditInputBoundary;

    }
    public MessageEditResponse editMessage(MessageEditData data){
        return messageEditInputBoundary.editMessage(data);
    }
}
