package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

class UpdateUserInfoTestCase {

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        ENTITY_MANAGER_FACTORY = UserDBHandler.ENTITY_MANAGER_FACTORY;
        em = ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM User u WHERE u.username LIKE 'userinfo%'").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @ParameterizedTest
    @CsvSource({
        "userinfo1, NewName1, NewSurname1, newemail1@example.com, 9876543211, true",
        "userinfo2, NewName2, NewSurname2, test2@example.com, 1234567892, true",
        "userinfo3, CurrentName3, CurrentSurname3, newemail3@example.com, 9876543213, true",
        "userinfo4, CurrentName4, CurrentSurname4, test4@example.com, 1234567894, true"
    })
    public void testUpdateUserInfo(String usernameToUpdate, String newName, String newSurname, String newEmail, String newPhone, boolean expectedResult) {
        UserDBHandler.registerUser(usernameToUpdate, "User02!@", "User02!@", "test@example.com", "123456789");

        boolean result = UserDBHandler.updateUserInfo(usernameToUpdate, usernameToUpdate, newName, newSurname, newEmail, newPhone);

        assertEquals(expectedResult, result);

        User updatedUser = getUserByUsername(usernameToUpdate);
        assertNotNull(updatedUser);

        assertEquals(newName, updatedUser.getName());
        assertEquals(newSurname, updatedUser.getSurname());
        assertEquals(newEmail, updatedUser.getEmail());
        assertEquals(newPhone, updatedUser.getPhone());
    }

    private User getUserByUsername(String username) {
        try {
            String query = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
            getUserQuery.setParameter("username", username);

            return getUserQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
