package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import gr.conference.confsys.ConferenceDBHandler;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class UpdateConferenceTestCase {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("sys");
        assertNotNull(entityManagerFactory);
        
        UserDBHandler.registerUser("Userconfe", "User02!@", "User02!@", "test@ex.com", "124324235");

        try {
            boolean conferenceCreated = ConferenceDBHandler.createConference("conference1", "Userconfe", "descripto");
            assertTrue(conferenceCreated, "Conference should be created successfully.");
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

    @ParameterizedTest
    @CsvSource({
        "conference1, NewConferenceName1, NewDescription1, true",  // Ενημέρωση με έγκυρα δεδομένα
        "conference1, NewConferenceName2, , true",                // Ενημέρωση μόνο ονόματος
        "conference1, , NewDescription2, true",                   // Ενημέρωση μόνο περιγραφής
    })
    public void testUpdateConference(String oldConferenceName, String newName, String newDescription, boolean expectedResult) {
        boolean result = ConferenceDBHandler.updateConference(oldConferenceName, newName, newDescription);
        
        if (expectedResult) {
            assertTrue(result, "Conference should be updated successfully.");
        } else {
            assertFalse(result, "Conference update should fail for invalid inputs.");
        }
    }

    @AfterAll
    public static void deleteTestData() {
        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM Conference c WHERE c.name LIKE 'NewConferenceName%' OR c.name = 'conference1'").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();

        EntityManager deleteEntityManager2 = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager2.getTransaction().begin();
        deleteEntityManager2.createQuery("DELETE FROM User u WHERE u.username = 'Userconfe'").executeUpdate();
        deleteEntityManager2.getTransaction().commit();
        deleteEntityManager2.close();
    }

}
