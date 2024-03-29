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
					String newUsername = infoUpdate(username);
					if(!username.equals(newUsername))
					{
						username = newUsername;
					}
					break;
				case 2:
					flag = 1;
					passwordUpdate(username);
					break;
				case 3:
					flag = 1;
					deleteAccount(username);
					StartingScreen sc = new StartingScreen();
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
		
		UserPage up = new UserPage(username);
	}
	
	private String infoUpdate(String username)
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
		
		RestClient.updatePost(username, usernameString, nameString, surnameString, emailString, phoneString);
		
		return usernameString;
		
	}
	
	private void passwordUpdate(String username)
	{
		Scanner scanner = new Scanner(System.in);
		
		RestClient.passwordUpdateRequest();
		System.out.print("Write your old password: ");
		String oldPassword = scanner.nextLine();
		System.out.print("Write your new password: ");
		String newPassword = scanner.nextLine();
		
		RestClient.passwordUpdatePost(username , oldPassword, newPassword);
	}
	
	private void deleteAccount(String username)
	{
		Scanner scanner = new Scanner(System.in);
		
		RestClient.deleteRequest();
		
		System.out.println("Are you sure you want to delete your account?(y/n)");
		System.out.print(">");
		String input = scanner.nextLine();
		
		if(input.equals("y") || input.equals("Y"))
		{
			RestClient.deletePost(username);
		}
	}

}
