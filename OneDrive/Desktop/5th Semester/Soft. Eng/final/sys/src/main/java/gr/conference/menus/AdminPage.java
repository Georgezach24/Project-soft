package gr.conference.menus;

import java.util.Scanner;

public class AdminPage {
	
	public AdminPage()
	{
		loadPage();
	}
	
	private void loadPage()
	{
		int flag = 0;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("--------------------");
		System.out.println("WELCOME ADMIN");
		System.out.println("--------------------");
		System.out.println("1. Admin settings");
		System.out.println("2. Create new conference");
		System.out.println("3. ");
		System.out.print(">");
		int input = scanner.nextInt();
		
		while (flag == 0) 
		{
			switch (input) {
			case 1: 
				flag = 1;
				AdminSettingsPage asp = new AdminSettingsPage();
				break;
			
			default:
				flag = 1;
				loadPage();
				break;
			}	
		}
	}
}
