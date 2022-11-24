package views.screens;

import entities.User;
import services.DBInitializer;
import services.DBService;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class ContactScreen extends JPanel {
    ArrayList<MemberVO>members = new ArrayList<MemberVO>();
    DBService dbService;

    public ContactScreen(DBService db) throws ExecutionException, InterruptedException {
        this.dbService = db;

        this.setBounds(200, 100, 400, 200);

        //Columns
        String[] colNames = new String[]{"User ID"};
        DefaultTableModel model = new DefaultTableModel(colNames, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);


        //Input Panel at the bottom of the screen


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2,1));

        JPanel panel = new JPanel();
        JTextField tfUserid = new JTextField(6);

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
        User targetUser = dbService.getUserDetails(1);
        ArrayList<Long> contacts = targetUser.getContacts();
        System.out.println(contacts);

        for (int i = 0; i < contacts.size(); i++) {
            rows[0] = String.valueOf(contacts.get(i));
            model.addRow(rows);
            members.add(new MemberVO(contacts.get(i)));

        }
        model.addRow(rows);
        tfUserid.setText("");
        //members.add(new MemberVO(0L));

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] rows = new String[2];
                rows[0] = tfUserid.getText();
                model.addRow(rows);

                tfUserid.setText("");
                Long userid = Long.parseLong(rows[0]);
                User targetUser = null;
                try {
                    targetUser = dbService.getUserDetails(1);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    dbService.addContact(targetUser, userid);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                members.add(new MemberVO(userid));
            }
        });

        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Delete
                int rowIndex = table.getSelectedRow();
                // If rowIndex is not selected, then rowIndex is -1
            }
        });
        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                DBInitializer dbInitializer = new DBInitializer();
//                try {
//                    dbInitializer.init();
//                } catch (FileNotFoundException ex) {
//                    throw new RuntimeException(ex);
//                }
                int rowIndex = table.getSelectedRow();

                if (rowIndex == -1) return;
                model.removeRow(rowIndex);
                System.out.println(members.size());
                Long userid = members.get(rowIndex).userid;

                DBService dbService = new DBService();
                User targetUser = null;
                try {
                    targetUser = dbService.getUserDetails(1);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    dbService.deleteContact(targetUser, userid);
                } catch (ExecutionException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                members.remove(rowIndex);
            }
        });

        this.add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
        }

        class MemberVO{
        private final Long userid;

        public MemberVO(Long userid){
            this.userid = userid;
        }


    }

}
