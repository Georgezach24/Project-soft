package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManagerFactory;

class PasswordResetTestCase {

    private EntityManager em;
    private EntityTransaction et;
    private EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");

    @BeforeEach
    public void setUp() {
        // Initialize EntityManager and EntityTransaction before each test
        em = ENTITY_MANAGER_FACTORY.createEntityManager();
        et = em.getTransaction();
        
        UserDBHandler.registerUser("User008", "User00812!", "User00812!", "test@example.com", "123456789");
        UserDBHandler.registerUser("User009", "User00912!!", "User00912!!", "test@example.com", "123456789");
    }

    @AfterEach
    public void tearDown() {
        // Clean up resources after each test
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    
    @Test
    public void testUpdatePassword() {
        // Assuming you have a user in the database with known username and password
        String username = "User008";
        String oldPassword = "User00812!";
        String newPassword = "User00712!";

        // Call the updatePassword method and log the result
        boolean result = UserDBHandler.updatePassword(username, oldPassword, newPassword);
        System.out.println("Update Password Result: " + result);

        // Assert that the update was successful
        assertTrue(result);

        // Verify that the password has been updated in the database
        User updatedUser = getUserByUsername(username);
        assertNotNull(updatedUser);
        assertEquals(newPassword, updatedUser.getPassword());
    }

    private User getUserByUsername(String username) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
        getUserQuery.setParameter("username", username);

        try {
            return getUserQuery.getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no user is found
            return null;
        } finally {
            em.close();
        }
    }



    
    @Test
    public void testUpdatePasswordInvalidNewPassword() {
        // Test when the new password is invalid
        String username = "User009";
        String oldPassword = "User00912!!";
        String newPassword1 = "invalid_password";  // Assuming this is an invalid password

        // Call the updatePassword method and assert the result
        boolean result = UserDBHandler.updatePassword(username, oldPassword, newPassword1);
        System.out.println("Update Password Result: " + result);

        // Assert that the update failed
        assertFalse(result);

        // Verify that the password remains unchanged in the database
     // Verify that the password remains unchanged in the database
        User unchangedUser = getUserByUsername(username);
        assertNotNull(unchangedUser);
        assertEquals(oldPassword, unchangedUser.getPassword());

    }

}
