package gr.conference.menus;

import java.util.Scanner;

import gr.conference.sys.RestClient;

public class RegisterPage {
	public RegisterPage()
	{
		loadPage();
	}
	
	private void loadPage()
	{
		Scanner scanner = new Scanner(System.in);
		RestClient.registerRequest();
	    System.out.println("Username: ");
	    String username = scanner.nextLine();
	    System.out.println("Password: ");
	    String password = scanner.nextLine();
	    System.out.println("Rewrite Password: ");
	    String password_conf = scanner.nextLine();
	    System.out.println("Email: ");
	    String email = scanner.nextLine();
	    System.out.println("Phone: ");
	    String phone = scanner.nextLine();
	    RestClient.registerPost(username,password,password_conf,email,phone);
	    scanner.close();
	}
			
}
