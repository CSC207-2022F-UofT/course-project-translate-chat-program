package views;


import message_edit_delete_use_case.MessageEditFailed;
import message_edit_delete_use_case.MessageEditResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.MessageEditPresenter;

import static org.junit.jupiter.api.Assertions.*;

class MessageEditPresenterTest {
    private MessageEditPresenter presenter;

    @BeforeEach
    void setUp() {
        this.presenter = new MessageEditPresenter();
    }

    @Test
    void prepareSuccessView() {
        MessageEditResponse response = new MessageEditResponse("bye", 3, true, null);
        assertEquals(response, presenter.prepareSuccessView(response));
    }

    @Test
    void prepareFailureView() {
        String error = "There is an error!";
        Exception e = assertThrows(MessageEditFailed.class, () -> presenter.prepareFailureView(error));
        assertEquals(error, e.getMessage());
    }
}