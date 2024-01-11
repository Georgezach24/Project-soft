package gr.conference.menus;

import java.util.Scanner;

import gr.conference.usersys.RestClient;
import javassist.expr.NewArray;


public class UserSettingsPage {
	
	public UserSettingsPage(String usernameString)
	{
		loadPage(usernameString);
	}
	
	private void loadPage(String username)
	{
		int flag = 0;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("-----------------------------");
		System.out.println("1. Information update");
		System.out.println("2. Password update");
		System.out.println("3. Delete Account");
		System.out.println("4. Back");
		System.out.print(">");
		int input = scanner.nextInt();
		System.out.println("-----------------------------");
		
		while(flag ==0)
		{
			switch (input)
			{
				case 1:
					flag = 1;
					infoUpdate(username);
					break;
				case 2:
					backOption(username);
					break;
				case 3:
					break;
				case 4:
					flag = 1;
					UserPage uPage = new UserPage(username);
					break;
				default:
					flag = 1;
					loadPage(username);
					break;
			}
		}
	}
	
	private void infoUpdate(String username)
	{
		Scanner scanner = new Scanner(System.in);
		
		RestClient.updateRequest();
		System.out.print("Write your new username: ");
		String usernameString = scanner.nextLine();
		System.out.print("Write your new Name: ");
		String nameString = scanner.nextLine();
		System.out.print("Write your new Surname: ");
		String surnameString = scanner.nextLine();
		System.out.print("Write your new Email: ");
		String emailString = scanner.nextLine();
		System.out.print("Write your new Phone: ");
		String phoneString = scanner.nextLine();
		String ret = RestClient.updatePost(username,usernameString, nameString, surnameString, emailString, phoneString);
	}
	
	private void backOption(String usernameString)
	{
		UserPage uPage = new UserPage(usernameString);
	}
}
