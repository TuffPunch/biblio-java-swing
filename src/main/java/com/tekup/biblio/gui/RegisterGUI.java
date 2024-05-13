package com.tekup.biblio.gui;

import com.tekup.biblio.dao.AuthDao;
import com.tekup.biblio.dao.StudentDao;
import com.tekup.biblio.models.Student;
import com.tekup.biblio.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;


public class RegisterGUI extends JFrame {
    private JTextField usernameField, nameField, contactField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton;

    public RegisterGUI() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nameField, gbc);

        JLabel contactLabel = new JLabel("Contact:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(contactLabel, gbc);
        contactField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(contactField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(confirmPasswordLabel, gbc);
        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(confirmPasswordField, gbc);

        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get user input
                String username = usernameField.getText();
                String name = nameField.getText();
                String contact = contactField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Validate password
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(RegisterGUI.this,
                            "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StudentDao studentDao = new StudentDao();
                try {
                    int lastStudentId = studentDao.getLastStudentId();
                    studentDao.addStudent(new Student(new User(lastStudentId + 1, username, password, "student"), name, contact));
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Student registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                new LoginGUI().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

}

