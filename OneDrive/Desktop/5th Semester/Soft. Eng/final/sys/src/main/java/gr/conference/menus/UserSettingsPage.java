package gr.conference.menus;

import java.util.InputMismatchException;
import java.util.Scanner;
import gr.conference.usersys.RestClient;

public class UserSettingsPage {

    public UserSettingsPage(String usernameString) {
        loadPage(usernameString);
    }

    private void loadPage(String username) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;

        while (!flag) {
            try {
                System.out.println("-----------------------------");
                System.out.println("1. Information update");
                System.out.println("2. Password update");
                System.out.println("3. Delete Account");
                System.out.println("4. Back");
                System.out.print(">");
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.println("-----------------------------");

                switch (input) {
                    case 1:
                        username = infoUpdate(username, scanner); // Update username if changed
                        break;
                    case 2:
                        passwordUpdate(username, scanner);
                        break;
                    case 3:
                        deleteAccount(username, scanner);
                        new StartingScreen(); // Go back to the starting screen after account deletion
                        flag = true;
                        return;
                    case 4:
                        flag = true;
                        new UserPage(username); // Go back to the user page
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        scanner.close();
    }

    private String infoUpdate(String username, Scanner scanner) {
        // No need to call RestClient.updateRequest() as it's not functional
        System.out.println("Updating Information:");

        System.out.print("Write your new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Write your new Name: ");
        String newName = scanner.nextLine();
        System.out.print("Write your new Surname: ");
        String newSurname = scanner.nextLine();
        System.out.print("Write your new Email: ");
        String newEmail = scanner.nextLine();
        System.out.print("Write your new Phone: ");
        String newPhone = scanner.nextLine();

        // Call RestClient to update user info
        RestClient.updatePost(username, newUsername, newName, newSurname, newEmail, newPhone);

        // If the username has changed, return the new username
        return newUsername.isBlank() ? username : newUsername;
    }

    private void passwordUpdate(String username, Scanner scanner) {
        // No need to call RestClient.passwordUpdateRequest() as it's not functional
        System.out.println("Updating Password:");
        System.out.print("Write your old password: ");
        String oldPassword = scanner.nextLine();
        System.out.print("Write your new password: ");
        String newPassword = scanner.nextLine();

        // Call RestClient to update the password
        RestClient.passwordUpdatePost(username, oldPassword, newPassword);
    }

    private void deleteAccount(String username, Scanner scanner) {
        // No need to call RestClient.deleteRequest() as it's not functional
        System.out.println("Are you sure you want to delete your account? (y/n)");
        System.out.print(">");

        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y")) {
            RestClient.deletePost(username);
            System.out.println("Your account has been deleted.");
        } else {
            System.out.println("Account deletion canceled.");
        }
    }
}
