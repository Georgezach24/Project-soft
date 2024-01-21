package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
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
        UserDBHandler.registerUser("User0002", "User02@!", "User02@!", "test@example.com", "123456789");
    }

    @AfterEach
    public void tearDown() {
        entityManager.getTransaction().rollback();
    }

    @AfterAll
    public static void deleteTestData() {
        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM User u WHERE u.username = 'User0002'").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();
    }

    @Test
    public void testLoginUserSuccess() {
        boolean result = UserDBHandler.loginUser("User0002", "User02@!");
        assertTrue(result, "Login for user 'User02d' with correct password should succeed, but it failed.");
    }



    @Test
    public void testLoginUserFailure() {
        boolean result = UserDBHandler.loginUser("nonExistentUser", "wrongPassword");
        assertFalse(result);
    }
}
