
package gr.conference.menus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gr.conference.confsys.*;

public class ConferenceCreatePage {

    public ConferenceCreatePage(String username) {
        loadPage(username);
    }

    public void loadPage(String username) {
        // Create the JFrame for the unified input
        JFrame frame = new JFrame("Create Conference");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 2));

        // Add labels and text fields
        JLabel nameLabel = new JLabel("Conference Name: ");
        JTextField nameField = new JTextField(20);
        JLabel descLabel = new JLabel("Conference Description: ");
        JTextField descField = new JTextField(20);

        // Create the submit button
        JButton submitButton = new JButton("Submit");

        // Add components to the frame
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(descLabel);
        frame.add(descField);
        frame.add(new JLabel());  // Empty cell in grid
        frame.add(submitButton);

        // Button action to handle submission
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String desc = descField.getText();

                if (name.isEmpty() || desc.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Call the REST client to create the conference
                    String output = RestClient.confCreatePost(name, username, desc);

                    if (output.equals("{"responseMessage":"Conference created successfully","responseCode":"200"}")) {
                        new ConferencePage(username, name);
                        frame.dispose();  // Close the frame
                    } else {
                        new UserPage(username);
                        frame.dispose();  // Close the frame
                    }
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
