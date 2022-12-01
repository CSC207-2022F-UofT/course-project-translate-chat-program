package message_search_use_case;

import gateways.MessageSearchFirebaseSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DBInitializer;
import views.MessageSearchPresenter;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for MessageSearchInteractor.
 * @author Muhammad Muzammil
 */
class MessageSearchInteractorTest {
    private MessageSearchInteractor interactor;
    DBInitializer initializer = new DBInitializer();

    @BeforeEach
    void setUp() {
        MessageSearchGateway gateway = new MessageSearchFirebaseSystem();
        MessageSearchOutputBoundary presenter = new MessageSearchPresenter();
        this.interactor = new MessageSearchInteractor(gateway, presenter);
    }

    /**
     * Test for search() method - Failure when no messages match the search data.
     * @throws FileNotFoundException if initializer failed to initialize for some reason.
     */
    @Test
    void searchFailedNoMatch() throws FileNotFoundException {
        initializer.init();
        Exception e = assertThrows(MessageSearchFailed.class, () -> interactor.search(new MessageSearchData("jkiojgfhhg", 0)));
        assertEquals("No messages match that search query.", e.getMessage());
    }

    /**
     * Test for search() method - Failure when search data is just blank.
     */
    @Test
    void searchFailedBlank() {
        Exception e = assertThrows(MessageSearchFailed.class, () -> interactor.search(new MessageSearchData("   ", 0)));
        assertEquals("Search query can't be blank.", e.getMessage());
    }

    /**
     * Test for search() method - Failure when search data is too short.
     */
    @Test
    void searchFailedLessThanOrEqualToFive() {
        Exception e = assertThrows(MessageSearchFailed.class, () -> interactor.search(new MessageSearchData("Bonj", 0)));
        assertEquals("Search query must be more than 5 characters.", e.getMessage());
    }

    /**
     * Test for search() method - Success
     */
    @Test
    void searchSuccess() {
        MessageSearchResponse actualResponse = interactor.search(new MessageSearchData("Hello!", 0));
        assertEquals(1, actualResponse.getMessages().size());
        assertEquals("Hello!", actualResponse.getMessages().get(0).getMessage());
        assertEquals(0, actualResponse.getMessages().get(0).getId());
    }


}