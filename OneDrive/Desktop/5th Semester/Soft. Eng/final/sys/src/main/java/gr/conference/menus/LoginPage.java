package gr.conference.menus;

import java.io.Console;
import java.util.Arrays;
import java.util.Scanner;

import gr.conference.sys.RestClient;
import gr.conference.sys.UserDBHandler;



public class LoginPage {
	
	public String username;
	
	

	public void loadPage() 
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
		username = username_local;
		UserPage uPage = new UserPage(username);
		scanner.close();
	}	
}


