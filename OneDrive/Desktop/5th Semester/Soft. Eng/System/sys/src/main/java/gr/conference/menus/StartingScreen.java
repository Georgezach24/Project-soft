package gr.conference.menus;

import java.util.Scanner;

public class StartingScreen {
	
	public StartingScreen()
	{
		loadMenu();
	}
	
	private void loadMenu()
	{
		Scanner scanner = new Scanner(System.in);
		int flag = 0;
		
		System.out.println("------------------------------------------");
		System.out.println("WELCOME TO THE CONFERENCE SYSTEM USER PAGE");
		System.out.println("1. LOGIN");
		System.out.println("2. REGISTER");
		System.out.println("3. CONTINUE AS GUEST");
		System.out.println("4. EXIT");
		System.out.print("Your input >");
		System.out.print("Your input >");
		int input = scanner.nextInt();
		
		while(flag == 0)
		{
			switch(input)
			{
				case 1:
					flag = 1;
					LoginPage lp = new LoginPage();
					break;
				case 2:
					flag = 1;
					RegisterPage rp = new RegisterPage();
					break;
				case 3:
					flag = 1;
					//TODO Φτιάξε τι θα γίνεται σε περίπτωση visitor.
					break;
				case 4:
					flag = 1;
					System.exit(0);
					break;
				default:
					flag = 0;
					break;
			}
		}
		
	}
}
