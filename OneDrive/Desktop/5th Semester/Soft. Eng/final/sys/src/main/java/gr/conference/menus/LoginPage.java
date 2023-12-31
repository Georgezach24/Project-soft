package gr.conference.menus;

import java.io.Console;
import java.util.Arrays;
import java.util.Scanner;

import gr.conference.sys.RestClient;
import gr.conference.sys.UserDBHandler;



public class LoginPage {
	
	public static String username;
	
	public LoginPage()
	{
		loadPage();
		
	}
	

	private void loadPage() 
	{
		Scanner scanner = new Scanner(System.in);
	    RestClient.loginRequest();
	    String username_local = "";
		while(UserDBHandler.loginTries != 0)
		{
	        System.out.print("Username: ");
	        username_local = scanner.nextLine();
	        System.out.print("Password: ");
	        String password = scanner.nextLine();
	        String ret = RestClient.loginPost(username_local, password);
	        if(ret.equals("{\"responseMessage\":\"Succes!\",\"responseCode\":\"200\"}"))
	        {
	        	break;
	        }
	    }
		scanner.close();
		setUsername(username_local);
		UserPage up = new UserPage();
	}

	public static String getUsername() {
		return username;
	}


	public static void setUsername(String username_lc) {
		username_lc = username;
	}
	
	
}


