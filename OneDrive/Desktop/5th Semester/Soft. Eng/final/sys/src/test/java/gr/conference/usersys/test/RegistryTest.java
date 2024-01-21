package gr.conference.usersys.test;

import org.junit.jupiter.api.Test;

import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;

public class RegistryTest {

    @Test
    public void testRegisterUser() {
        try {
            
            UserDBHandler.registerUser("existingUser", "StrongP@ss1", "StrongP@ss1", "test@example.com", "123456789");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }
    @AfterAll
    public static void deleteTestData() {
        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM User u WHERE u.username = 'existingUser'").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();
    }
    
    
}