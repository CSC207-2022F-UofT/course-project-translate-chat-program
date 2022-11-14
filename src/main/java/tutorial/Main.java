package tutorial;

import entities.User;
import services.DBInitializer;
import services.DBService;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static DBService dbService = new DBService();

    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {
        DBInitializer dbInitializer = new DBInitializer();
        dbInitializer.init();
//        System.out.println("s");
//        System.out.println(Main.getUser(1));

    }

    public static User getUser(int userID) throws ExecutionException, InterruptedException {
        try {
            return dbService.getUserDetails(userID);
        } catch (Exception e) {
            System.err.println("error");
        }
        return null;
    }

    public static String addUser(User user) throws ExecutionException, InterruptedException {
        try {
            return dbService.saveUserDetails(user);
        } catch (Exception e) {
            System.err.println("error");
        }
        return null;
    }
}

