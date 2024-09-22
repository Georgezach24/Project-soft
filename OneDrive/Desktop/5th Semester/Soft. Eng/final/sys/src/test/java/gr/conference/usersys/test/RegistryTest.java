package gr.conference.usersys.test;

import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class RegistryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void setupAll() {
        // Δημιουργούμε το EntityManagerFactory για να συνδεθούμε με τη βάση δεδομένων
        emf = Persistence.createEntityManagerFactory("sys");
    }

    @BeforeEach
    public void setup() {
        // Δημιουργούμε το EntityManager πριν από κάθε τεστ
        em = emf.createEntityManager();
    }

    @AfterEach
    public void teardown() {
        // Κλείνουμε το EntityManager μετά από κάθε τεστ
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @AfterAll
    public static void teardownAll() {
        // Κλείνουμε το EntityManagerFactory μετά την ολοκλήρωση όλων των τεστ
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @ParameterizedTest
    @CsvSource({
        // Επιτυχημένες περιπτώσεις
        "testuser1, StrongP@ss1, StrongP@ss1, test1@example.com, 1234567890, true",
        
        "testuser2, StrongP@ss2, StrongP@ss2, test2@example.com, 0987654321, true",

        // Επιτυχία: ειδικοί χαρακτήρες στο username
        "user_special!@#, StrongP@ss7, StrongP@ss7, special@example.com, 2233445566, true",

        "user12345, StrongP@ss10, StrongP@ss10, test10@example.com, 7878787878, true",
        
        // Επιτυχία: Email με subdomain
        "testuser14, StrongP@ss14, StrongP@ss14, user@mail.example.com, 2323232323, true",


        // Επιτυχία: Password με όλα τα απαιτούμενα στοιχεία
        "testuser18, P@ssw0rdStrong, P@ssw0rdStrong, test18@example.com, 4567890123, true",

        // Επιτυχία: Username με κεφαλαία και μικρά
        "UserWithCase, StrongP@ss21, StrongP@ss21, test21@example.com, 7890123456, true",

        // Επιτυχία: Όλα έγκυρα
        "testuser26, Str0ng!P@ssword, Str0ng!P@ssword, test26@example.com, 3453453453, true",

        // Επιτυχία: Ειδικοί χαρακτήρες στο email
        "testuser28, StrongP@ss28, StrongP@ss28, special!email@example.com, 9990001111, true",


        // Επιτυχία: Μεγάλο τηλέφωνο με έγκυρο μήκος
        "testuser30, StrongP@ss30, StrongP@ss30, test30@example.com, 7776665552, true"
    })
    public void testRegisterUser(String username, String password, String password2, String email, String phone, boolean expected) {
        try {
            boolean result = UserDBHandler.registerUser(username.trim(), password, password2, email, phone);
            assertEquals(expected, result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
