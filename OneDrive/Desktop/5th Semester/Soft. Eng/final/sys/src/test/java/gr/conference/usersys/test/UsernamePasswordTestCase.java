package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;
import gr.conference.usersys.UserDBHandler;

public class UsernamePasswordTestCase {

    @ParameterizedTest
    @CsvSource({
        "ValidP@ss1, true",         // Έγκυρος κωδικός πρόσβασης
        "weakpass, false",          // Αδύναμος κωδικός πρόσβασης
        "NoSpecialChar1, false",    // Κωδικός χωρίς ειδικούς χαρακτήρες
        "Short1!, false",           // Σύντομος κωδικός πρόσβασης
        "NOLOWERCASE1!, false",     // Κωδικός χωρίς πεζά γράμματα
        "nouppercase1!, false",     // Κωδικός χωρίς κεφαλαία γράμματα
        "nouppercase1, false"       // Κωδικός χωρίς ειδικούς χαρακτήρες και κεφαλαία γράμματα
    })
    @DisplayName("Password Validation Tests")
    public void testIsPasswordValid(String password, boolean expected) {
        assertEquals(expected, UserDBHandler.isPasswordValid(password), "Password validation failed for: " + password);
    }

    @ParameterizedTest
    @CsvSource({
        "ValidUser_123, true",      // Έγκυρο όνομα χρήστη
        "inv, false",               // Σύντομο όνομα χρήστη
        "123invalid, false",        // Όνομα χρήστη που ξεκινά με αριθμό
        "invalid!, false",          // Όνομα χρήστη με ειδικό χαρακτήρα
        "_invalid, false",          // Όνομα χρήστη που ξεκινά με κάτω παύλα
        "Valid_Use_123, true"       // Έγκυρο όνομα χρήστη με κάτω παύλα
    })
    @DisplayName("Username Validation Tests")
    public void testIsUsernameValid(String username, boolean expected) {
        assertEquals(expected, UserDBHandler.isUsernameValid(username), "Username validation failed for: " + username);
    }
}
