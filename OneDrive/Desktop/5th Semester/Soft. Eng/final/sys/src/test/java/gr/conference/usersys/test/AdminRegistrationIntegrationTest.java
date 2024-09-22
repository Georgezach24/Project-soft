package gr.conference.usersys.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AdminRegistrationIntegrationTest {

    private final String PERSISTENCE_UNIT_NAME = "sys";
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterAll
    public static void tearDown() {
        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM User u WHERE u.username = 'admin1'").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();

        if (emf != null) {
            emf.close();
        }
    }

    @ParameterizedTest
    @CsvSource({
        "admin1, ADMIN, true", 
        "user1, USER, false" // Example of a non-admin registration for testing
    })
    @DisplayName("Administrator Registration Parameterized Test")
    public void testIsAdminRegistered(String username, String role, boolean expected) {
        // Create a new user
        User user = new User();
        user.setUsername(username);
        user.setRole(role);
        user.setPassword("testpassword");
        em.persist(user);
        em.getTransaction().commit();

        // Check if the user is registered as an admin
        boolean isAdminRegistered = UserDBHandler.isAdminRegistered(username);

        // Assert based on the expected result
        if (expected) {
            assertTrue(isAdminRegistered, "Admin should be registered");
        } else {
            assertFalse(isAdminRegistered, "User should not be registered as an admin");
        }
    }
}
