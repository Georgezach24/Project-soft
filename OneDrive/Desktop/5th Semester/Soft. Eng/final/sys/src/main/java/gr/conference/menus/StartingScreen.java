package gr.conference.menus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartingScreen {

    private JFrame frame;
    private JTextField inputField;
    private JLabel outputLabel;
    public String user;

    public StartingScreen() {
        loadMenu();
    }

    private void loadMenu() {
        frame = new JFrame("Conference System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1));

        menuPanel.add(new JLabel("WELCOME TO THE CONFERENCE SYSTEM USER PAGE", JLabel.CENTER));
        menuPanel.add(new JLabel("1. LOGIN", JLabel.CENTER));
        menuPanel.add(new JLabel("2. REGISTER", JLabel.CENTER));
        menuPanel.add(new JLabel("3. CONTINUE AS GUEST", JLabel.CENTER));
        menuPanel.add(new JLabel("4. EXIT", JLabel.CENTER));
        outputLabel = new JLabel("", JLabel.CENTER); // Για την έξοδο μηνυμάτων
        menuPanel.add(outputLabel);

        frame.add(menuPanel, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.CENTER);

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput(inputField.getText());
                inputField.setText(""); // Καθαρισμός του πεδίου μετά την είσοδο
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
            outputLabel.setText("Invalid input! Please enter a valid number.");
            return;
        }

        LoginPage lp = new LoginPage();

        switch (choice) {
            case 1:
                lp.loadPageUser();
                frame.dispose(); // Κλείσιμο παραθύρου
                break;
            case 2:
                new RegisterPage();
                frame.dispose(); // Κλείσιμο παραθύρου
                break;
            case 3:
                outputLabel.setText("Continuing as guest...");
                frame.dispose();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                outputLabel.setText("Invalid option! Please select a valid option.");
                break;
        }
    }

    public String getUser() {
        return user;
    }
}
