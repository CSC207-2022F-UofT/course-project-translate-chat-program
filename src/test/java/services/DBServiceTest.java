package services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class DBServiceTest {
    private static Firestore db;
    private static DBService dbs;

    @BeforeAll
    static void init() throws FileNotFoundException {
        DBInitializer init = new DBInitializer();
        init.init();
        db = FirestoreClient.getFirestore();
        dbs = new DBService();
    }

    // Sets up a fake user in the database before each test
    @BeforeEach
    void setup() throws InterruptedException {
        User newUser = new User("NewUser", "english", "email@email.com", "password",1);
        newUser.setUser_id(6);
        db.collection("users").document("id6").set(newUser);
        // This just pauses execution for 1 second so that the database
        // can actually update, otherwise the tests run before the database
        // actually reflects the changes
        Thread.sleep(1000);
    }
    // Tears down the fake user after each test is completed.
    @AfterEach
    void tearDown() throws ExecutionException, InterruptedException {
        db.collection("users").document("id6").delete();
        db.collection("users").document("id7").delete();
    }

    @Test
    void saveUserDetails() throws ExecutionException, InterruptedException {
        User newUser = new User("NewUser2", "english", "email", "password",1);
        newUser.setUser_id(7);
        dbs.saveUserDetails(newUser);
        assert !db.collection("users").whereEqualTo("name", "NewUser").get().get().isEmpty();
    }

    @Test
    void getAllUserId() throws ExecutionException, InterruptedException {
        List<Integer> ids = dbs.getAllUserId();
        System.out.println("filler");
        assertTrue(ids.contains(6));
    }

    @Test
    void getByUsername() throws ExecutionException, InterruptedException {
        User user = dbs.getByUsername("NewUser");
        assertEquals(user.getName(), "NewUser");
        assertEquals(user.getDefault_lang(), "english");
        assertEquals(user.getEmail(), "email@email.com");
        assertEquals(user.getPassword(), "password");
        assertEquals(user.getUser_id(), 6);
    }
}