package gr.conference.menus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gr.conference.usersys.RestClient;
import gr.conference.usersys.UserDBHandler;

public class LoginPage {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    
    public String username;

    public void loadPageUser() {
        frame = new JFrame("User Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        frame.add(new JLabel("Username:"));
        usernameField = new JTextField();
        frame.add(usernameField);

        frame.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        frame.add(passwordField);

        messageLabel = new JLabel("", JLabel.CENTER);
        frame.add(messageLabel);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserLogin();
            }
        });

        frame.add(loginButton);
        frame.setVisible(true);
    }

    private void handleUserLogin() {
        String username_local = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String ret = RestClient.loginPost(username_local, password);

        JsonObject response = new Gson().fromJson(ret, JsonObject.class);
        String responseCode = response.get("responseCode").getAsString();
        String responseMessage = response.get("responseMessage").getAsString();

        if (responseCode.equals("200")) {
            messageLabel.setText("Login successful.");
            username = username_local;
            new UserPage(username); // Μετάβαση στη σελίδα χρήστη
            frame.dispose(); // Κλείσιμο παραθύρου login
        } else {
            messageLabel.setText("Login failed. Try again.");
            UserDBHandler.loginTries--; // Μείωση προσπαθειών σε περίπτωση αποτυχίας
        }

        if (UserDBHandler.loginTries == 0) {
            messageLabel.setText("Too many failed attempts. Access denied.");
            frame.dispose();
            new StartingScreen(); // Μετάβαση στην αρχική οθόνη
        }
    }

    public void loadPageAdmin() {
        frame = new JFrame("Admin Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        frame.add(new JLabel("Username:"));
        usernameField = new JTextField();
        frame.add(usernameField);

        frame.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        frame.add(passwordField);

        messageLabel = new JLabel("", JLabel.CENTER);
        frame.add(messageLabel);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAdminLogin();
            }
        });

        frame.add(loginButton);
        frame.setVisible(true);
    }

    private void handleAdminLogin() {
        String username_local = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String ret = RestClient.loginPost(username_local, password);

        JsonObject response = new Gson().fromJson(ret, JsonObject.class);
        String responseCode = response.get("responseCode").getAsString();
        String responseMessage = response.get("responseMessage").getAsString();

        if (responseCode.equals("200")) {
            messageLabel.setText("Login successful.");
            new UserPage("admin"); // Μετάβαση στη σελίδα διαχειριστή
            frame.dispose(); // Κλείσιμο παραθύρου login
        } else {
            messageLabel.setText("Admin login failed. Try again.");
            UserDBHandler.loginTries--; // Μείωση προσπαθειών σε περίπτωση αποτυχίας
        }

        if (UserDBHandler.loginTries == 0) {
            messageLabel.setText("Too many failed attempts. Access denied.");
            frame.dispose();
            new StartingScreen(); // Μετάβαση στην αρχική οθόνη
        }
    }
}
