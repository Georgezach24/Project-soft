package gr.conference.menus;

import java.io.Console;
import java.util.Arrays;
import java.util.Scanner;

import gr.conference.sys.RestClient;



public class LoginPage {
	
	public LoginPage()
	{
		loadPage();
	}

	private void loadPage() {
	    Console console = System.console();
	    if (console != null) {
	        RestClient.loginRequest();
	        System.out.print("Username: ");
	        String username = console.readLine();
	        char[] passwordChars = console.readPassword("Password: ");

	        String password = new String(passwordChars);

	        RestClient.loginPost(username, password);

	        // Clear the password characters from memory
	        Arrays.fill(passwordChars, ' ');
	    } else {
	        // Fallback for environments where console is not available
	        Scanner scanner = new Scanner(System.in);
	        RestClient.loginRequest();
	        System.out.print("Username: ");
	        String username = scanner.nextLine();
	        System.out.print("Password: ");
	        String password = scanner.nextLine();

	        RestClient.loginPost(username, password);

	        // Clear the password from memory (not as secure as char array, but a basic attempt)
	        password = null;
	    }
	}

}
