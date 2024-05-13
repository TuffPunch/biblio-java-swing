package com.tekup.biblio.gui;

import com.tekup.biblio.dao.BookDao;
import com.tekup.biblio.models.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddBookGUI extends JFrame {
    private JTextField titleField, authorField, publisherField;
    private JButton addButton, cancelButton;

    public AddBookGUI(String currentUserRole, int userId) {
        setTitle("Add Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL; // Components fill horizontally
        c.insets = new Insets(10, 10, 10, 10); // Add spacing between elements


        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField();
        JLabel publisherLabel = new JLabel("Publisher:");
        publisherField = new JTextField();

        addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String title = titleField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();

                BookDao bdao = new BookDao();
                try {
                    bdao.addBook(new Book(0, title, author, publisher));
                    new ListBooksGUI(currentUserRole, userId);
                    dispose();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ListBooksGUI(currentUserRole, userId);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
        c.gridx = 0;
        c.gridy = 0;
        panel.add(titleLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(titleField, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(authorLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(authorField, c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(publisherLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(publisherField, c);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel(), c); // Empty label for layout purposes
        c.gridx = 1;
        c.gridy = 3;
        panel.add(addButton, c);

        c.gridx = 1;
        c.gridy = 4;
        panel.add(cancelButton, c);

        add(panel);
        setSize(400, 300);

        setVisible(true);
    }

}
