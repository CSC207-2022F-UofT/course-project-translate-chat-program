package views.controllers;

/**
 * A facade for the many controllers needed in the HomeScreen
 */
public class HomeScreenController {
    private final LogoutController logoutController;
    private final SendMessageController sendMessageController;
    private final AudioConvertController audioConvertController;
    private final AudioRecorderController audioRecorderController;
    private final MessageSearchController messageSearchController;
    private final MessageEditController messageEditController;
    private final MessageDeleteController messageDeleteController;
    private final MessageTranslateController messageTranslateController;

    public HomeScreenController(LogoutController logoutController, SendMessageController sendMessageController, AudioConvertController audioConvertController, AudioRecorderController audioRecorderController, MessageSearchController messageSearchController, MessageEditController messageEditController, MessageDeleteController messageDeleteController, MessageTranslateController messageTranslateController) {
        this.logoutController = logoutController;
        this.sendMessageController = sendMessageController;
        this.audioConvertController = audioConvertController;
        this.audioRecorderController = audioRecorderController;
        this.messageSearchController = messageSearchController;
        this.messageEditController = messageEditController;
        this.messageDeleteController = messageDeleteController;
        this.messageTranslateController = messageTranslateController;
    }
}
