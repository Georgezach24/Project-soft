package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import gr.conference.confsys.ConferenceDBHandler;
import gr.conference.usersys.UserDBHandler;

public class IsConferenceNameUniqueTestCase {

    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("sys");
    }

    @AfterAll
    public static void tearDownClass() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            
            EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
            deleteEntityManager.getTransaction().begin();
            deleteEntityManager.createQuery("DELETE FROM Conference c WHERE c.name = 'existingConf'").executeUpdate();
            deleteEntityManager.getTransaction().commit();
            deleteEntityManager.close();

            EntityManager deleteEntityManager2 = Persistence.createEntityManagerFactory("sys").createEntityManager();
            deleteEntityManager2.getTransaction().begin();
            deleteEntityManager2.createQuery("DELETE FROM User u WHERE u.username = 'existingUser1'").executeUpdate();
            deleteEntityManager2.getTransaction().commit();
            deleteEntityManager2.close();
        }
    }

    @Test
    public void testIsConferenceNameUnique() {
        // Test data
        String existingConferenceName = "existingConf";
        String newConferenceName = "newConf";

        UserDBHandler.registerUser("existingUser1", "User02!@", "User02!@", "test@ex.com", "1234324345");
        createTestConference(existingConferenceName, "existingDesc");

       
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            boolean resultExisting = ConferenceDBHandler.isConferenceNameUnique(em, existingConferenceName);

            
            assertFalse(resultExisting);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }

        
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            boolean resultNew = ConferenceDBHandler.isConferenceNameUnique(em, newConferenceName);

           
            assertTrue(resultNew);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    private void createTestConference(String conferenceName, String description) {
        
        ConferenceDBHandler.createConference(conferenceName, "existingUser1", description);
    }

    

}
