package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.conference.confsys.Conference;
import gr.conference.confsys.ConferenceDBHandler;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class UpdateConferenceTestCase {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("sys");
        System.out.println("EntityManagerFactory created: " + entityManagerFactory);
        assertNotNull(entityManagerFactory);

        try {
            boolean test = ConferenceDBHandler.createConference("conference11", "user002", "descripto");
            assertTrue(test);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Conference was not created: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testUpdateConference() {
        // Test the updateConference method
        boolean result = ConferenceDBHandler.updateConference("conference11", "newconfuser", "descuserconf");

        // Check if the update was successful
        assertTrue(result);

        // Retrieve the updated conference from the handler
        Conference updatedConference = ConferenceDBHandler.getConferenceByName(
                entityManagerFactory.createEntityManager(), "newconfuser");

        // Check if the conference was actually updated
        assertNotNull(updatedConference);
        assertEquals("newconfuser", updatedConference.getName());
        assertEquals("descuserconf", updatedConference.getDesc());
    }
}
