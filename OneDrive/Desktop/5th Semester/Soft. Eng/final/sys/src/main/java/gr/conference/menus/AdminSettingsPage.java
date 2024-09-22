package gr.conference.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import gr.conference.usersys.RestClient;

public class AdminSettingsPage {

    private JFrame frame;
    private JTextField inputField;
    private JLabel outputLabel;

    public AdminSettingsPage() {
        loadPage();
    }

    private void loadPage() {
        frame = new JFrame("Admin Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Ορισμός μεγέθους παραθύρου
        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1));

        JLabel label1 = new JLabel("1. Information update", JLabel.CENTER);
        JLabel label2 = new JLabel("2. Password update", JLabel.CENTER);
        JLabel label3 = new JLabel("3. Delete Account", JLabel.CENTER);
        JLabel label4 = new JLabel("4. Account status update", JLabel.CENTER);
        JLabel label5 = new JLabel("5. Back", JLabel.CENTER);
        outputLabel = new JLabel("", JLabel.CENTER); // Για την εμφάνιση μηνυμάτων

        menuPanel.add(label1);
        menuPanel.add(label2);
        menuPanel.add(label3);
        menuPanel.add(label4);
        menuPanel.add(label5);
        menuPanel.add(outputLabel); // Αυτό θα εμφανίζει τα αποτελέσματα

        frame.add(menuPanel, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.CENTER);

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput(inputField.getText());
                inputField.setText(""); // Καθαρισμός πεδίου μετά την είσοδο
            }
        });

        frame.add(inputField, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void handleUserInput(String input) {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            outputLabel.setText("Invalid input! Please enter a number.");
            return;
        }

        switch (choice) {
            case 1:
                infoUpdate();
                break;
            case 2:
                passwordUpdate();
                break;
            case 3:
                deleteAccount();
                break;
            case 4:
                statusUpdate();
                break;
            case 5:
                frame.dispose();
                new UserPage("admin"); // Επιστροφή στο AdminPage
                break;
            default:
                outputLabel.setText("Invalid choice! Please select a valid option.");
                break;
        }
    }

    private String infoUpdate() {
        RestClient.updateRequest();
        String username = JOptionPane.showInputDialog("Write the current username: ");
        String usernameString = JOptionPane.showInputDialog("Write the new username: ");
        String nameString = JOptionPane.showInputDialog("Write the new Name: ");
        String surnameString = JOptionPane.showInputDialog("Write the new Surname: ");
        String emailString = JOptionPane.showInputDialog("Write the new Email: ");
        String phoneString = JOptionPane.showInputDialog("Write the new Phone: ");
        
        RestClient.updatePost(username, usernameString, nameString, surnameString, emailString, phoneString);
        
        outputLabel.setText("Information updated for user: " + usernameString);
        return usernameString;
    }

    private void passwordUpdate() {
        RestClient.passwordUpdateRequest();
        String username = JOptionPane.showInputDialog("Write the username: ");
        String oldPassword = JOptionPane.showInputDialog("Write the old password: ");
        String newPassword = JOptionPane.showInputDialog("Write the new password: ");
        
        RestClient.passwordUpdatePost(username, oldPassword, newPassword);
        
        outputLabel.setText("Password updated for user: " + username);
    }

    private void deleteAccount() {
        RestClient.deleteRequest();
        String username = JOptionPane.showInputDialog("Write the username: ");
        String input = JOptionPane.showInputDialog("Are you sure you want to delete this account? (y/n)");
        
        if (input.equalsIgnoreCase("y")) {
            RestClient.deletePost(username);
            outputLabel.setText("Account deleted for user: " + username);
        } else {
            outputLabel.setText("Account deletion cancelled.");
        }
    }

    private void statusUpdate() {
        RestClient.updateStatusRequest();
        String username = JOptionPane.showInputDialog("Write the username: ");
        String status = JOptionPane.showInputDialog("Write the status (ACTIVE/INACTIVE): ");
        RestClient.updateStatusPost(username, status);
        
        outputLabel.setText("Status updated for user: " + username);
    }
}
