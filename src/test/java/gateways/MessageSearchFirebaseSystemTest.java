package gateways;

import entities.Message;

import message_search_use_case.MessageSearchData;

import services.DBInitializer;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageSearchFirebaseSystemTest {
    private MessageSearchFirebaseSystem gateway = new MessageSearchFirebaseSystem();
    DBInitializer dbInitializer = new DBInitializer();


    @Test
    void searchFoundMatch() throws FileNotFoundException {
        dbInitializer.init();
        List<Message> listMsg = gateway.search(new MessageSearchData("Bonjour", 1));
        assertEquals(1, listMsg.size());
    }

    @Test
    void searchFoundNoMatch() {
        List<Message> listMsg = gateway.search(new MessageSearchData("Hello!", 1));
        assertEquals(0, listMsg.size());
    }
}