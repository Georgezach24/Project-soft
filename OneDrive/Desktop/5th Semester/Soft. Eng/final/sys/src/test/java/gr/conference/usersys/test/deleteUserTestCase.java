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

public class deleteUserTestCase {

    private static EntityManagerFactory emf;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("sys");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Διαγραφή των χρηστών αν υπάρχουν ήδη στη βάση δεδομένων
        em.createQuery("DELETE FROM User u WHERE u.username IN ('userToDelete', 'adminToDelete')").executeUpdate();

        // Εισαγωγή δεδομένων για τη δοκιμή
        User user1 = new User();
        user1.setUsername("userToDelete");
        user1.setPassword("password1");
        user1.setRole("USER");

        User user2 = new User();
        user2.setUsername("adminToDelete");
        user2.setPassword("password2");
        user2.setRole("ADMIN");

        em.persist(user1);
        em.persist(user2);
        em.getTransaction().commit();
        em.close();
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Διαγραφή των χρηστών μετά από κάθε δοκιμή
        em.createQuery("DELETE FROM User u WHERE u.username IN ('userToDelete', 'adminToDelete')").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @ParameterizedTest
    @CsvSource({
        "userToDelete, true",    // Existing user, should be deleted
        "nonExistingUser, false",  // Non-existing user, should return false
        "adminToDelete, true",    // Existing admin, should be deleted
        "invalidUser, false"    // Another non-existing user
    })
    public void testDeleteUser(String username, boolean expectedResult) {
        // Test the deleteUser method with different usernames
        boolean result = UserDBHandler.deleteUser(username);
        assertEquals(expectedResult, result);
    }
}
