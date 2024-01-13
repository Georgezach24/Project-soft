package gr.conference.usersys.test;

import org.junit.jupiter.api.Test;

import gr.conference.usersys.UserDBHandler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

public class RegistryTest {

    @Test
    public void testRegisterUser() {
        try {
            // Test registration with an existing username
            UserDBHandler.registerUser("existingUser", "StrongP@ss1", "StrongP@ss1", "test@example.com", "123456789");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown: " + e.getMessage());
        }

        // Add similar try-catch blocks for other assertions...
    }
}