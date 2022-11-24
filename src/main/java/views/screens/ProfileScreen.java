package views.screens;

import entities.User;
import views.controllers.CustomizationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileScreen extends JPanel implements ActionListener{
    CustomizationController controller;

    JLabel nameLabel = new JLabel("Name");
    JLabel passLabel = new JLabel("Password");
    JLabel langLabel = new JLabel("Default Language");

    JTextField nameField = new JTextField(50);
    JPasswordField passField = new JPasswordField(50);
    JTextField langField = new JTextField(50);
    User user;


    public ProfileScreen(CustomizationController controller, User user) {
        this.controller = controller;
        this.user = user;

        JLabel title = new JLabel("Profile");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton nameButton = new JButton("set name");
        JButton passButton = new JButton("set password");
        JButton langButton = new JButton("set default language");

        nameButton.addActionListener(this);
        passButton.addActionListener(this);
        langButton.addActionListener(this);

        JPanel nameSection = new JPanel();
        nameSection.add(nameLabel);
        nameSection.add(nameField);
        nameSection.add(nameButton);

        JPanel passSection = new JPanel();
        passSection.add(passLabel);
        passSection.add(passField);
        passSection.add(passButton);

        JPanel langSection = new JPanel();
        langSection.add(langLabel);
        langSection.add(langField);
        langSection.add(langButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(nameSection);
        this.add(passSection);
        this.add(langSection);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String source = e.getActionCommand();
        String name = this.nameField.getText();
        String password = new String(this.passField.getPassword());
        String default_lang = this.langField.getText();

        if (source.equals("set name")) {
            controller.changeName(name,default_lang, password, user);
            System.out.println(user.getName());
        } else if (source.equals("set password")) {
            System.out.println(password.length());
            controller.changePassword(name, default_lang, password, user);
            System.out.println(user.getPassword());
        } else if (source.equals("set default language")) {
            controller.changeLanguage(name, default_lang, password, user);
        }
    }
}
