package gr.conference.menus;

import java.util.Scanner;

import jakarta.validation.constraints.Pattern.Flag;

public class UserPage {
	public UserPage(String usernameString)
	{
		loadPage(usernameString);
	}
	
	private void loadPage(String uString)
	{
		int flag = 0;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("--------------------");
		System.out.println("WELCOME " + uString.toUpperCase());
		System.out.println("--------------------");
		System.out.println("1. User settings");
		System.out.println("2. Create new conference");
		System.out.println("3. ");
		System.out.print(">");
		int input = scanner.nextInt();
		
		while (flag == 0) 
		{
			switch (input) {
			case 1: 
				flag = 1;
				UserSettingsPage userSettingsPage = new UserSettingsPage(uString);
				break;
			
			default:
				flag = 1;
				loadPage(uString);
				break;
			}	
		}
	}
}
