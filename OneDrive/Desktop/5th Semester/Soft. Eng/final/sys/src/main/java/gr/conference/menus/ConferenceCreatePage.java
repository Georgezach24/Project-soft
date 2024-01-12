package gr.conference.menus;

import java.util.Scanner;
import gr.conference.confsys.*;

public class ConferenceCreatePage {
	public ConferenceCreatePage(String username)
	{
		loadPage(username);
	}
	
	public void loadPage(String username)
	{
		Scanner scanner = new Scanner(System.in);
		RestClient.confCreateRequest();
		System.out.print("Insert the conference name: ");
		String name = scanner.nextLine();
		System.out.print("Insert the conference description: ");
		String desc = scanner.nextLine();
		
		RestClient.confCreatePost(name, username, desc);
		UserPage up = new UserPage(username);
	}
}
