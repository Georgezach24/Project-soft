package gr.conference.menus;

import java.util.Scanner;


import gr.conference.confsys.*;


public class ConferencePage {
	public ConferencePage(String username ,String confName)
	{
		loadPage(username , confName);
	}
	
	private void loadPage(String username , String confName)
	{
		Scanner scanner = new Scanner(System.in);
		Integer flag = 0;
		
		System.out.println(username.toUpperCase() + " you are inside conference: " + confName.toUpperCase());
		System.out.println("1. Update conference info");
		System.out.println("2. Create new Paper");
		System.out.println("3. Back");
		System.out.print("> ");
		int input = scanner.nextInt();
		
		while(flag == 0)
		{
			switch(input)
			{
			case 1:
				flag = 1;
				updateInfo(username,confName);
				break;
			case 2:
				flag = 1;
				createPaper(confName, username);
				break;
			case 3:
				flag = 1;
				UserPage up = new UserPage(username);
				break;
			
			default:
				flag = 1;
				loadPage(username, confName);
				break;
			}
		}
	}
	
	private void updateInfo(String confName , String username)
	{
		Scanner scanner = new Scanner(System.in);
		
		RestClient.confUpdateRequest();
		System.out.print("Insert new name: ");
		String newName = scanner.nextLine();
		System.out.print("Insert new desc: ");
		String desc = scanner.nextLine();
		RestClient.confUpdatePost(confName, newName, desc);
		ConferencePage cp = new ConferencePage(username, confName);
	}
	
	private void createPaper(String confName, String username)
	{
		Scanner scanner = new Scanner(System.in);
		
		gr.conference.papersys.RestClient.createPaperRequest();
		System.out.print("Insert Paper title: ");
		String paper_nameString = scanner.nextLine();
		gr.conference.papersys.RestClient.paperCreatePost(paper_nameString, username, confName);
	}
}
