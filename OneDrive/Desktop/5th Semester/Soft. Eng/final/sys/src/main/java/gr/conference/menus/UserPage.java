package gr.conference.menus;

public class UserPage {
	public UserPage()
	{
		loadPage();
	}
	
	private void loadPage()
	{
		String name = LoginPage.getUsername();
		System.out.println("--------------------");
		System.out.println("WELCOME " + name);
		System.out.println("--------------------");
	}
}
