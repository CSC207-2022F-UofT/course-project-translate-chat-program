package views;

import entities.Message;
import entities.User;
import message_search_use_case.MessageSearchFailed;
import message_search_use_case.MessageSearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.MessageSearchPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for MessageSearchPresenter
 * @author Muhammad Muzammil
 */
class MessageSearchPresenterTest {
    private MessageSearchPresenter presenter;

    @BeforeEach
    void setUp() {
        this.presenter = new MessageSearchPresenter();
    }

    /**
     * Test for prepareSuccessView().
     */
    @Test
    void prepareSuccessView() {
        List<Message> listMsg = new ArrayList<>();
        listMsg.add(new Message(456, "Hello! How are you?",
                new User("John", "en", "john@gmail.com", "4567", 4567),
                new User("Jacques", "fr", "jacques@gmail.com", "12345", 12234),
                new Date(126374853)));
        MessageSearchResponse response = new MessageSearchResponse("Hello!", listMsg, true, null);
        assertEquals(response, presenter.prepareSuccessView(response));
    }

    /**
     * Test for prepareFailView().
     */
    @Test
    void prepareFailView() {
        String error = "There has been an error!";
        Exception e = assertThrows(MessageSearchFailed.class, () -> presenter.prepareFailView(error));
        assertEquals(error, e.getMessage());
    }
}