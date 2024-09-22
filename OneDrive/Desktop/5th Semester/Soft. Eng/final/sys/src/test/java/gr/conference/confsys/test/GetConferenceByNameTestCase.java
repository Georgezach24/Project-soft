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

        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM Conference c WHERE c.name IN ('testconf1', 'testconf2', 'testconf3', 'conf1234', 'conf_special')").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();

        EntityManager deleteEntityManager2 = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager2.getTransaction().begin();
        deleteEntityManager2.createQuery("DELETE FROM User u WHERE u.username IN ('existingUser1', 'existingUser2', 'user1234', 'specialuser')").executeUpdate();
        deleteEntityManager2.getTransaction().commit();
        deleteEntityManager2.close();
    }

    @ParameterizedTest
    @CsvSource({
        "testconf1, description1, existingUser1",
        "testconf2, description2, existingUser2",
        "testconf3, thirdconference, user1234",
        "conf1234, conference1234, user1234",
        "conf_special, specialconference, specialuser"
    })
    public void testGetConferenceByName(String conferenceName, String description, String username) {
       
        UserDBHandler.registerUser(username, "User02!@", "User02!@", username + "@ex.com", "1234324345");
        createTestConference(conferenceName, description, username);

        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            Conference result = ConferenceDBHandler.getConferenceByName(em, conferenceName);

            assertNotNull(result);
            assertEquals(conferenceName, result.getName());
            assertEquals(description, result.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    private void createTestConference(String conferenceName, String description, String username) {
        ConferenceDBHandler.createConference(conferenceName, username, description);
    }
}
