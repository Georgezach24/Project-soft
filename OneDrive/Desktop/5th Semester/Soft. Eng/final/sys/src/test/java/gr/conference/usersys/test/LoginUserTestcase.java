package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.conference.usersys.UserDBHandler;

class LoginUserTestcase {

    private static EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        UserDBHandler.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");
        entityManager.getTransaction().begin();
        
        UserDBHandler.registerUser("User0002", "User02@!", "User02@!", "test@example.com", "123456789");  // Regular user
        UserDBHandler.registerAdmin(); // Register the admin with "ADMIN" role
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.getTransaction().begin();

        entityManager.createQuery("DELETE FROM User u WHERE u.username = 'User0002'").executeUpdate();
        entityManager.createQuery("DELETE FROM User u WHERE u.username = 'admin'").executeUpdate();

        entityManager.getTransaction().commit();

        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @ParameterizedTest
    @CsvSource({
        "admin, admin, ADMIN, true",  // Admin credentials
        "User0002, User02@!, USER, true",  // Valid regular user credentials
        "User0002, WrongPass@1, USER, false",  // Invalid password
        "NonExistentUser, User02@!, USER, false",  // Invalid username
        "'', '', USER, false",  // Empty username and password
    })
    public void testLogin(String username, String password, String expectedRole, boolean expectedResult) {
        String result = UserDBHandler.loginUser(username, password);
        
        if (expectedResult) {
            assertNotNull(result, "User should be able to login with correct credentials.");
            assertEquals(expectedRole, result, "The user role should match the expected role.");
        } else {
            assertNull(result, "Login should fail for incorrect credentials.");
        }
    }

    @Test
    public void testLoginWithNullInputs() {
        String result = UserDBHandler.loginUser(null, null);
        assertNull(result, "Login should fail when username and password are null.");
    }
}
