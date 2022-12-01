package views;

import gateways.MessageSearchFirebaseSystem;
import message_search_use_case.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DBInitializer;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for MessageSearchController
 * @author Muhammad Muzammil
 */
class MessageSearchControllerTest {
    private MessageSearchController controller;
    DBInitializer initializer = new DBInitializer();

    @BeforeEach
    void setUp() {
        MessageSearchGateway gateway = new MessageSearchFirebaseSystem();
        MessageSearchOutputBoundary presenter = new MessageSearchPresenter();
        MessageSearchInputBoundary interactor = new MessageSearchInteractor(gateway, presenter);
        this.controller = new MessageSearchController(interactor);
    }

    /**
     * Test for search() method - Failure when no messages match the search data.
     * @throws FileNotFoundException if initializer fails to initialize for some reason.
     */
    @Test
    void searchFailedNoMatch() throws FileNotFoundException {
        initializer.init();
        Exception e = assertThrows(MessageSearchFailed.class, () -> controller.search(new MessageSearchData("jkiojgfhhg", 0)));
        assertEquals("No messages match that search query.", e.getMessage());
    }

    /**
     * Test for search() method - Failure when search data is just blank.
     */
    @Test
    void searchFailedBlank() {
        Exception e = assertThrows(MessageSearchFailed.class, () -> controller.search(new MessageSearchData("   ", 0)));
        assertEquals("Search query can't be blank.", e.getMessage());
    }

    /**
     * Test for search() method - Failure when search data is too short.
     */
    @Test
    void searchFailedLessThanOrEqualToFive() {
        Exception e = assertThrows(MessageSearchFailed.class, () -> controller.search(new MessageSearchData("Hel", 0)));
        assertEquals("Search query must be more than 5 characters.", e.getMessage());
    }

    /**
     * Test for search() method - Success
     */
    @Test
    void searchSuccess() {
        MessageSearchResponse actualResponse = controller.search(new MessageSearchData("Hello!", 0));
        assertEquals(1, actualResponse.getMessages().size());
        assertEquals("Hello!", actualResponse.getMessages().get(0).getMessage());
        assertEquals(0, actualResponse.getMessages().get(0).getId());
    }
}