
package gr.conference.menus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gr.conference.confsys.*;

public class ConferencePage {

    public ConferencePage(String username) {
        loadPage(username);
    }

    public void loadPage(String username) {
        // Create the JFrame for the unified input
        JFrame frame = new JFrame("ConferencePage Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));  // Adjust size as necessary

        // Add labels and text fields
        JLabel inputLabel1 = new JLabel("Input 1: ");
        JTextField inputField1 = new JTextField(20);
        JLabel inputLabel2 = new JLabel("Input 2: ");
        JTextField inputField2 = new JTextField(20);

        // Create the submit button
        JButton submitButton = new JButton("Submit");

        // Add components to the frame
        frame.add(inputLabel1);
        frame.add(inputField1);
        frame.add(inputLabel2);
        frame.add(inputField2);
        frame.add(new JLabel());  // Empty cell in grid
        frame.add(submitButton);

        // Button action to handle submission
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input1 = inputField1.getText();
                String input2 = inputField2.getText();

                if (input1.isEmpty() || input2.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Here you can handle the input and call other methods based on the logic needed
                    frame.dispose();  // Close the frame
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
