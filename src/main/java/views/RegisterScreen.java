package views;

import user_register_use_case.CreationData;
import user_register_use_case.RegisterResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Frameworks/Drivers layer

/**
 * A basic registration screen with text fields and two buttons, the "Sign Up" button will
 * attempt to create an account while the "login" button will navigate to the login page.
 */
public class RegisterScreen extends JPanel implements ActionListener {
    /**
     * The username chosen by the user
     */
    HintTextField username = new HintTextField("Enter username...");
    /**
     * The email chosen by the user
     */
    HintTextField email = new HintTextField("Enter email...");
    /**
     * The password
     */
    HintPasswordField password = new HintPasswordField("Enter password...  ");
    /**
     * The second password to make sure the user understands
     */
    HintPasswordField repeatPassword = new HintPasswordField("Confirm password...");
    /**
     * A dropdown with any possible language as a choice
     */
    JComboBox<String> default_lang;
    /**
     * The controller
     */
    UserRegisterController controller;


    /**
     * A window with a title and a JButton
     */
    public RegisterScreen(String[] languages, UserRegisterController controller) {
        this.default_lang = new AutoFillDropdown(languages);
        this.controller = controller;

        JLabel title = new JLabel("Register");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        username.setBorder(BorderFactory.createTitledBorder("Username"));
        email.setBorder(BorderFactory.createTitledBorder("Email"));

        JButton signUp = new JButton("Sign up");
        JButton login = new JButton("Login");


        JPanel buttons = new JPanel();
        buttons.add(signUp);
        buttons.add(login);

        // Allows you to type into the dropdown menu
        default_lang.setEditable(true);

        signUp.addActionListener(this);
        login.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(username);
        this.add(email);
        this.add(password);
        this.add(repeatPassword);
        this.add(default_lang);

        this.add(title);
        this.add(buttons);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String source = e.getActionCommand();
        String username = this.username.getText();
        String password1 = new String(this.password.getPassword());
        String password2 = new String(this.repeatPassword.getPassword());
        String email = this.email.getText();
        String default_lang = (String) this.default_lang.getSelectedItem();

        if (!password1.equals(password2)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }
        if (username.isBlank() || password1.isBlank() || password2.isBlank() || email.isBlank()) {
            JOptionPane.showMessageDialog(this, "Must fill in all required fields.");
            return;
        }
        System.out.println(source);
        if (source.equals("Sign up")) {
            try {
                RegisterResponse resp = controller.register(username, password1, email, default_lang);
                // TODO: Navigate to next page
                CreationData data = resp.getData();
                JOptionPane.showMessageDialog(this, "Creating account with paramters: \n" +
                        data.getUsername() + "\n" + data.getPassword() + "\n" + data.getEmail() + "\n" + data.getDefault_lang()+ "\n"  + resp.getTime());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else if (source.equals("Login")) {
            // TODO: Navigate to Login Page
            JOptionPane.showMessageDialog(this, "Not yet implemented!");
        }
    }
}
