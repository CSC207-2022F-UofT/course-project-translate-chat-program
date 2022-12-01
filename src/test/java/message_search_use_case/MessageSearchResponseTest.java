package message_search_use_case;

import entities.Message;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for MessageSearchResponse
 * @author Muhammad Muzammil
 */
class MessageSearchResponseTest {
    private MessageSearchResponse response;
    private List<Message> listMsg;


    @BeforeEach
    void setUp() {
        listMsg = new ArrayList<>();
        listMsg.add(new Message(456, "Hello! How are you?",
                new User("John", "en", "john@gmail.com", "4567", 4567),
                new User("Jacques", "fr", "jacques@gmail.com", "12345", 12234),
                new Date(126374853)));
        this.response = new MessageSearchResponse("Hello", listMsg, true, null);
    }

    /**
     * Test for getText() method.
     */
    @Test
    void getText() {
        assertEquals("Hello", response.getText());
    }

    /**
     * Test for getException() method
     */
    @Test
    void getException() {
        assertNull(response.getException());
    }

    /**
     * Test for getMessages() method.
     */
    @Test
    void getMessages() {
        assertEquals(listMsg, response.getMessages());
    }
}