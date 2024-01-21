package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

class updateStatusTestCase {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;

    @BeforeAll
    public static void setUp() {
        ENTITY_MANAGER_FACTORY = UserDBHandler.ENTITY_MANAGER_FACTORY;
    }
    
    @AfterAll
    public static void deleteTestData() {
        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM User u WHERE u.username = 'usertoUpdate'").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();
    }

    @Test
    public void testUpdateStatus() {
        String usernameToUpdate = "userToUpdate";
        String initialStatus = "Active";
        String newStatus = "Inactive";

        UserDBHandler.registerUser(usernameToUpdate, "User02!@", "User02!@", "test@example.com", "123456789");
        UserDBHandler.updateStatus(usernameToUpdate, initialStatus);

        User userBeforeUpdate = getUserByUsername(usernameToUpdate);
        assertNotNull(userBeforeUpdate);
        assertEquals(initialStatus, userBeforeUpdate.getUser_status());

        boolean result = UserDBHandler.updateStatus(usernameToUpdate, newStatus);

        assertTrue(result);

        User userAfterUpdate = getUserByUsername(usernameToUpdate);
        assertNotNull(userAfterUpdate);
        assertEquals(newStatus, userAfterUpdate.getUser_status());
    }

    @Test
    public void testUpdateStatusForNonExistingUser() {
        String nonExistingUsername = "nonExistingUser";
        String newStatus = "Inactive";

        boolean result = UserDBHandler.updateStatus(nonExistingUsername, newStatus);

        assertFalse(result);
    }

    private User getUserByUsername(String username) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            String query = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
            getUserQuery.setParameter("username", username);

            return getUserQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
}
