package controllers;

import gateways.MessageSearchFirebaseSystem;
import message_search_use_case.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.MessageSearchPresenter;
import services.DBInitializer;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void searchFailedNoMatch() throws FileNotFoundException {
        initializer.init();
        Exception e = assertThrows(MessageSearchFailed.class, () -> controller.search(new MessageSearchData("jkiojgfhhg", 0)));
        assertEquals("No messages match that search query.", e.getMessage());
    }

    @Test
    void searchFailedBlank() {
        Exception e = assertThrows(MessageSearchFailed.class, () -> controller.search(new MessageSearchData("   ", 0)));
        assertEquals("Search query can't be blank.", e.getMessage());
    }

    @Test
    void searchFailedLessThanOrEqualToFive() {
        Exception e = assertThrows(MessageSearchFailed.class, () -> controller.search(new MessageSearchData("Hel", 0)));
        assertEquals("Search query must be more than 5 characters.", e.getMessage());
    }

    @Test
    void searchSuccess() {
        MessageSearchResponse actualResponse = controller.search(new MessageSearchData("Hello!", 0));
        assertEquals(1, actualResponse.getMessages().size());
        assertEquals("Hello!", actualResponse.getMessages().get(0).get("message"));
    }
}