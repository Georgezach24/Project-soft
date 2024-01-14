package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import gr.conference.confsys.Conference;
import gr.conference.confsys.ConferenceDBHandler;
import gr.conference.usersys.UserDBHandler;

import java.util.List;

public class SearchConferenceTestCase {

    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("sys");
        UserDBHandler.registerUser("existingUser1", "StrongP@ss1", "StrongP@ss1", "test@example.com", "123456789");
        ConferenceDBHandler.createConference("testconf", "existingUser1", "description1");
    }

    @AfterAll
    public static void tearDownClass() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testSearchConferences() {
        
        String conferenceName = "testconf";
        String description = "description1";

        
        List<Conference> result = ConferenceDBHandler.searchConferences(conferenceName, description);

        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(conferenceName, result.get(0).getName());
        assertEquals(description, result.get(0).getDesc());
    }

   

}
