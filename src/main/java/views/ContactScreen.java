package views;

import contact_usecases.add_contact_use_case.AddContactData;
import contact_usecases.delete_contact_use_case.DeleteContactData;
import controllers.AddContactController;
import controllers.DeleteContactController;
import entities.User;
import services.DBService;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class ContactScreen extends JPanel implements ActionListener {
    ArrayList<MemberVO>members = new ArrayList<>();
    DBService dbService;
    int userID;
    DeleteContactController dcController;
    AddContactController acController;

    JTable table;
    DefaultTableModel model;
    JTextField tfUserid;
    public ContactScreen(int userID, DeleteContactController dcController, AddContactController acController) throws ExecutionException, InterruptedException {
        this.userID = userID;
        this.dbService = new DBService();
        this.dcController = dcController;
        this.acController = acController;
        //Columns
        String[] colNames = new String[]{"User ID"};
        this.model = new DefaultTableModel(colNames, 0);

        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        this.table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);


        //Input Panel at the bottom of the screen


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2,1));

        JPanel panel = new JPanel();
        this.tfUserid = new JTextField(6);

        panel.add(new JLabel("User ID"));
        panel.add(tfUserid);
        bottomPanel.add(panel);

        JPanel panel2 = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnDel = new JButton("Delete");
        panel2.add(btnAdd);
        panel2.add(btnDel);
        bottomPanel.add(panel2);

        String[] rows = new String[2];

        // Fetch list of all users from database
        User targetUser = dbService.getUserDetails(userID);
        ArrayList<Long> contacts = targetUser.getContacts();
        System.out.println(contacts);

        for (Long contact : contacts) {
            rows[0] = String.valueOf(contact);
            model.addRow(rows);
            members.add(new MemberVO(contact));
        }

        tfUserid.setText("");
        btnAdd.addActionListener(this);
        btnDel.addActionListener(this);

        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    public int getSelectedRowUserID() {
        int rowIndex = this.table.getSelectedRow();
        if (rowIndex == -1) {
            return rowIndex;
        } else {
            return members.get(rowIndex).userid.intValue();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String source = e.getActionCommand();

        if (source.equals("Delete")) {
            int rowIndex = this.table.getSelectedRow();

            if (rowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Select a contact.");
            }
            this.model.removeRow(rowIndex);

            Long contactID = members.get(rowIndex).userid;
            DeleteContactData dcData = new DeleteContactData(userID, contactID);
            dcController.deleteContact(dcData);

            members.remove(rowIndex);

        } else if (source.equals("Add")) {
            String[] rows = new String[2];

            if (this.tfUserid.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Type in a user ID.");
            } else if (!tfUserid.getText().matches("^\\d*$")) {
                JOptionPane.showMessageDialog(this, "Type in an integer.");
            }

            rows[0] = this.tfUserid.getText();
            this.tfUserid.setText("");
            long contactID = Long.parseLong(rows[0]);

            try {
                this.acController.addContact(new AddContactData(userID, (int) contactID));
                model.addRow(rows);
                members.add(new MemberVO(contactID));
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error.getMessage());
            }
        }
    }

    static class MemberVO{
        private final Long userid;

        public MemberVO(Long userid){
            this.userid = userid;
        }


    }
}
