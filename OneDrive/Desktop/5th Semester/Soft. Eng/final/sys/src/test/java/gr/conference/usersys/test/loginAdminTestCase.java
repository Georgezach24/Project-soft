package gr.conference.usersys.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;

import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

class loginAdminTestCase {
	
		private static final String PERSISTENCE_UNIT_NAME = "sys"; 
	    private static EntityManagerFactory emf;
	    private static EntityManager em;
    
	    @BeforeAll
	    public static void setUp() {
        
    	emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setRole("ADMIN");
        adminUser.setPassword("admin");
        em.persist(adminUser);

        em.getTransaction().commit();
        
    }
	    
	    @AfterAll
	    public static void deleteTestData() {
	        EntityManager deleteEntityManager = Persistence.createEntityManagerFactory("sys").createEntityManager();
	        deleteEntityManager.getTransaction().begin();
	        deleteEntityManager.createQuery("DELETE FROM User u WHERE u.username = 'admin'").executeUpdate();
	        deleteEntityManager.getTransaction().commit();
	        deleteEntityManager.close();
	    }
    @Test
    public void testLoginAdminWithValidCredentials() {
        String username = "admin";
        String password = "admin";

        boolean result = UserDBHandler.loginAdmin(username, password);

        assertTrue(result);
    }

    @Test
    public void testLoginAdminWithInvalidCredentials() {
        String username = "invalidAdmin";
        String password = "invalidPassword";

        boolean result = UserDBHandler.loginAdmin(username, password);

        assertFalse(result);
    }
}
