package gr.conference.menus;

public class UserPage {
	public UserPage(String usernameString)
	{
		loadPage(usernameString);
	}
	
	private void loadPage(String uString)
	{
		System.out.println("--------------------");
		System.out.println("WELCOME " + uString);
		System.out.println("--------------------");
	}
}
