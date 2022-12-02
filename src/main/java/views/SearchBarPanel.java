package views;


import controllers.MessageSearchController;
import entities.Message;

import message_search_use_case.MessageSearchData;
import message_search_use_case.MessageSearchResponse;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchBarPanel extends JPanel implements ActionListener {

    HintTextField searchText = new HintTextField("Search messages...");
    MessageSearchController messageSearchController;
    int chatID;
    public SearchBarPanel(MessageSearchController messageSearchController, int chatID) {
        this.messageSearchController = messageSearchController;
        this.chatID = chatID;

        searchText.setColumns(30);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(searchText);
        this.add(searchButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        MessageSearchData data = new MessageSearchData(searchText.getText(), this.chatID);

        try {
            MessageSearchResponse response = this.messageSearchController.search(data);
            List<Message> messages = response.getMessages();
            JFrame application = new JFrame("Application");
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            JTextArea messagesBox = new JTextArea();
            messagesBox.setEditable(false);
            messagesBox.setFont(new Font("Serif", Font.PLAIN, 15));
            messagesBox.setLineWrap(true);

            for (Message message : messages) {
                messagesBox.append("<" + message.getReceiver().getName() + ">: " + message.getMessage() + "\n");
            }

            mainPanel.add(new JScrollPane(messagesBox), BorderLayout.CENTER);
            application.add(mainPanel);
//            application.setSize(30, 40);
            application.pack();

            application.setVisible(true);




        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, error.getMessage());
        }
    }
}