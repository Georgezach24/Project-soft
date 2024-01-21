package gr.conference.usersys.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminRegistrationIntegrationTest {

    private final String PERSISTENCE_UNIT_NAME = "sys";
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        User adminUser = new User();
        adminUser.setUsername("admin1");
        adminUser.setRole("ADMIN");
        adminUser.setPassword("admin1");
        em.persist(adminUser);

        em.getTransaction().commit();
    }

    
    @AfterAll
    public static void tearDown() {
    	EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
        deleteEntityManager.getTransaction().begin();
        deleteEntityManager.createQuery("DELETE FROM User u WHERE u.username = 'admin1'").executeUpdate();
        deleteEntityManager.getTransaction().commit();
        deleteEntityManager.close();
    	
    	
    	if (emf != null) {
            emf.close();
        }
    }

    @Test
    @DisplayName("Administrator registry")
    public void testIsAdminRegistered() {
        boolean isAdminRegistered = UserDBHandler.isAdminRegistered("admin1");

        assertTrue(isAdminRegistered, "Admin should be registered");
    }
}
