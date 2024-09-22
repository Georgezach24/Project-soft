package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        ConferenceDBHandler.createConference("techsummit", "existingUser1", "A tech summit");
        ConferenceDBHandler.createConference("devconf", "existingUser1", "Developer conference");
    }

    @AfterAll
    public static void tearDownClass() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }

        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM Conference c WHERE c.name IN ('testconf', 'techsummit', 'devconf')").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();

        EntityManager deleteEntityManager2 = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager2.getTransaction().begin();
        deleteEntityManager2.createQuery("DELETE FROM User u WHERE u.username = 'existingUser1'").executeUpdate();
        deleteEntityManager2.getTransaction().commit();
        deleteEntityManager2.close();
    }

    @ParameterizedTest
    @CsvSource({
        "testconf, description1, testconf, description1",  // Exact match
        "techsummit, A tech summit, techsummit, A tech summit",  // Exact match
        "devconf, Developer conference, devconf, Developer conference",  // Exact match
        "invalid, description1, , ",  // No match
        ", Developer conference, devconf, Developer conference",  // Match by description
        "testconf, , testconf, description1",  // Match by name only
    })
    public void testSearchConferences(String searchName, String searchDescription, String expectedName, String expectedDescription) {
        List<Conference> result = ConferenceDBHandler.searchConferences(searchName, searchDescription);

        if (expectedName == null) {
            assertTrue(result.isEmpty(), "Expected no results, but found some.");
        } else {
            assertFalse(result.isEmpty(), "Expected results, but found none.");
            assertEquals(expectedName, result.get(0).getName(), "Conference name does not match.");
            assertEquals(expectedDescription, result.get(0).getDesc(), "Conference description does not match.");
        }
    }
}
