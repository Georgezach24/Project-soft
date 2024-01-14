package gr.conference.confsys;

import java.util.Date;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.conference.usersys.User;

public class ConferenceDBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceDBHandler.class);
    public static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");

    public static boolean createConference(String conferenceName, String creatorUsername, String desc) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
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
                    LOGGER.error("Creator not found!");
                }
            } else {
                LOGGER.error("Conference name is not unique!");
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred during createConference", e);
            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
        }

        return false;
    }

    public static List<Conference> searchConferences(String conferenceName, String description) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

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

    private static boolean isConferenceNameUnique(EntityManager em, String conferenceName) {
        String query = "SELECT COUNT(c) FROM Conference c WHERE c.name = :name";
        Long count = em.createQuery(query, Long.class)
                .setParameter("name", conferenceName)
                .getSingleResult();

        return count == 0;
    }
}
