package views;

import controllers.*;
import entities.Message;



import gateways.MessageSearchFirebaseSystem;
import message_search_use_case.MessageSearchGateway;
import message_search_use_case.MessageSearchInputBoundary;
import message_search_use_case.MessageSearchInteractor;
import message_search_use_case.MessageSearchOutputBoundary;

import services.DBInitializer;
import services.DBService;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    Map<String, Object> controllers;

    int chatID;

    int senderID;

    int receiverID;

    String senderName;


    String lang;

    public ChatScreen(int senderID, int receiverID, int chatID, String senderName,
                      Map<String, Object> controllers, String lang) {
        this.controllers = controllers;
        this.chatID = chatID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.senderName = senderName;
        this.lang = lang;
        this.mainGUI = this;
        this.runChatScreen();
    }

    public void runChatScreen() {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mainGUI.display();
            });
        }

    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel searchBarPanel = new SearchBarPanel((MessageSearchController) controllers.get("search"), chatID);

        mainPanel.add(searchBarPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        BoxLayout boxLayout = new BoxLayout(southPanel, BoxLayout.X_AXIS);
        southPanel.setLayout(boxLayout);

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener(this));

        chatBox = new JTextPane();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        //chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);
        JPanel recordButton = new RecordButton((AudioRecorderController) controllers.get("audio_record"),
                (AudioConvertController) controllers.get("audio_convert"),
                (MessageTranslateController) controllers.get("message_translate"), this.lang, messageBox);


        southPanel.add(messageBox);
        southPanel.add(sendMessage);
        southPanel.add(recordButton);

        mainPanel.add(BorderLayout.SOUTH, southPanel);
        ArrayList<Message> messages = ((SendMessageController) controllers.get("send")).getAllMessages(chatID);
        // Initialize chatbox with all saved messages from the chat
        for (Message currMessage : messages) {
            JTextArea messageArea = new JTextArea();
            messageArea.setLineWrap(true);
            messageArea.setEditable(false);
            messageArea.setFont(new Font("Serif", Font.PLAIN, 15));
            messageArea.setText("<" + currMessage.getReceiver().getName() + ">:  " + currMessage.getMessage());
            List<Integer> ids = new ArrayList<>();
            ids.add(currMessage.getId());
            ids.add(chatID);
            List<Object> conts = new ArrayList<>();
            conts.add(controllers.get("edit"));
            conts.add(controllers.get("delete"));
            if (currMessage.getReceiver().getUser_id() == senderID) {
                messageArea.addMouseListener(new EditDeletePopupListener(ids, conts, messageArea,
                        (JPanel) messageBox.getParent(), chatBox, currMessage.getReceiver().getName()));
            }
            messageArea.addMouseListener(new TranslateListener(lang));

            //chatBox.setCaretPosition(chatBox.getDocument().getLength());
            chatBox.insertComponent(messageArea);

            try {
                chatBox.getDocument().insertString(chatBox.getDocument().getLength(),"\n", null);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }

        newFrame.add(mainPanel);
        newFrame.setSize(470, 300);
        newFrame.setVisible(true);
    }

    public JTextArea displayMessage() {
        JTextArea messageArea = new JTextArea();
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Serif", Font.PLAIN, 15));
        messageArea.setText("<" + senderName + ">:  " + messageBox.getText());

        //chatBox.setCaretPosition(chatBox.getDocument().getLength());
        chatBox.setSelectionStart(chatBox.getDocument().getLength());
        chatBox.setSelectionEnd(chatBox.getDocument().getLength());
        chatBox.insertComponent(messageArea);
        try {
            chatBox.getDocument().insertString(chatBox.getDocument().getLength(),"\n", null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }


        return messageArea;
    }

    public int sendMessage() {
        Date curr_date = new Date();

        try {
            return ((SendMessageController) controllers.get("send")).sendMessage(chatID, messageBox.getText(), senderID, receiverID, curr_date).getMessage().getId();
        } catch (ExecutionException | InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    class sendMessageButtonListener implements ActionListener {
        JPanel parent;
        public sendMessageButtonListener(JPanel parent) {
            this.parent = parent;
        }

        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                JOptionPane.showMessageDialog(parent, "Type something in!");
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                JTextArea messageArea = displayMessage();
                // Persists message to database
                int msgID = sendMessage();
                // Clear the message box for new input
                messageBox.setText("");

                List<Integer> ids = new ArrayList<>();
                ids.add(msgID);
                ids.add(chatID); //Change to chatID later

                List<Object> conts = new ArrayList<>();
                conts.add(controllers.get("edit"));
                conts.add(controllers.get("delete"));

                messageArea.addMouseListener(new EditDeletePopupListener(ids, conts, messageArea,
                        (JPanel) messageBox.getParent(), chatBox, senderName));
                messageArea.addMouseListener(new TranslateListener(lang));

            }

            messageBox.requestFocusInWindow();
        }
    }
    static class EditDeletePopupListener extends MouseAdapter {
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
            if (SwingUtilities.isRightMouseButton(e)){
                doPop(e);
            }
        }


        public void doPop(MouseEvent e){
            EditDeletePopupMenu editDeletePopupMenu = new EditDeletePopupMenu(this.ids, this.controllers, message, parentPanel, chatBox, userName);
            editDeletePopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
        public void mouseReleased(MouseEvent e){
            if(e.isPopupTrigger()){
                doPop(e);
            }
        }


    }
    static class TranslateListener extends MouseAdapter {
        public TranslateListener(String lang) {
            this.lang = lang;
        }

        String lang;
        public void mousePressed(MouseEvent e){
            if (SwingUtilities.isLeftMouseButton(e)){
                LabelAdapter labelAdapter = new LabelAdapter(lang);
                labelAdapter.mouseClicked(e);
            }
        }
    }
}