package gr.conference.menus;

import java.util.Scanner;

import javax.swing.JOptionPane;

import gr.conference.confsys.*;

public class ConferenceCreatePage {
	public ConferenceCreatePage(String username)
	{
		loadPage(username);
	}
	
	public void loadPage(String username)
	{
		
		RestClient.confCreateRequest();
		JOptionPane.showMessageDialog(null,("Insert the conference name: "));
		String name = JOptionPane.showInputDialog(null);
		JOptionPane.showMessageDialog(null,("Insert the conference description: "));
		String desc = JOptionPane.showInputDialog(null);
		
		String output= RestClient.confCreatePost(name, username, desc);
		
		if(output.equals("{\"responseMessage\":\"Conference created successfully\",\"responseCode\":\"200\"}"))
		{
			ConferencePage cp = new ConferencePage(username,name);
		}
		else {
			UserPage up = new UserPage(username);
		}
	}
}
