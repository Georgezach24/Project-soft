package gr.conference.menus;

import java.util.Scanner;

import gr.conference.usersys.RestClient;

public class AdminSettingsPage {
	public AdminSettingsPage()
	{
		loadPage();
	}
	
	private void loadPage()
	{
		int flag = 0;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("-----------------------------");
		System.out.println("1. Information update");
		System.out.println("2. Password update");
		System.out.println("3. Delete Account");
		System.out.println("4. Account status update");
		System.out.println("5. Back");
		System.out.print(">");
		int input = scanner.nextInt();
		System.out.println("-----------------------------");
		
		while(flag ==0)
		{
			switch (input)
			{
				case 1:
					flag = 1;
					infoUpdate();
					break;
				case 2:
					flag = 1;
					passwordUpdate();
					break;
				case 3:
					flag = 1;
					deleteAccount();
					break;
				case 4:
					flag = 1;
					statusUpdate();
					break;
				case 5:
					flag = 1;
					AdminPage ap = new AdminPage();
					break;
				default:
					flag = 1;
					loadPage();
					break;
			}
			
		}
		
		AdminPage ap = new AdminPage();
	}
	
	private String infoUpdate()
	{
		Scanner scanner = new Scanner(System.in);
		
		RestClient.updateRequest();
		System.out.print("Write the current username: ");
		String username = scanner.nextLine();
		System.out.print("Write the new username: ");
		String usernameString = scanner.nextLine();
		System.out.print("Write the new Name: ");
		String nameString = scanner.nextLine();
		System.out.print("Write the new Surname: ");
		String surnameString = scanner.nextLine();
		System.out.print("Write the new Email: ");
		String emailString = scanner.nextLine();
		System.out.print("Write the new Phone: ");
		String phoneString = scanner.nextLine();
		
		RestClient.updatePost(username, usernameString, nameString, surnameString, emailString, phoneString);
		
		return usernameString;
		
	}
	
	private void passwordUpdate()
	{
		Scanner scanner = new Scanner(System.in);
		
		RestClient.passwordUpdateRequest();
		System.out.print("Write the username: ");
		String username = scanner.nextLine();
		System.out.print("Write the old password: ");
		String oldPassword = scanner.nextLine();
		System.out.print("Write the new password: ");
		String newPassword = scanner.nextLine();
		
		RestClient.passwordUpdatePost(username , oldPassword, newPassword);
	}
	
	private void deleteAccount()
	{
		Scanner scanner = new Scanner(System.in);
		
		RestClient.deleteRequest();
		System.out.print("Write the username: ");
		String username = scanner.nextLine();
		System.out.println("Are you sure you want to delete this account?(y/n)");
		System.out.print(">");
		String input = scanner.nextLine();
		
		if(input.equals("y") || input.equals("Y"))
		{
			RestClient.deletePost(username);
		}
	}
	
	private void statusUpdate()
	{
		Scanner scanner = new Scanner(System.in);
		RestClient.updateStatusRequest();
		System.out.print("Write the username: ");
		String username = scanner.nextLine();
		System.out.print("Write the status(ACTIVE/INACTIVE): ");
		String status = scanner.nextLine();
		RestClient.updateStatusPost(username, status);
	}
}

