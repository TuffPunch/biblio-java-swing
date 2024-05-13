package com.tekup.biblio.gui;

import com.tekup.biblio.dao.AuthDao;
import com.tekup.biblio.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginGUI extends JFrame implements ActionListener {

    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginGUI() {
        super("Library Management System - Login"); // Set window title

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this); // Register this class as the listener for button clicks
        registerButton = new JButton("Register");
        registerButton.addActionListener(this); // Register this class as the listener for button clicks

        // Organize layout using a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL; // Components fill horizontally
        c.insets = new Insets(10, 10, 10, 10); // Add spacing between elements

        // Add labels and text fields to the panel
        c.gridx = 0;
        c.gridy = 0;
        panel.add(usernameLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        panel.add(usernameField, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        panel.add(passwordField, c);

        // Add login button to the panel
        c.gridy = 2;
        c.gridwidth = 2; // Button spans 2 columns
        panel.add(loginButton, c);

        // Add register button
        c.gridy = 3;
        c.gridwidth = 2; // Button spans 2 columns
        panel.add(registerButton, c);

        // Add panel to the frame
        getContentPane().add(panel);

        // Set window properties
        pack(); // Adjust frame size to fit components
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on window close
        setVisible(true); // Make the window visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Handle login button click here (e.g., validate username/password with your logic)
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword()); // Get password as a String

            AuthDao adao = new AuthDao();
            try {
                User u = adao.login(username, password);
                if (u != null) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    new ListBooksGUI(u.getRole(), u.getUserId());
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }


            // Clear password field after login attempt
            passwordField.setText("");
        }
         else if (e.getSource() == registerButton) {
             new RegisterGUI();
             dispose();
        }
    }

}

