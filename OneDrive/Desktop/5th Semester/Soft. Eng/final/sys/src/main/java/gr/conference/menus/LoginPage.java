package gr.conference.menus;

import java.util.Scanner;

import gr.conference.sys.RestClient;

public class LoginPage {
	
	public LoginPage()
	{
		loadPage();
	}
	
	private void loadPage()
	{
		Scanner scanner = new Scanner(System.in);
		RestClient.loginRequest();;
	    System.out.println("Username: ");
	    String username = scanner.nextLine();
	    System.out.println("Password: ");
	    String password = scanner.nextLine();
	    RestClient.loginPost(username,password);
	    scanner.close();
	}
}
