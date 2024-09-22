package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import gr.conference.confsys.ConferenceDBHandler;
import gr.conference.usersys.UserDBHandler;

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

            EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
            deleteEntityManager.getTransaction().begin();
            deleteEntityManager.createQuery("DELETE FROM Conference c WHERE c.name IN ('existingConf', 'existingConf2', 'testConf123')").executeUpdate();
            deleteEntityManager.getTransaction().commit();
            deleteEntityManager.close();

            EntityManager deleteEntityManager2 = Persistence.createEntityManagerFactory("sys").createEntityManager();
            deleteEntityManager2.getTransaction().begin();
            deleteEntityManager2.createQuery("DELETE FROM User u WHERE u.username IN ('existingUser1', 'existingUser2', 'userTest')").executeUpdate();
            deleteEntityManager2.getTransaction().commit();
            deleteEntityManager2.close();
        }
    }

    @ParameterizedTest
    @CsvSource({
        "existingConf, existingDesc, false",
        "existingConf2, anotherDesc, false",
        "testConf123, testDesc, false"
    })
    public void testIsConferenceNameUnique(String conferenceName, String description, boolean expectedResult) {
        
        UserDBHandler.registerUser("existingUser1", "User02!@", "User02!@", "test@ex.com", "1234324345");
        createTestConference(conferenceName, description);

        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            boolean result = ConferenceDBHandler.isConferenceNameUnique(em, conferenceName);
            assertEquals(expectedResult, result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    private void createTestConference(String conferenceName, String description) {
        ConferenceDBHandler.createConference(conferenceName, "existingUser1", description);
    }
}
