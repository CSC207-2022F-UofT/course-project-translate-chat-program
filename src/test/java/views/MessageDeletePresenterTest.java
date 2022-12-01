package views;

import entities.Message;
import entities.User;
import message_edit_delete_use_case.MessageDeleteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.MessageDeletePresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageDeletePresenterTest {
    private MessageDeletePresenter presenter;

    @BeforeEach
    void setUp() {
        this.presenter = new MessageDeletePresenter();
    }

    @Test
    void prepareSuccessView() {
        List<Message> listMsg = new ArrayList<>();
        listMsg.add(new Message(456, "Hello! How are you?",
                new User("John", "en", "john@gmail.com", "4567", 4567),
                new User("Jacques", "fr", "jacques@gmail.com", "12345", 12234),
                new Date(126374853)));
        MessageDeleteResponse response = new MessageDeleteResponse(4567, true, null);
        assertEquals(response, presenter.prepareSuccessView(response));
    }
}

