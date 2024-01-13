package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.conference.usersys.UserDBHandler;

class LoginUserTestcase {

    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        // Use an in-memory database for testing
        entityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        UserDBHandler.ENITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");
        entityManager.getTransaction().begin();
        UserDBHandler.registerAdmin(); // Ensure admin is registered
        UserDBHandler.registerUser("User02", "User02@!", "User02@!", "test@example.com", "123456789");
    }

    @AfterEach
    public void tearDown() {
        // Rollback any changes made during the test and close the EntityManager
        entityManager.getTransaction().rollback();
        entityManager.close();
    }

    @Test
    public void testLoginUserSuccess() {
        // Prepare test data in the in-memory database
        

        // Call the loginUser method
        boolean result = UserDBHandler.loginUser("User02", "User02@!");

        // Verify that the method returns true
        assertTrue(result);
    }

    @Test
    public void testLoginUserFailure() {
        // Call the loginUser method with non-existent user credentials
        boolean result = UserDBHandler.loginUser("nonExistentUser", "wrongPassword");

        // Verify that the method returns false
        assertFalse(result);
    }
}
