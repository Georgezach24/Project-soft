package gr.conference.usersys.test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;


public class AdminRegistrationIntegrationTest {

    private static final String PERSISTENCE_UNIT_NAME = "sys"; // Provide your persistence unit name
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        // Insert an admin user into the database for testing
        User adminUser = new User();
        adminUser.setUsername("admxzincsdx");
        adminUser.setRole("ADMIN");
        adminUser.setPassword("admin");
        em.persist(adminUser);

        em.getTransaction().commit();
    }

    @AfterEach
    public void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    @DisplayName("Administrator registry")
    public void testIsAdminRegistered() {
        boolean isAdminRegistered = UserDBHandler.isAdminRegistered(em);

        assertTrue(isAdminRegistered, "Admin should be registered");
    }
}
