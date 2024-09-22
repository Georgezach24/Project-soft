package gr.conference.menus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import gr.conference.usersys.RestClient;

public class UserSettingsPage {

    private JFrame frame;
    private JTextField inputField;
    private JLabel outputLabel;

    public UserSettingsPage(String usernameString) {
        loadPage(usernameString);
    }

    private void loadPage(String username) {
        frame = new JFrame("User Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Μέγεθος παραθύρου
        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1));

        JLabel label1 = new JLabel("1. Information update", JLabel.CENTER);
        JLabel label2 = new JLabel("2. Password update", JLabel.CENTER);
        JLabel label3 = new JLabel("3. Delete Account", JLabel.CENTER);
        JLabel label4 = new JLabel("4. Back", JLabel.CENTER);
        outputLabel = new JLabel("", JLabel.CENTER); // Για την έξοδο μηνυμάτων

        menuPanel.add(new JLabel("User Settings", JLabel.CENTER));
        menuPanel.add(label1);
        menuPanel.add(label2);
        menuPanel.add(label3);
        menuPanel.add(label4);
        menuPanel.add(outputLabel);

        frame.add(menuPanel, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.CENTER);

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput(inputField.getText(), username);
                inputField.setText(""); // Καθαρισμός του πεδίου μετά την είσοδο
            }
        });

        frame.add(inputField, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void handleUserInput(String input, String username) {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            outputLabel.setText("Invalid input! Please enter a valid number.");
            return;
        }

        switch (choice) {
            case 1:
                username = infoUpdate(username);
                break;
            case 2:
                passwordUpdate(username);
                break;
            case 3:
                deleteAccount(username);
                new StartingScreen(); // Επιστροφή στην αρχική σελίδα μετά την διαγραφή λογαριασμού
                frame.dispose();
                return;
            case 4:
                new UserPage(username); // Επιστροφή στη σελίδα του χρήστη
                frame.dispose();
                return;
            default:
                outputLabel.setText("Invalid option. Please try again.");
                break;
        }
    }

    private String infoUpdate(String username) {
        outputLabel.setText("Updating Information:");

        String newUsername = JOptionPane.showInputDialog("Write your new username: ");
        String newName = JOptionPane.showInputDialog("Write your new Name: ");
        String newSurname = JOptionPane.showInputDialog("Write your new Surname: ");
        String newEmail = JOptionPane.showInputDialog("Write your new Email: ");
        String newPhone = JOptionPane.showInputDialog("Write your new Phone: ");

        RestClient.updatePost(username, newUsername, newName, newSurname, newEmail, newPhone);

        return newUsername.isBlank() ? username : newUsername;
    }

    private void passwordUpdate(String username) {
        outputLabel.setText("Updating Password:");

        String oldPassword = JOptionPane.showInputDialog("Write your old password: ");
        String newPassword = JOptionPane.showInputDialog("Write your new password: ");

        RestClient.passwordUpdatePost(username, oldPassword, newPassword);
    }

    private void deleteAccount(String username) {
        outputLabel.setText("Deleting Account:");

        String input = JOptionPane.showInputDialog("Are you sure you want to delete your account? (y/n)");
        if (input.equalsIgnoreCase("y")) {
            RestClient.deletePost(username);
            outputLabel.setText("Your account has been deleted.");
        } else {
            outputLabel.setText("Account deletion canceled.");
        }
    }
}
