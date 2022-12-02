package views;

import controllers.AddContactController;
import controllers.CustomizationController;
import controllers.DeleteContactController;
import controllers.SendMessageController;
import entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HomeScreen extends JPanel implements ActionListener{

    Map<String, Object> controllers;
    Navigator nav;
    User currUser;

    HashMap<String, String> langs;

    public HomeScreen(HashMap<String, String> langs, Map<String, Object> controllers, Navigator nav) {
        this.controllers = controllers;
        this.nav = nav;
        this.langs = langs;
    }

    public void finalizeScreen(User user) {
        this.currUser = user;
//        JLabel label = new JLabel(String.valueOf(currUser.getUser_id()));
//        this.add(label);
        //TODO: Add all the screens (ChatScreen, ProfileScreen, ContactScreen)
        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        addLeftPanel();
        addRightPanel();
    }

    private void addRightPanel() {

        JPanel rightPanel = new JPanel();
        BorderLayout borderLayout =  new BorderLayout();
        rightPanel.setLayout(borderLayout);
        this.add(rightPanel, BorderLayout.EAST);
        this.putClientProperty("right", rightPanel);
        addOpenChatBtn(rightPanel);
        addChatPanel(rightPanel);
    }

    private void addChatPanel(JPanel panel) {
        JPanel chatPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        chatPanel.setLayout(borderLayout);
        panel.add(chatPanel, BorderLayout.SOUTH);
        panel.putClientProperty("chat", chatPanel);
    }

    private void addOpenChatBtn(JPanel panel) {
        JButton openChatBtn = new JButton("Open Chat");
        openChatBtn.addActionListener(this);
        panel.add(openChatBtn, BorderLayout.NORTH);
    }

    private void addLeftPanel() {
        JPanel leftPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        leftPanel.setLayout(borderLayout);
        this.add(leftPanel, BorderLayout.WEST);
        this.putClientProperty("left", leftPanel);
        addButtons(leftPanel);
        addContactScreen(leftPanel);
    }

    private void addContactScreen(JPanel panel) {
        try {
            ContactScreen contactScreen =  new ContactScreen(currUser.getUser_id(),
                    (DeleteContactController) controllers.get("delete_contact"),
                    (AddContactController) controllers.get("add_contact"));
            panel.add(contactScreen, BorderLayout.SOUTH);
            panel.putClientProperty("contact", contactScreen);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void addButtons(JPanel panel) {
        JPanel topLeftPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        topLeftPanel.setLayout(borderLayout);
        panel.add(topLeftPanel, BorderLayout.NORTH);
        topLeftPanel.add(new JLabel("Your User ID: " + currUser.getUser_id()), BorderLayout.CENTER);
        addLogout(topLeftPanel);
        addCustomize(topLeftPanel);
    }

    private void addCustomize(JPanel panel) {
        JButton customizeBtn = new JButton("Customize");
        customizeBtn.addActionListener(this);
        panel.add(customizeBtn, BorderLayout.EAST);
    }

    private void addLogout(JPanel panel) {

        JButton logoutBtn = new JButton("Log out");
        logoutBtn.addActionListener(this);
        panel.add(logoutBtn, BorderLayout.WEST);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String source = e.getActionCommand();
        switch (source) {
            case "Log out" -> {
                nav.showScreen("login");
                this.removeAll();
                this.revalidate();
            }
            case "Customize" -> {
                JFrame customizeWindow = new JFrame("Customize");
                CustomizationController cController = (CustomizationController) this.controllers.get("customization");
                ProfileScreen profileScreen = new ProfileScreen(this.langs, cController, currUser);
                customizeWindow.add(profileScreen);
                customizeWindow.pack();
                customizeWindow.setVisible(true);
            }
            case "Open Chat" -> {
                ContactScreen contactScreen = (ContactScreen) ((JPanel) this.getClientProperty("left"))
                        .getClientProperty("contact");
                JPanel rightPanel = (JPanel) this.getClientProperty("right");
                int contactID = contactScreen.getSelectedRowUserID();
                if (contactID == -1) {
                    JOptionPane.showMessageDialog(this, "Select a row.");
                } else {
                    //JLabel contactIDLabel = new JLabel("Contact ID:" + contactID);
                    JPanel chatPanel = (JPanel) rightPanel.getClientProperty("chat");
                    //chatPanel.add(contactIDLabel, BorderLayout.CENTER);
                    addChatScreen(chatPanel, contactID);
                }
            }
        }
    }

    private void addChatScreen(JPanel panel, int contactID) {
        int currUserID = this.currUser.getUser_id();
        int chatID = ((SendMessageController) this.controllers.get("send")).getChatIDByUsers(currUserID, contactID);
        if (chatID == -1) {
            JOptionPane.showMessageDialog(panel, "Something went wrong!");
        }
        Map<String, Object> controllersForChat =  new HashMap<>();
        controllersForChat.put("send", controllers.get("send"));
        controllersForChat.put("edit", controllers.get("message_edit"));
        controllersForChat.put("search", controllers.get("message_search"));
        controllersForChat.put("delete", controllers.get("message_delete"));
        controllersForChat.put("audio_record", controllers.get("audio_record"));
        controllersForChat.put("audio_convert", controllers.get("audio_convert"));
        controllersForChat.put("message_translate", controllers.get("message_translate"));
        new ChatScreen(currUserID, contactID, chatID, this.currUser.getName(),
                controllersForChat, this.currUser.getDefault_lang());
    }
}
