package gr.conference.runClass;


import gr.conference.menus.StartingScreen;
import gr.conference.usersys.UserDBHandler;


public class Main {
//This is main..
	public static void main(String[] args) {
		UserDBHandler.registerAdmin();
		StartingScreen sc = new StartingScreen();
	}

}
