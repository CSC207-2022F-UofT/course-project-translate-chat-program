package views.controllers;


import message_edit_delete_use_case.*;

import java.util.concurrent.ExecutionException;

public class MessageDeleteController {
    private final MessageDeleteInputBoundary messageDeleteInputBoundary;
    public MessageDeleteController(MessageDeleteInputBoundary messageDeleteInputBoundary){
        this.messageDeleteInputBoundary = messageDeleteInputBoundary;

    }
    public MessageDeleteResponse delete(MessageDeleteData data) throws ExecutionException, InterruptedException {
        return messageDeleteInputBoundary.messageDelete(data);
    }
}



