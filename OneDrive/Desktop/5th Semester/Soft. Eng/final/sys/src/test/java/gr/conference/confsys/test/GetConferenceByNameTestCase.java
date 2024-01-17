package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import gr.conference.confsys.Conference;
import gr.conference.confsys.ConferenceDBHandler;

public class GetConferenceByNameTestCase {

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
    public void testGetConferenceByName() {
 
        String conferenceName = "testconf";
        String description = "description1";

       
        createTestConference(conferenceName, description);

        
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            Conference result = ConferenceDBHandler.getConferenceByName(em, conferenceName);

            // Assert the result
            assertNotNull(result);
            assertEquals(conferenceName, result.getName());
            assertEquals(description, result.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    private void createTestConference(String conferenceName, String description) {
        
        ConferenceDBHandler.createConference(conferenceName, "existingUser1", description);
    }

    

}
