package gr.conference.menus;

import gr.conference.papersys.PaperDBHandler;
import gr.conference.papersys.RestClient;

import java.util.Scanner;

public class PaperMenu {

    public PaperMenu(String username, String confName) {
        loadPage(username, confName);
    }

    private void loadPage(String username, String confName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(username.toUpperCase() + " you are inside the paper system for conference: " + confName.toUpperCase());
        System.out.println("1. Create new Paper");
        System.out.println("2. Update Paper");
        System.out.println("3. Search Paper");
        System.out.println("4. Back");
        System.out.print("> ");
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline

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
                System.out.println("Invalid input, try again.");
                loadPage(username, confName);
                break;
        }
    }

    private void createPaper(String confName, String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert Paper title: ");
        String title = scanner.nextLine();
        System.out.print("Insert Paper abstract: ");
        String abstractText = scanner.nextLine();
        System.out.print("Insert Paper content: ");
        String content = scanner.nextLine();

        String response = RestClient.paperCreatePost(title, username, confName, abstractText, content);
        System.out.println(response);
    }

    private void updatePaper(String confName, String username) {
        // Update paper logic
    }

    private void searchPaper(String confName, String username) {
        // Search paper logic
    }
}
