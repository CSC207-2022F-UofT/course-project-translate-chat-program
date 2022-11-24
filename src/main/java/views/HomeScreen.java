package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Frameworks/Drivers layer

/**
 * A basic registration screen with text fields and two buttons, the "Sign Up" button will
 * attempt to create an account while the "login" button will navigate to the login page.
 */
public class HomeScreen extends JPanel implements ActionListener {
    private int currUserId;
    // TODO: Change types to ChatScreen and ContactScreen respectively
    private JPanel chatScreen;
    private JPanel contacts;
    private Navigator nav;

    private JButton logout;
    private JButton customize;

    public void setCurrUserId(int currUserId) {
        this.currUserId = currUserId;
    }

    public int getCurrUserId() {
        return this.currUserId;
    }

    // TODO: Change types of chatScreen, contacts, to ChatScreen and ContactScreen
    public HomeScreen(int currUserId, JPanel chatScreen, JPanel contacts, Navigator nav) {
        this.currUserId = currUserId;
        this.chatScreen = chatScreen;
        this.contacts = contacts;
        this.nav = nav;

        chatScreen.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        contacts.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.logout = new JButton("logout");
        this.customize = new JButton("customize");
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 1));
        buttons.add(logout);
        buttons.add(customize);


        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipadx = 50;
        this.add(buttons, c);
        c.ipadx = 0;
        c.ipady = 100;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        this.add(contacts, c);
        c.ipady = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        this.add(chatScreen, c);

        logout.addActionListener(this);
        customize.addActionListener(this);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String source = e.getActionCommand();
        if (source.equals("logout")) {
            nav.showScreen("login");
        } else if (source.equals("customize")) {
            nav.showScreen("customize");
        }
    }
}
