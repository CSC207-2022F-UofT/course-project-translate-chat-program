package gateways;

import entities.Message;

import message_search_use_case.MessageSearchData;

import services.DBInitializer;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for MessageSearchFirebaseSystem.
 * @author Muhammad Muzammil
 */
class MessageSearchFirebaseSystemTest {
    private final MessageSearchFirebaseSystem gateway = new MessageSearchFirebaseSystem();
    DBInitializer dbInitializer = new DBInitializer();

    /**
     * Test for search() method - List returned has some number of Message entities.
     * @throws FileNotFoundException if initializer failed to initialize for some reason.
     */
    @Test
    void searchFoundMatch() throws FileNotFoundException {
        dbInitializer.init();
        List<Message> listMsg = gateway.search(new MessageSearchData("He", 0));
        assertEquals(2, listMsg.size());
    }

    /**
     * Test for search() method - List returned is empty.
     */
    @Test
    void searchFoundNoMatch() {
        List<Message> listMsg = gateway.search(new MessageSearchData("Hello!", 1));
        assertEquals(0, listMsg.size());
    }
}