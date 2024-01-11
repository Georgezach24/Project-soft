package Testcases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gr.conference.usersys.UserDBHandler;





public class UsernamePasswordTestCase {

    @Test
    @DisplayName ("Password Tests")
    public void testIsPasswordValid() {
        assertTrue(UserDBHandler.isPasswordValid("ValidP@ss1"), "Valid password failed");
        assertFalse(UserDBHandler.isPasswordValid("weakpass"), "Weak password passed");
        assertFalse(UserDBHandler.isPasswordValid("NoSpecialChar1"), "Password without special character passed");
        assertFalse(UserDBHandler.isPasswordValid("Short1!"), "Short password passed");
        assertFalse(UserDBHandler.isPasswordValid("NOLOWERCASE1!"), "Password without lowercase passed");
        assertFalse(UserDBHandler.isPasswordValid("nouppercase1!"), "Password without uppercase passed");
        assertFalse(UserDBHandler.isPasswordValid("nouppercase1"), "Password without special character and uppercase passed");
    }

    @Test
    @DisplayName ("Username Tests")
    public void testIsUsernameValid() {
        assertTrue(UserDBHandler.isUsernameValid("ValidUser_123"), "Valid username failed");
        assertFalse(UserDBHandler.isUsernameValid("inv"), "Short username passed");
        assertFalse(UserDBHandler.isUsernameValid("123invalid"), "Username starting with a digit passed");
        assertFalse(UserDBHandler.isUsernameValid("invalid!"), "Username with special character passed");
        assertFalse(UserDBHandler.isUsernameValid("_invalid"), "Username starting with an underscore passed");
        assertTrue(UserDBHandler.isUsernameValid("Valid_Use_123"), "Valid username with underscores failed");
    }
}

