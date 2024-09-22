package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.conference.confsys.ConferenceDBHandler;
import gr.conference.usersys.UserDBHandler;

class CreateConferenceTestCase {

    private static EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        ConferenceDBHandler.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");
        entityManager.getTransaction().begin();

        // Ensure the creator user exists for testing
        UserDBHandler.registerUser("Userconf", "User00913!!", "User00913!!", "test1@example.com", "123456787");
    }

    @AfterEach
    public void tearDown() {
        // Rollback to avoid conflicts in subsequent tests
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        // Clean up created conferences after each test
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Conference c WHERE c.name LIKE 'TechConf%'").executeUpdate();
        entityManager.getTransaction().commit();

        // Clean up the test user
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM User u WHERE u.username = 'Userconf'").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    @ParameterizedTest
    @CsvSource({
        "TechConf2024, Userconf, A tech conference about innovation, true",   // Valid conference
        "TechConf2025, Userconf, Another tech summit, true",                  // Valid conference
    })
    public void testCreateConference(String conferenceName, String creatorUsername, String description, boolean expectedResult) {
        try {
            // Log the conference details for debugging purposes
            System.out.println("Attempting to create conference: " + conferenceName);

            boolean result = ConferenceDBHandler.createConference(conferenceName, creatorUsername, description);

            if (expectedResult) {
                assertTrue(result, "Conference should be created successfully.");
            } else {
                assertFalse(result, "Conference creation should fail for invalid inputs.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    public void testCreateConferenceWithNullInputs() {
        try {
            boolean result = ConferenceDBHandler.createConference(null, null, null);
            assertFalse(result, "Conference creation should fail when name, username, and description are null.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}
