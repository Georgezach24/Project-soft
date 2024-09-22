package gr.conference.menus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import gr.conference.usersys.RestClient;

public class RegisterPage {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JTextField phoneField;

    public RegisterPage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(6, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();

        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(confirmPasswordLabel);
        registerPanel.add(confirmPasswordField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(phoneLabel);
        registerPanel.add(phoneField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        frame.add(registerPanel, BorderLayout.CENTER);
        frame.add(registerButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RestClient.registerRequest();
        RestClient.registerPost(username, password, confirmPassword, email, phone);

        JOptionPane.showMessageDialog(frame, "Registration successful!");
        
        frame.dispose();
        new StartingScreen();
    }
}
