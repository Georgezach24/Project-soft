package gr.conference.menus;

import java.util.List;
import java.util.Scanner;

import gr.conference.confsys.Conference;
import gr.conference.confsys.RestClient;

public class ConferenceSearchPage {
	public ConferenceSearchPage(String username)
	{
		loadPage(username);
	}
	
	private void loadPage(String username)
	{
		Scanner scanner = new Scanner(System.in);
		
		RestClient.confSearchRequest();
		System.out.print("Conference name: ");
		String name = scanner.nextLine();
		System.out.print("Conference description: ");
		String desc = scanner.nextLine();
		List <Conference> searchRes = RestClient.confSearchPost(name,desc);
		
		if(!searchRes.isEmpty())
		{
			System.out.println(searchRes);
			UserPage up = new UserPage(username);
		}
		else
		{
			UserPage up = new UserPage(username);
		}
		
	}
}
