import gateways.UserLoginFirebaseSystem;
import gateways.UserRegistrationFirebaseSystem;
import org.jetbrains.annotations.NotNull;
import services.DBInitializer;
import services.DBService;
import user_login_use_case.LoginInputBoundary;
import user_login_use_case.UserLoginGateway;
import user_login_use_case.UserLoginInteractor;
import user_register_use_case.UserRegistrationGateway;
import user_register_use_case.UserRegistrationInteractor;
import views.*;
import user_register_use_case.UserRegisterInputBoundary;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Set up Database
        try {
            new DBInitializer().init();
        } catch (Exception ignored) {
        }

        CardLayout cardLayout = new CardLayout();
        JPanel screens = new JPanel(cardLayout);
        JFrame application = new JFrame("Translation App");
        application.add(screens);

        Navigator nav = new CardLayoutNavigator(cardLayout, screens);
        DBService dbs = new DBService();

        // Initialize screens
        JPanel registerScreen = initRegisterScreen(nav, dbs);
        JPanel loginSreen = initLoginScreen(nav, dbs);
        // Add screens to the card layout
        screens.add(registerScreen, "register");
        screens.add(loginSreen, "login");

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(640, 640);
        application.setVisible(true);
        nav.showScreen("register");
    }
    @NotNull
    private static JPanel initRegisterScreen(Navigator nav, DBService db) {
        UserRegistrationGateway userFactory = new UserRegistrationFirebaseSystem(db);
        UserRegisterPresenter presenter = new UserRegisterPresenter();
        UserRegisterInputBoundary interactor = new UserRegistrationInteractor(userFactory, presenter);
        UserRegisterController userRegisterController = new UserRegisterController(interactor);

        HashMap<String, String> langs = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/languages.txt"))) {
            String line = br.readLine();

            while (line != null) {
                String[] langCode = line.split(" ");
                langCode[0] = langCode[0].strip();
                langCode[1] = langCode[1].strip();
                langs.put(langCode[0], langCode[1]);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RegisterScreen(langs, userRegisterController, nav);
    }
    @NotNull
    private static JPanel initLoginScreen(Navigator nav, DBService db) {
        UserLoginGateway auth = new UserLoginFirebaseSystem(db);
        LoginPresenter presenter = new LoginPresenter();
        LoginInputBoundary interactor = new UserLoginInteractor(auth, presenter);
        LoginController userLoginController = new LoginController(interactor);

        return new LoginScreen(userLoginController, nav);
    }

}
