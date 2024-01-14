package gr.conference.confsys.test;

import static org.junit.Assert.*;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.conference.confsys.ConferenceDBHandler;

public class CreateConferenceTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("sys");
    }

    @After
    public void tearDown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testCreateConference() {
        // Test data
        String conferenceName = "Test Conference";
        String creatorUsername = "testuser";
        String desc = "Test conference description";

        // Perform the test
        boolean result = ConferenceDBHandler.createConference(conferenceName, creatorUsername, desc);

        // Assert the result
        assertTrue(result);
    }
}
