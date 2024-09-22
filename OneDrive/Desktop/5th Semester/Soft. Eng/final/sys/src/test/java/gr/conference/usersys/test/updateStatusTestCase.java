package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class updateStatusTestCase {
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        // Δημιουργούμε ένα νέο EntityManagerFactory και EntityManager πριν από κάθε τεστ
        emf = Persistence.createEntityManagerFactory("sys");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterEach
    public void teardown() {
        // Έλεγχος αν υπάρχει ενεργή συναλλαγή πριν ξεκινήσουμε νέα
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        try {
            // Διαγραφή των χρηστών που δημιουργήθηκαν για τα τεστ
            em.createQuery("DELETE FROM User u WHERE u.username = 'userToUpdate1'").executeUpdate();
            em.createQuery("DELETE FROM User u WHERE u.username = 'userToUpdate2'").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Κλείνουμε το EntityManager και καθαρίζουμε τους πόρους μετά από κάθε τεστ
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }

    // Παραμετροποιημένο τεστ για την ενημέρωση της κατάστασης χρήστη
    @ParameterizedTest
    @CsvSource({
        "userToUpdate1, Active, Inactive, true",    // Επιτυχία: ο χρήστης ενημερώνεται με επιτυχία
        "userToUpdate2, Inactive, Active, true",    // Επιτυχία: ο χρήστης ενημερώνεται ξανά
        "nonExistingUser, Active, Inactive, false"  // Αποτυχία: ανύπαρκτος χρήστης
    })
    public void testUpdateStatus(String username, String initialStatus, String newStatus, boolean expectedResult) {
        // Εγγραφή χρήστη μόνο αν δεν είναι ανύπαρκτος
        if (!username.equals("nonExistingUser")) {
            UserDBHandler.registerUser(username, "User02!@", "User02!@", "test@example.com", "123456789");
            UserDBHandler.updateStatus(username, initialStatus);
            em.flush();  // Διασφάλιση ότι ο χρήστης έχει αποθηκευτεί στη βάση
            em.clear();  // Καθαρισμός του persistence context
        }

        // Ενημερώνουμε την κατάσταση του χρήστη
        boolean result = UserDBHandler.updateStatus(username, newStatus);

        // Ελέγχουμε αν το αποτέλεσμα ήταν το αναμενόμενο
        assertEquals(expectedResult, result);

        if (expectedResult) {
            // Ελέγχουμε τη νέα κατάσταση του χρήστη μετά την ενημέρωση
            User updatedUser = getUserByUsername(username);
            assertNotNull(updatedUser);
            assertEquals(newStatus, updatedUser.getUser_status());
        }
    }

    // Βοηθητική μέθοδος για να βρούμε χρήστη από το όνομα χρήστη
    private User getUserByUsername(String username) {
        try {
            String query = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
            getUserQuery.setParameter("username", username);
            return getUserQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No user found with username: " + username);
            return null;
        }
    }
}
