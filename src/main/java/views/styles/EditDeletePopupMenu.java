package views.styles;

import message_edit_delete_use_case.MessageDeleteData;
import message_edit_delete_use_case.MessageEditData;
import views.controllers.MessageDeleteController;
import views.controllers.MessageEditController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EditDeletePopupMenu extends JPopupMenu {
    JMenuItem edit;
    JMenuItem delete;
    List<Integer> ids;
    List<Object> controllers;
    JTextArea message;
    JPanel parentPanel;
    JTextPane chatBox;
    String username;


    public EditDeletePopupMenu(List<Integer> ids, List<Object> controllers, JTextArea message, JPanel parentPanel, JTextPane
                                chatBox, String username){
        this.ids = ids;
        this.edit = new JMenuItem("Edit");
        this.delete = new JMenuItem("Delete");
        this.edit.addActionListener(new EditActionListener());
        this.delete.addActionListener(new DeleteActionListener());
        this.controllers = controllers;
        this.add(edit);
        this.add(delete);
        this.message = message;
        this.parentPanel = parentPanel;
        this.chatBox = chatBox;
        this.username = username;

    }
    class EditActionListener implements ActionListener{
        HintTextField editTextField;
        JFrame editApplication;

        @Override
        public void actionPerformed(ActionEvent e) {
            editApplication = new JFrame("Edit");
            editTextField = new HintTextField("Edit Message...");
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
            JButton editButton = new JButton("Edit");
            mainPanel.add(editTextField);
            mainPanel.add(editButton);
            editButton.addActionListener(new EditButtonActionListener());
            editApplication.add(mainPanel);
            editApplication.pack();
            editApplication.setVisible(true);

        }
        class EditButtonActionListener implements  ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                ((MessageEditController) controllers.get(0)).editMessage(
                        new MessageEditData(editTextField.getText(), ids.get(0)));
                JOptionPane.showMessageDialog(editApplication, "Edited!");
                message.setText("<" + username + ">:  " + editTextField.getText());
            }
        }
    }
    class DeleteActionListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ((MessageDeleteController) controllers.get(1)).delete(new MessageDeleteData(ids.get(0), ids.get(1)));
                //chatBox.remove(message);
                //chatBox.revalidate();
                //chatBox.repaint();
            } catch (ExecutionException | InterruptedException ex){

                JOptionPane.showMessageDialog(parentPanel, "Something went wrong! Please try again!");
            }


        }
    }

}
