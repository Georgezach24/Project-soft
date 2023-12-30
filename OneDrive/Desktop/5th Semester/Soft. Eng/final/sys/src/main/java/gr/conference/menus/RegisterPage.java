package gr.conference.menus;

import java.util.Arrays;
import java.util.Scanner;
import java.io.Console;

import gr.conference.sys.RestClient;

import gr.conference.sys.*;

public class RegisterPage {
	public RegisterPage()
	{
		loadPage();
	}
	
	private void loadPage() {
	    Scanner scanner = new Scanner(System.in);
	    RestClient.registerRequest();

	    System.out.print("Username: ");
	    String username = scanner.nextLine();

	    System.out.print("Password: ");
	    String password = readPasswordFromConsole();

	    System.out.print("Rewrite Password: ");
	    String password_conf = readPasswordFromConsole();

	    System.out.print("Email: ");
	    String email = scanner.nextLine();

	    System.out.print("Phone: ");
	    String phone = scanner.nextLine();

	    RestClient.registerPost(username, password, password_conf, email, phone);

	    scanner.close();

	    StartingScreen ss = new StartingScreen();
	}

	private String readPasswordFromConsole() 
	{
	    Console console = System.console();
	    if (console != null) {
	        char[] passwordChars = console.readPassword();
	        return new String(passwordChars);
	    } else {
	        // Fallback for environments where console is not available
	        Scanner scanner = new Scanner(System.in);
	        return scanner.nextLine();
	    }
	}

			
}
