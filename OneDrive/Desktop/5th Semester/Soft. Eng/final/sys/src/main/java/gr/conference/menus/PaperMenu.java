package gr.conference.menus;

import gr.conference.papersys.PaperDBHandler;
import gr.conference.papersys.RestClient;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class PaperMenu {

    public PaperMenu(String username, String confName) {
        loadPage(username, confName);
    }

    private void loadPage(String username, String confName) {
        
        JOptionPane.showMessageDialog(null,(username.toUpperCase() + " you are inside the paper system for conference: " + confName.toUpperCase()));
        JOptionPane.showMessageDialog(null,("1. Create new Paper"));
        JOptionPane.showMessageDialog(null,("2. Update Paper"));
        JOptionPane.showMessageDialog(null,("3. Search Paper"));
        JOptionPane.showMessageDialog(null,("4. Back"));
        JOptionPane.showMessageDialog(null,("> "));
        int input = Integer.parseInt(JOptionPane.showInputDialog(null));
        JOptionPane.showInputDialog(null); // Consume newline

        switch (input) {
            case 1:
                createPaper(confName, username);
                break;
            case 2:
                updatePaper(confName, username);
                break;
            case 3:
                searchPaper(confName, username);
                break;
            case 4:
                new ConferencePage(username, confName);
                break;
            default:
                JOptionPane.showMessageDialog(null,("Invalid input, try again."));
                loadPage(username, confName);
                break;
        }
    }

    private void createPaper(String confName, String username) {
        
        JOptionPane.showMessageDialog(null,("Insert Paper title: "));
        String title = JOptionPane.showInputDialog(null);
        JOptionPane.showMessageDialog(null,("Insert Paper abstract: "));
        String abstractText = JOptionPane.showInputDialog(null);
        JOptionPane.showMessageDialog(null,("Insert Paper content: "));
        String content = JOptionPane.showInputDialog(null);

        String response = RestClient.paperCreatePost(title, username, confName, abstractText, content);
        JOptionPane.showMessageDialog(null,(response));
    }

    private void updatePaper(String confName, String username) {
    }

    private void searchPaper(String confName, String username) {
    }
}
