package controllers;

import entities.User;
import gateways.CustomizationFirebaseSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.CustomizationPresenter;
import profile_customization_use_case.*;
import services.DBInitializer;
import services.DBService;
import shared.UserDetails;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CustomizationControllerTest {

    private CustomizationController controller;
    DBInitializer initializer = new DBInitializer();
    DBService dbService = DBService.getInstance();

    @BeforeEach
    void setUp() {
        CustomizationGateway gateway = new CustomizationFirebaseSystem();
        CustomizationOutputBoundary presenter = new CustomizationPresenter();
        CustomizationInputBoundary interactor = new CustomizationInteractor(gateway, presenter);
        this.controller = new CustomizationController(interactor);
    }

    @Test
    void changeLanguageSuccess() throws ExecutionException, InterruptedException {
        User user = dbService.getUserDetails(8);
        UserDetails details = new UserDetails(user.getName(), user.getUser_id(), user.getDefault_lang(), new ArrayList<>());
        CustomizationResponse response = controller.changeLanguage(user.getName(), "fr", user.getPassword(), details);
        Assertions.assertEquals("fr", response.getDefaultLanguage());
    }

    @Test
    void changeLanguageFailBlank() throws ExecutionException, InterruptedException {
        User user = dbService.getUserDetails(8);
        UserDetails details = new UserDetails(user.getName(), user.getUser_id(), user.getDefault_lang(), new ArrayList<>());
        Exception e = Assertions.assertThrows(CustomizationFailed.class, () -> controller.changeLanguage(user.getName(), " ", user.getPassword(), details));
        Assertions.assertEquals("Please enter a language", e.getMessage());
    }

    @Test
    void changeNameSuccess() throws ExecutionException, InterruptedException {
        User user = dbService.getUserDetails(8);
        UserDetails details = new UserDetails(user.getName(), user.getUser_id(), user.getDefault_lang(), new ArrayList<>());
        CustomizationResponse response = controller.changeName("name", user.getDefault_lang(), user.getPassword(), details);
        Assertions.assertEquals("name", response.getName());
    }

    @Test
    void changeNameFailBlank() throws ExecutionException, InterruptedException {
        User user = dbService.getUserDetails(8);
        UserDetails details = new UserDetails(user.getName(), user.getUser_id(), user.getDefault_lang(), new ArrayList<>());
        Exception e = Assertions.assertThrows(CustomizationFailed.class, () -> controller.changeName(" ", user.getDefault_lang(), user.getPassword(), details));
        Assertions.assertEquals("Please enter a name", e.getMessage());
    }

    @Test
    void changeNameFailExist() throws ExecutionException, InterruptedException {
        User user = dbService.getUserDetails(8);
        UserDetails details = new UserDetails(user.getName(), user.getUser_id(), user.getDefault_lang(), new ArrayList<>());
        Exception e = Assertions.assertThrows(CustomizationFailed.class, () -> controller.changeName("danny", user.getDefault_lang(), user.getPassword(), details));
        Assertions.assertEquals("Name already taken, please enter another name", e.getMessage());
    }

    @Test
    void changePasswordSuccess() throws ExecutionException, InterruptedException {
        User user = dbService.getUserDetails(8);
        UserDetails details = new UserDetails(user.getName(), user.getUser_id(), user.getDefault_lang(), new ArrayList<>());
        CustomizationResponse response = controller.changePassword(user.getName(), user.getDefault_lang(), "qwertyui", details);
        Assertions.assertEquals("qwertyui", response.getPassword());
    }

    @Test
    void changePasswordFailBlank() throws FileNotFoundException, ExecutionException, InterruptedException {
        initializer.init();
        User user = dbService.getUserDetails(8);
        UserDetails details = new UserDetails(user.getName(), user.getUser_id(), user.getDefault_lang(), new ArrayList<>());
        Exception e = Assertions.assertThrows(CustomizationFailed.class, () -> controller.changePassword(user.getName(), user.getDefault_lang(), " ", details));
        Assertions.assertEquals("Please enter a password", e.getMessage());
    }

    @Test
    void changePasswordFailTooShort() throws ExecutionException, InterruptedException {
        User user = dbService.getUserDetails(8);
        UserDetails details = new UserDetails(user.getName(), user.getUser_id(), user.getDefault_lang(), new ArrayList<>());
        Exception e = Assertions.assertThrows(CustomizationFailed.class, () -> controller.changePassword(user.getName(), user.getDefault_lang(), "qwe", details));
        Assertions.assertEquals("Please enter a password longer than 7 characters", e.getMessage());
    }
}
