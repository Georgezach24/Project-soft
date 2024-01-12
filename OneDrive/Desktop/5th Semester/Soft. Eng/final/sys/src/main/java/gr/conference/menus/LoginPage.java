package gr.conference.menus;


import java.util.Scanner;

import gr.conference.usersys.RestClient;
import gr.conference.usersys.UserDBHandler;



public class LoginPage {
	
	public String username;
	
	

	public void loadPageUser() 
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
	        	username = username_local;
	        	UserPage uPage = new UserPage(username);
	        	break;
	        }
	    }
		
		scanner.close();
	}	
	
	public void loadPageAdmin() 
	{
		Scanner scanner = new Scanner(System.in);
	    RestClient.AdminloginRequest();
	    String username_local = "";
		while(UserDBHandler.loginTries != 0)
		{
	        System.out.print("Username: ");
	        username_local = scanner.nextLine();
	        System.out.print("Password: ");
	        String password = scanner.nextLine();
	        String ret = RestClient.AdminLoginPost(username_local, password);
	        if(ret.equals("{\"responseMessage\":\"Succes!\",\"responseCode\":\"200\"}"))
	        {
	        	AdminPage ap = new AdminPage();
	        	break;
	        }
	    }
	}
}


