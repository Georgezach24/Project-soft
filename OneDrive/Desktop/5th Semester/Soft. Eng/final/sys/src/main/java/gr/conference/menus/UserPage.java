package gr.conference.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserPage {

    private JFrame frame;
    private JTextField inputField;
    private JLabel outputLabel;

    public UserPage(String usernameString) {
        loadPage(usernameString);
    }

    private void loadPage(String uString) {
        frame = new JFrame("User Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Ορισμός μεγέθους παραθύρου
        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1));

        JLabel welcomeLabel = new JLabel("WELCOME " + uString.toUpperCase(), JLabel.CENTER);
        JLabel label1 = new JLabel("1. User settings", JLabel.CENTER);
        JLabel label2 = new JLabel("2. Create new conference", JLabel.CENTER);
        JLabel label3 = new JLabel("3. Conference search", JLabel.CENTER);
        JLabel label4 = new JLabel("4. Exit", JLabel.CENTER);  // Πρόσθεσα επιλογή εξόδου
        outputLabel = new JLabel("", JLabel.CENTER); // Για την εμφάνιση των μηνυμάτων αποτελεσμάτων

        menuPanel.add(welcomeLabel);
        menuPanel.add(label1);
        menuPanel.add(label2);
        menuPanel.add(label3);
        menuPanel.add(label4);
        menuPanel.add(outputLabel); // Προσθήκη του label για τα αποτελέσματα

        frame.add(menuPanel, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.CENTER);

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput(inputField.getText(), uString);
                inputField.setText(""); // Καθαρισμός πεδίου μετά την είσοδο
            }
        });

        frame.add(inputField, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void handleUserInput(String input, String uString) {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            outputLabel.setText("Invalid input! Please enter a valid number.");
            return;
        }

        switch (choice) {
            case 1:
                outputLabel.setText("Opening User Settings...");
                new UserSettingsPage(uString); // Αν υπάρχει αυτή η κλάση
                frame.dispose();
                break;

            case 2:
                outputLabel.setText("Opening Create New Conference...");
                new ConferenceCreatePage(uString); // Αν υπάρχει αυτή η κλάση
                frame.dispose();
                break;

            case 3:
                outputLabel.setText("Opening Conference Search...");
                new ConferenceSearchPage(uString); // Αν υπάρχει αυτή η κλάση
                frame.dispose();
                break;

            case 4:
                outputLabel.setText("Exiting...");
                frame.dispose();
                break;

            default:
                outputLabel.setText("Invalid choice! Please select a valid option.");
                break;
        }
    }
}
