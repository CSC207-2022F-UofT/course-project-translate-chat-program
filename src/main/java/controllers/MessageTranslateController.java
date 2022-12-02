package controllers;

import translate_use_case.MessageTranslateData;
import translate_use_case.MessageTranslateInputBoundary;
import translate_use_case.MessageTranslateResponse;


import java.io.IOException;

public class MessageTranslateController {
    final MessageTranslateInputBoundary messageTranslateInputBoundary;

    public MessageTranslateController(MessageTranslateInputBoundary messageTranslateInputBoundary) {
        this.messageTranslateInputBoundary = messageTranslateInputBoundary;
    }

    public MessageTranslateResponse translate(MessageTranslateData data) throws IOException {
        return this.messageTranslateInputBoundary.translate(data);
    }
}
