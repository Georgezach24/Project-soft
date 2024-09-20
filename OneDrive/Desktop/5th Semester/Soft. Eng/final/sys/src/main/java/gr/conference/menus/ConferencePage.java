package gr.conference.menus;

import java.util.Scanner;

import gr.conference.confsys.*;
import gr.conference.papersys.Paper;
import gr.conference.papersys.RestClient;

public class ConferencePage {

    public ConferencePage(String username, String confName) {
        loadPage(username, confName);
    }

    private void loadPage(String username, String confName) {
        Scanner scanner = new Scanner(System.in);
        Integer flag = 0;

        System.out.println(username.toUpperCase() + " you are inside conference: " + confName.toUpperCase());
        System.out.println("1. Update conference info");
        System.out.println("2. Create new Paper");
        System.out.println("3. Back");
        System.out.print("> ");
        int input = scanner.nextInt();

        while (flag == 0) {
            switch (input) {
                case 1:
                    flag = 1;
                    updateInfo(confName, username);
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

    // Update conference information
    private void updateInfo(String confName, String username) {
        Scanner scanner = new Scanner(System.in);

        RestClient.confUpdateRequest();
        System.out.print("Insert new conference name: ");
        String newName = scanner.nextLine();
        System.out.print("Insert new conference description: ");
        String desc = scanner.nextLine();
        RestClient.confUpdatePost(confName, newName, desc);
        System.out.println("Conference information updated successfully.");

        // Reload conference page after update
        ConferencePage cp = new ConferencePage(username, confName);
    }

    // Create a new paper
    private void createPaper(String confName, String username) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Creating a new paper in conference: " + confName);
        System.out.print("Insert Paper title: ");
        String paperTitle = scanner.nextLine();

        System.out.print("Insert Paper abstract: ");
        String paperAbstract = scanner.nextLine();

        System.out.print("Insert Paper content: ");
        String paperContent = scanner.nextLine();

        // Call RestClient to create the paper
        String response = RestClient.paperCreatePost(paperTitle, username, confName, paperAbstract, paperContent);

        // Check response
        if (response.contains("Paper created successfully")) {
            System.out.println("Paper created successfully.");
            createPaperSuccessScreen(username, confName, paperTitle);
        } else {
            System.out.println("Error creating paper: " + response);
            loadPage(username, confName);
        }
    }

    // After successfully creating a paper, show a confirmation screen
    private void createPaperSuccessScreen(String username, String confName, String paperTitle) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Paper '" + paperTitle + "' has been created successfully.");
        System.out.println("1. Create another paper");
        System.out.println("2. Return to conference page");
        System.out.print("> ");
        int input = scanner.nextInt();

        switch (input) {
            case 1:
                createPaper(confName, username);
                break;
            case 2:
                loadPage(username, confName);
                break;
            default:
                System.out.println("Invalid input, returning to conference page.");
                loadPage(username, confName);
                break;
        }
    }
}
