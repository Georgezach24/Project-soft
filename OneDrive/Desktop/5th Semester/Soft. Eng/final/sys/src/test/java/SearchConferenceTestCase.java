import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gr.conference.confsys.Conference;
import gr.conference.confsys.ConferenceDBHandler;

import java.util.List;

public class SearchConferenceTestCase {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("sys");
    }

    @AfterEach
    public void tearDown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testSearchConferences() {
        // Test data
        String conferenceName = "Test Conference";
        String description = "Test conference description";

        // Create a test conference
        createTestConference(conferenceName, description);

        // Perform the test
        List<Conference> result = ConferenceDBHandler.searchConferences(conferenceName, description);

        // Assert the result
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(conferenceName, result.get(0).getName());
        assertEquals(description, result.get(0).getDesc());
    }

    private void createTestConference(String conferenceName, String description) {
        // Create a test conference for the search
        ConferenceDBHandler.createConference(conferenceName, "testuser", description);
    }
}
