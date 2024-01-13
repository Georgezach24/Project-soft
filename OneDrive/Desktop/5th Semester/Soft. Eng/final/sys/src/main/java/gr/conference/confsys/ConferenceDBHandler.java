package gr.conference.confsys;

import java.util.Date;
import java.util.List;

import gr.conference.usersys.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;


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
	
	public static List<Conference> searchConferences(String conferenceName, String description) {
        EntityManager em = ENITY_MANAGER_FACTORY.createEntityManager();

        try {
            StringBuilder jpqlBuilder = new StringBuilder("SELECT c FROM Conference c WHERE 1=1");

            if (conferenceName != null && !conferenceName.isBlank()) {
                jpqlBuilder.append(" AND LOWER(c.name) LIKE :conferenceName");
            }

            if (description != null && !description.isBlank()) {
                jpqlBuilder.append(" AND LOWER(c.desc) LIKE :description");
            }

            jpqlBuilder.append(" ORDER BY c.name");

            TypedQuery<Conference> query = em.createQuery(jpqlBuilder.toString(), Conference.class);

            if (conferenceName != null && !conferenceName.isBlank()) {
                query.setParameter("conferenceName", "%" + conferenceName.toLowerCase() + "%");
            }

            if (description != null && !description.isBlank()) {
                query.setParameter("description", "%" + description.toLowerCase() + "%");
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }
	
	public static boolean updateConference(String oldConferenceName, String newName, String newDescription) 
	{
		EntityManager em = ENITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		try {
		et.begin();
		
		Conference conferenceToUpdate = getConferenceByName(em, oldConferenceName);
		
		if (conferenceToUpdate != null) {
			if (newName != null && !newName.isBlank()) {
			conferenceToUpdate.setName(newName);
			}
			
			if (newDescription != null) {
			conferenceToUpdate.setDesc(newDescription);
			}
			
			em.merge(conferenceToUpdate);
			et.commit();
			return true;
		} 
		else {
			System.out.println("Conference not found!");
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
		
	private static Conference getConferenceByName(EntityManager em, String conferenceName) {
		String query = "SELECT c FROM Conference c WHERE c.name = :name";
		TypedQuery<Conference> conferenceQuery = em.createQuery(query, Conference.class);
		conferenceQuery.setParameter("name", conferenceName);
		
		try {
			return conferenceQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

    private static boolean isConferenceNameUnique(EntityManager em, String conferenceName) {
        String query = "SELECT COUNT(c) FROM Conference c WHERE c.name = :name";
        Long count = em.createQuery(query, Long.class)
                .setParameter("name", conferenceName)
                .getSingleResult();

        return count == 0;
    }
}
