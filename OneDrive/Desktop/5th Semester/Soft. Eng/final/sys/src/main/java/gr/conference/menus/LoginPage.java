package gr.conference.menus;

import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import gr.conference.usersys.RestClient;
import gr.conference.usersys.UserDBHandler;

public class LoginPage {

    public String username;

    // Method to load the user login page
    public void loadPageUser() {
        Scanner scanner = new Scanner(System.in);
        String username_local = "";
        
        // Loop until loginTries is 0 or the user logs in successfully
        while (UserDBHandler.loginTries != 0) {
            System.out.print("Username: ");
            username_local = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Call RestClient to handle user login
            String ret = RestClient.loginPost(username_local, password);
            
            // Parse the response using Gson
            JsonObject response = new Gson().fromJson(ret, JsonObject.class);
            String responseCode = response.get("responseCode").getAsString();
            String responseMessage = response.get("responseMessage").getAsString();

            // Check if login was successful
            if (responseCode.equals("200")) {
                System.out.println(responseMessage); // Success message
                username = username_local;
                UserPage uPage = new UserPage(username);
                break;
            } else {
                System.out.println("Login failed. Try again.");
                UserDBHandler.loginTries--; // Decrement tries on failure
            }
        }

        if (UserDBHandler.loginTries == 0) {
            System.out.println("Too many failed attempts. Access denied.");
        }

        scanner.close();
    }

    // Method to load the admin login page
    public void loadPageAdmin() {
        Scanner scanner = new Scanner(System.in);
        String username_local = "";
        
        // Loop until loginTries is 0 or the admin logs in successfully
        while (UserDBHandler.loginTries != 0) {
            System.out.print("Username: ");
            username_local = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Call RestClient to handle admin login
            String ret = RestClient.loginPost(username_local, password); // Use unified login for both admin and users
            
            // Parse the response using Gson
            JsonObject response = new Gson().fromJson(ret, JsonObject.class);
            String responseCode = response.get("responseCode").getAsString();
            String responseMessage = response.get("responseMessage").getAsString();

            // Check if login was successful
            if (responseCode.equals("200")) {
                System.out.println(responseMessage); // Success message
                AdminPage ap = new AdminPage();
                break;
            } else {
                System.out.println("Admin login failed. Try again.");
                UserDBHandler.loginTries--; // Decrement tries on failure
            }
        }

        if (UserDBHandler.loginTries == 0) {
            System.out.println("Too many failed attempts. Access denied.");
        }

        scanner.close();
    }
}
