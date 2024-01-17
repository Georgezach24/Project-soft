package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import gr.conference.confsys.ConferenceDBHandler;

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
        }
    }

    @Test
    public void testIsConferenceNameUnique() {
        // Test data
        String existingConferenceName = "existingConf";
        String newConferenceName = "newConf";

       
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
