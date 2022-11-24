package views;

import user_login_use_case.LoginData;
import user_login_use_case.LoginResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Frameworks/Drivers layer

/**
 * A basic registration screen with text fields and two buttons, the "Sign Up" button will
 * attempt to create an account while the "login" button will navigate to the login page.
 */
public class LoginScreen extends JPanel implements ActionListener {
    /**
     * The username chosen by the user
     */
    HintTextField username = new HintTextField("Enter Username...");
    /**
     * The password
     */
    HintPasswordField password = new HintPasswordField("Enter Password...  ");
    LoginController controller;
    Navigator nav;


    /**
     * A window with a title and a JButton
     */
    public LoginScreen(LoginController controller,Navigator nav) {
        this.controller = controller;
        this.nav = nav;

        JLabel title = new JLabel("Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        username.setBorder(BorderFactory.createTitledBorder("Username"));
        password.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton signUp = new JButton("Sign up");
        JButton login = new JButton("Login");

        JPanel buttons = new JPanel();
        buttons.add(signUp);
        buttons.add(login);


        signUp.addActionListener(this);
        login.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(username);
        this.add(password);

        this.add(title);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String source = e.getActionCommand();
        String username = this.username.getText();
        String password = new String(this.password.getPassword());

        if (source.equals("Sign up")) {
            nav.showScreen("register");
            return;
        }
        // Make sure text fields aren't empty
        if (username.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(this, "Must fill in all required fields.");
            return;
        }

        if (source.equals("Login")) {
            try {
                LoginResponse resp = controller.login(username, password);
                LoginData data = resp.getData();
                if (resp.isSuccess()) {
                    nav.showScreen("home");
                    // JOptionPane.showMessageDialog(this, "Log into account with paramters: \n" +
                     //       data.getUsername() + "\n" + data.getPassword() + "\n" + resp.getTime());
                } else {
                    JOptionPane.showMessageDialog(this, resp.getException().getMessage());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }
}

