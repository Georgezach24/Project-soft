package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gr.conference.usersys.UserDBHandler;

class deleteUserTestCase {

    @BeforeAll
    public static void setUp() {
        new UserDBHandler();
    }

    @Test
    public void testDeleteUser() {
        String usernameToDelete = "userToDelete";

        // Assuming that the user exists before the test
        UserDBHandler.registerUser(usernameToDelete, "User02!@", "User02!@", "test@example.com", "123456789");

        boolean result = UserDBHandler.deleteUser(usernameToDelete);

        assertTrue(result);
    }

    @Test
    public void testDeleteNonExistingUser() {
        String nonExistingUsername = "nonExistingUser";

        boolean result = UserDBHandler.deleteUser(nonExistingUsername);

        assertFalse(result);
    }
}
