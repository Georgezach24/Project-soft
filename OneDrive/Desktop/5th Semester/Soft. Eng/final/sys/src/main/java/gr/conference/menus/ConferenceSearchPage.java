package gr.conference.menus;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import gr.conference.confsys.Conference;
import gr.conference.confsys.RestClient;

public class ConferenceSearchPage {

    private JFrame frame;
    private JTextField nameField;
    private JTextField descField;
    private JTextArea resultArea;

    public ConferenceSearchPage(String username) {
        initialize(username);
    }

    private void initialize(String username) {
        frame = new JFrame("Search Conference");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Conference Name: ");
        nameField = new JTextField();

        JLabel descLabel = new JLabel("Conference Description: ");
        descField = new JTextField();

        searchPanel.add(nameLabel);
        searchPanel.add(nameField);
        searchPanel.add(descLabel);
        searchPanel.add(descField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchConferences(username);
            }
        });

        searchPanel.add(new JLabel()); // Κενό για ευθυγράμμιση
        searchPanel.add(searchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Κλείσιμο παραθύρου
                new UserPage(username); // Επιστροφή στη σελίδα χρήστη
            }
        });

        frame.add(backButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void searchConferences(String username) {
        String name = nameField.getText();
        String desc = descField.getText();

        RestClient.confSearchRequest();
        List<Conference> searchResults = RestClient.confSearchPost(name, desc);

        if (!searchResults.isEmpty()) {
            StringBuilder resultText = new StringBuilder("Search Results:\n");
            for (Conference conf : searchResults) {
                resultText.append("Name: ").append(conf.getName()).append("\n");
                resultText.append("Description: ").append(conf.getDesc()).append("\n");
                resultText.append("Date: ").append(conf.getDate()).append("\n\n");
            }
            resultArea.setText(resultText.toString());
        } else {
            resultArea.setText("No conferences found.");
        }
    }
}
