package views.screens;

import entities.Chat;
import entities.Message;
import entities.User;

import gateways.MessageDeleteFirebaseSystem;
import gateways.MessageEditFirebaseSystem;
import message_edit_delete_use_case.*;

import gateways.MessageSearchFirebaseSystem;
import message_search_use_case.MessageSearchGateway;
import message_search_use_case.MessageSearchInputBoundary;
import message_search_use_case.MessageSearchInteractor;
import message_search_use_case.MessageSearchOutputBoundary;

import services.DBInitializer;
import services.DBService;
import views.presenters.MessageDeletePresenter;
import views.presenters.MessageEditPresenter;
import views.presenters.MessageSearchPresenter;
import views.controllers.MessageDeleteController;
import views.controllers.MessageEditController;
import views.controllers.MessageSearchController;
import views.styles.EditDeletePopupMenu;
import views.styles.SearchBarPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
import javax.swing.text.BadLocationException;

public class ChatScreen extends JPanel {

    String      appName     = "Translation Chat App";
    ChatScreen     mainGUI;
    JFrame      newFrame    = new JFrame(appName);
    JButton     sendMessage;
    JTextField  messageBox;
    JTextPane chatBox;
    JTextField  usernameChooser;
    JFrame      preFrame;

    DBInitializer dbInitializer;

    DBService dbService;

    Chat currChat;

    User sender;

    User receiver;

    MessageEditController editController;
    MessageDeleteController deleteController;
    MessageSearchController searchController;

    public static void main(String[] args) {

        MessageEditGateway eGateway = new MessageEditFirebaseSystem();
        MessageEditOutputBoundary ePresenter = new MessageEditPresenter();
        MessageEditInputBoundary eInteractor  = new MessageEditInteractor(eGateway, ePresenter);
        MessageEditController eController = new MessageEditController(eInteractor);

        MessageDeleteGateway dGateway = new MessageDeleteFirebaseSystem();
        MessageDeleteOutputBoundary dPresenter = new MessageDeletePresenter();
        MessageDeleteInputBoundary dInteractor  = new MessageDeleteInteractor(dGateway, dPresenter);
        MessageDeleteController dController = new MessageDeleteController(dInteractor);

        MessageSearchGateway sGateway = new MessageSearchFirebaseSystem();
        MessageSearchOutputBoundary sPresenter = new MessageSearchPresenter();
        MessageSearchInputBoundary sInteractor  = new MessageSearchInteractor(sGateway, sPresenter);
        MessageSearchController sController = new MessageSearchController(sInteractor);

        new ChatScreen(eController, dController, sController);
    }

    public ChatScreen(MessageEditController editController, MessageDeleteController deleteController,
                      MessageSearchController searchController) {
        this.deleteController = deleteController;
        this.editController = editController;
        this.searchController = searchController;

        this.mainGUI = this;
        this.runChatScreen();
    }

    public void runChatScreen() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager
                                .getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        mainGUI.preDisplay();
                    } catch (FileNotFoundException | InterruptedException | ExecutionException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }



    public void preDisplay() throws FileNotFoundException, ExecutionException, InterruptedException, ParseException {
        this.dbInitializer = new DBInitializer();
        this.dbInitializer.init();
        this.dbService = new DBService();

        this.currChat = dbService.getChatDetails(3);



        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        usernameChooser = new JTextField(15);
        JLabel chooseUsernameLabel = new JLabel("Pick a username:");
        JButton enterServer = new JButton("Enter Chat Server");
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        // preRight.weightx = 2.0;
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(usernameChooser, preRight);
        preFrame.add(prePanel, BorderLayout.CENTER);
        preFrame.add(enterServer, BorderLayout.SOUTH);
        preFrame.setSize(300, 300);
        preFrame.setVisible(true);

    }

    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        MessageSearchGateway searchGateway = new MessageSearchFirebaseSystem();
        MessageSearchOutputBoundary searchPresenter = new MessageSearchPresenter();
        MessageSearchInputBoundary searchInteractor = new MessageSearchInteractor(searchGateway, searchPresenter);
        MessageSearchController searchController = new MessageSearchController(searchInteractor);
        JPanel searchBarPanel = new SearchBarPanel(searchController, currChat.getId());

        mainPanel.add(searchBarPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatBox = new JTextPane();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        //chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(470, 300);
        newFrame.setVisible(true);
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                JTextArea messageArea = new JTextArea();
                messageArea.setLineWrap(true);
                messageArea.setEditable(false);
                messageArea.setFont(new Font("Serif", Font.PLAIN, 15));
                messageArea.setText("<" + username + ">:  " + messageBox.getText());
                //chatBox.setCaretPosition(chatBox.getDocument().getLength());
                chatBox.setSelectionStart(chatBox.getDocument().getLength());
                chatBox.setSelectionEnd(chatBox.getDocument().getLength());
                chatBox.insertComponent(messageArea);
                try {
                    chatBox.getDocument().insertString(chatBox.getDocument().getLength(),"\n", null);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }
                //chatBox.append("<" + username + ">:  " + messageBox.getText()
                //        + "\n");


                List<Integer> messageIDs = null;

                try {
                    messageIDs = dbService.getAllMessageIDs();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int nextMessageID = Collections.max(messageIDs) + 1;
                User sender;
                User receiver;
                try {
                    sender = dbService.getUserDetails(2);
                    receiver = dbService.getUserDetails(3);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Message sentMessage = new Message(nextMessageID, messageBox.getText(), sender, receiver, new Date(122, Calendar.DECEMBER, 15));
                try {
                    dbService.addMessage(sentMessage, currChat);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Clear the message box for new input
                messageBox.setText("");


                List<Integer> ids = new ArrayList<>();
                ids.add(nextMessageID);
                ids.add(3); //Change to chatID later

                List<Object> controllers = new ArrayList<>();
                controllers.add(editController);
                controllers.add(deleteController);

                messageArea.addMouseListener(new EditDeletePopupListener(ids, controllers, messageArea,
                        (JPanel) messageBox.getParent(), chatBox, username));


            }
            messageBox.requestFocusInWindow();
        }
    }
    static class EditDeletePopupListener extends MouseAdapter{
        List<Integer> ids;
        List<Object> controllers;
        JTextArea message;
        JPanel parentPanel;
        JTextPane chatBox;
        String userName;


        public EditDeletePopupListener(List<Integer> ids, List<Object> controllers, JTextArea message, JPanel parentPanel, JTextPane
            chatBox, String userName){
            this.ids = ids;
            this.controllers = controllers;
            this.message = message;
            this.parentPanel = parentPanel;
            this.chatBox = chatBox;
            this.userName = userName;

        }
        public void mousePressed(MouseEvent e){
            if(e.isPopupTrigger()){
                doPop(e);
            }}


        public void doPop(MouseEvent e){
            EditDeletePopupMenu editDeletePopupMenu = new EditDeletePopupMenu(this.ids, this.controllers, message, parentPanel, chatBox, userName);
            editDeletePopupMenu.show(e.getComponent(), e.getXOnScreen(), e.getYOnScreen());


        }
        public void mouseReleased(MouseEvent e){
            if(e.isPopupTrigger()){
                doPop(e);
            }}


    }


    String  username;

    class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            username = usernameChooser.getText();
            if (username.length() < 1) {
                System.out.println("No!");
            } else {
                preFrame.setVisible(false);
                display();
            }
        }

    }
}