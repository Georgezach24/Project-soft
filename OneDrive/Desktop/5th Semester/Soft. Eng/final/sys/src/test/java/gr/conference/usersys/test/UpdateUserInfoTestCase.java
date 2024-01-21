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

class UpdateUserInfoTestCase {

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;

    @BeforeAll
    public static void setUp() {
        ENTITY_MANAGER_FACTORY = UserDBHandler.ENTITY_MANAGER_FACTORY;
    }
    
    @AfterAll
    public static void deleteTestData() {
        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM User u WHERE u.username = 'userinfo'").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();
    }

    @Test
    public void testUpdateUserInfo() {
        String usernameToUpdate = "userinfo";
        String newName = "NewName";
        String newSurname = "NewSurname";
        String newEmail = "newemail@example.com";
        String newPhone = "987654321";

        UserDBHandler.registerUser(usernameToUpdate, "User02!@", "User02!@", "test@example.com", "123456789");

        boolean result = UserDBHandler.updateUserInfo(usernameToUpdate, usernameToUpdate, newName, newSurname, newEmail, newPhone);

        assertTrue(result);

        User updatedUser = getUserByUsername(usernameToUpdate);
        assertNotNull(updatedUser);
        assertEquals(newName, updatedUser.getName());
        assertEquals(newSurname, updatedUser.getSurname());
        assertEquals(newEmail, updatedUser.getEmail());
        assertEquals(newPhone, updatedUser.getPhone());
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
            em.close();
        }
    }
}
