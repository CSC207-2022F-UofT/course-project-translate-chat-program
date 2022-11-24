package views.controllers;

import entities.User;
import profile_customization_use_case.CustomizationData;
import profile_customization_use_case.CustomizationInputBoundary;

public class CustomizationController {
    private final CustomizationInputBoundary cib;

    public CustomizationController(CustomizationInputBoundary cib) {
        this.cib = cib;
    }

    public void changeLanguage(String name, String default_lang, String password, User user) {
        CustomizationData data = new CustomizationData(name, default_lang, password, user);
        this.cib.changeLanguage(data);
    }
    public void changeName(String name, String default_lang, String password, User user) {
        CustomizationData data = new CustomizationData(name, default_lang, password, user);
        this.cib.changeName(data);
    }
    public void changePassword(String name, String default_lang, String password, User user) {
        CustomizationData data = new CustomizationData(name, default_lang, password, user);
        this.cib.changePassword(data);
    }
}