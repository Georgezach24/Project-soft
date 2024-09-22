package gr.conference.menus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gr.conference.papersys.RestClient;

public class ConferencePage {

    private JFrame frame;
    private JTextField inputField;
    private JLabel outputLabel;

    public ConferencePage(String username, String confName) {
        initialize(username, confName);
    }

    private void initialize(String username, String confName) {
        frame = new JFrame("Conference: " + confName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Μέγεθος παραθύρου
        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));

        JLabel welcomeLabel = new JLabel(username.toUpperCase() + " you are inside conference: " + confName.toUpperCase(), JLabel.CENTER);
        JLabel label1 = new JLabel("1. Update conference info", JLabel.CENTER);
        JLabel label2 = new JLabel("2. Create new Paper", JLabel.CENTER);
        JLabel label3 = new JLabel("3. Back", JLabel.CENTER);

        outputLabel = new JLabel("", JLabel.CENTER);

        menuPanel.add(welcomeLabel);
        menuPanel.add(label1);
        menuPanel.add(label2);
        menuPanel.add(label3);

        frame.add(menuPanel, BorderLayout.CENTER);
        frame.add(outputLabel, BorderLayout.NORTH);

        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput(inputField.getText(), username, confName);
                inputField.setText(""); // Καθαρισμός του πεδίου μετά την εισαγωγή
            }
        });

        frame.add(inputField, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void handleUserInput(String input, String username, String confName) {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            outputLabel.setText("Invalid input! Please enter a number.");
            return;
        }

        switch (choice) {
            case 1:
                outputLabel.setText("Updating conference info...");
                updateInfo(confName, username);
                break;
            case 2:
                outputLabel.setText("Creating new paper...");
                createPaper(confName, username);
                break;
            case 3:
                outputLabel.setText("Going back...");
                frame.dispose(); // Κλείσιμο παραθύρου
                new UserPage(username); // Επιστροφή στη σελίδα χρήστη
                break;
            default:
                outputLabel.setText("Invalid choice! Please select a valid option.");
                break;
        }
    }

    private void updateInfo(String confName, String username) {
        RestClient.confUpdateRequest();
        String newName = JOptionPane.showInputDialog(frame, "Insert new conference name:");
        String desc = JOptionPane.showInputDialog(frame, "Insert new conference description:");
        RestClient.confUpdatePost(confName, newName, desc);
        outputLabel.setText("Conference information updated successfully.");
    }

    private void createPaper(String confName, String username) {
        String paperTitle = JOptionPane.showInputDialog(frame, "Insert Paper title:");
        String paperAbstract = JOptionPane.showInputDialog(frame, "Insert Paper abstract:");
        String paperContent = JOptionPane.showInputDialog(frame, "Insert Paper content:");

        String response = RestClient.paperCreatePost(paperTitle, username, confName, paperAbstract, paperContent);

        if (response.contains("Paper created successfully")) {
            outputLabel.setText("Paper created successfully.");
            JOptionPane.showMessageDialog(frame, "Paper '" + paperTitle + "' has been created successfully.");
        } else {
            outputLabel.setText("Error creating paper: " + response);
        }
    }
}
