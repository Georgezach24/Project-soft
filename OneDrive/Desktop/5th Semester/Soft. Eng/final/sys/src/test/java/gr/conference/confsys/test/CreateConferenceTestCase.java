package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gr.conference.confsys.ConferenceDBHandler;
import gr.conference.usersys.UserDBHandler;

public class CreateConferenceTestCase {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("sys");
        System.out.println("EntityManagerFactory created: " + entityManagerFactory);
        assertNotNull(entityManagerFactory);
        UserDBHandler.registerUser("Userconf", "User00913!!", "User00913!!", "test1@example.com", "123456787");
    }

    @AfterEach
    public void tearDown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testCreateConference() {
        // Test data
        String conferenceName = "Test Conference1";
        String creatorUsername = "Userconf";
        String desc = "Test conference descriptiosn";

        // Perform the test
        try {
            boolean result = ConferenceDBHandler.createConference(conferenceName, creatorUsername, desc);
            // Assert the result
            assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}
