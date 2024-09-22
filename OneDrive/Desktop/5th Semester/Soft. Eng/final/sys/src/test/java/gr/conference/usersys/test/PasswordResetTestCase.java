package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityTransaction;

public class PasswordResetTestCase {

    private EntityManager em;
    private EntityTransaction et;
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");

    @BeforeEach
    public void setUp() {
        em = ENTITY_MANAGER_FACTORY.createEntityManager();
        et = em.getTransaction();
        et.begin();

        // Προσθήκη χρηστών για τις δοκιμές
        UserDBHandler.registerUser("User008d", "User00812!", "User00812!", "test@example.com", "123456789");
        UserDBHandler.registerUser("User009d", "User00912!!", "User00912!!", "test@example.com", "123456789");
        et.commit();
        em.close();
    }

    @AfterEach
    public void tearDown() {
        em = ENTITY_MANAGER_FACTORY.createEntityManager();
        et = em.getTransaction();
        et.begin();

        // Διαγραφή των χρηστών μετά από κάθε δοκιμή
        em.createQuery("DELETE FROM User u WHERE u.username IN ('User008d', 'User009d')").executeUpdate();
        et.commit();
        em.close();
    }

    @ParameterizedTest
    @CsvSource({
        "User008d, User00812!, User00712!, true",   // Επιτυχής αλλαγή κωδικού
        "User009d, User00912!!, invalid_password, false",  // Αποτυχία λόγω μη έγκυρου νέου κωδικού
        "User009d, User00912!!, Pass, false",  // Αποτυχία λόγω πολύ μικρού νέου κωδικού
        "User008d, User00812!, NoSpecialChar1, false"  // Αποτυχία λόγω νέου κωδικού χωρίς ειδικό χαρακτήρα
    })
    public void testUpdatePassword(String username, String oldPassword, String newPassword, boolean expectedResult) {
        // Κλήση της updatePassword με τις παραμέτρους
        boolean result = UserDBHandler.updatePassword(username, oldPassword, newPassword);
        assertEquals(expectedResult, result);

        if (expectedResult) {
            // Έλεγχος ότι ο νέος κωδικός λειτουργεί για login
            System.out.println("Testing login with new password...");
            boolean canLoginWithNewPassword = UserDBHandler.loginUser(username, newPassword) != null;
            assertTrue(canLoginWithNewPassword, "The user should be able to login with the new password.");
        } else {
            // Έλεγχος ότι ο παλιός κωδικός λειτουργεί για login αν η αλλαγή απέτυχε
            System.out.println("Testing login with old password...");
            boolean canLoginWithOldPassword = UserDBHandler.loginUser(username, oldPassword) != null;
            assertTrue(canLoginWithOldPassword, "The user should be able to login with the old password.");
        }
    }
}
