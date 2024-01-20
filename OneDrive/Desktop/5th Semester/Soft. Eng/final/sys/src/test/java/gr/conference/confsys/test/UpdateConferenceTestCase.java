package gr.conference.confsys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.conference.confsys.ConferenceDBHandler;
import jakarta.persistence.EntityManager;
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
            boolean test = ConferenceDBHandler.createConference("conference1", "Userconf", "descripto");
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
        
        boolean result = ConferenceDBHandler.updateConference("conference1", "Userconf", "descuserconf");
 
        assertTrue(result);

    }
    
    
    @AfterAll
    public static void deleteTestData() {
        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM Conference c WHERE c.name = 'Userconf'").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();

        EntityManager deleteEntityManager2 = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager2.getTransaction().begin();
        deleteEntityManager2.createQuery("DELETE FROM User u WHERE u.username = 'Userconf'").executeUpdate();
        deleteEntityManager2.getTransaction().commit();
        deleteEntityManager2.close();
    }

}
