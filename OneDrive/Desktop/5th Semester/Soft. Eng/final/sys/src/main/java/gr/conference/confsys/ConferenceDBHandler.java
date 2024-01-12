package gr.conference.confsys;

import java.util.Date;

import gr.conference.usersys.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


public class ConferenceDBHandler {
	
	public static EntityManagerFactory ENITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");
	
	public static boolean createConference(String conferenceName, String creatorUsername, String desc) {
	    EntityManager em = ENITY_MANAGER_FACTORY.createEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();

	        if (isConferenceNameUnique(em, conferenceName)) {
	     
	            User creator = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
	                    .setParameter("username", creatorUsername)
	                    .getSingleResult();

	            if (creator != null) {

	                creator.setRole("PC CHAIR");


	                Conference newConference = new Conference();
	                newConference.setName(conferenceName);
	                newConference.setDesc(desc);
	                newConference.setDate(new Date());
	                newConference.setId_creator(creator.getUserId());

	                em.persist(newConference);

	                et.commit();
	                return true;
	            } else {
	                System.out.println("Creator not found!");
	            }
	        } else {
	            System.out.println("Conference name is not unique!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (et.isActive()) {
	            et.rollback();
	        }
	    } finally {
	        em.close();
	    }

	    return false;
	}


    private static boolean isConferenceNameUnique(EntityManager em, String conferenceName) {
        String query = "SELECT COUNT(c) FROM Conference c WHERE c.name = :name";
        Long count = em.createQuery(query, Long.class)
                .setParameter("name", conferenceName)
                .getSingleResult();

        return count == 0;
    }
}
