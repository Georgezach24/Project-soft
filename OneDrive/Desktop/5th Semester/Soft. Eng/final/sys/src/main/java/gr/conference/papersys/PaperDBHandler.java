package gr.conference.papersys;

import java.util.Date;

import gr.conference.confsys.Conference;
import gr.conference.usersys.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class PaperDBHandler {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sys");

    public static boolean createPaper(String conferenceName, String creatorUsername, String title) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            if (isPaperTitleUnique(conferenceName, title, entityManager)) {
                Conference conference = getConferenceByName(conferenceName, entityManager);

                if (conference != null) {
                    User creator = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                            .setParameter("username", creatorUsername)
                            .getSingleResult();

                    if (creator != null) {
                        Paper newPaper = new Paper();
                        newPaper.setTitle(title);
                        newPaper.setAuthorNames(creator.getName());
                        newPaper.setContent(null);
                        newPaper.setCreationDate(new Date());
                        newPaper.setCreator(creator);  // Replace u_id with actual User reference
                        newPaper.setConference(conference);  // Replace conf_id with actual Conference reference
                        newPaper.setPaperState("CREATED");

                        entityManager.persist(newPaper);
                        transaction.commit();
                        return true;
                    } else {
                        System.out.println("Creator not found!");
                    }
                } else {
                    System.out.println("Conference not found!");
                }
            } else {
                System.out.println("Paper title is not unique within the conference!");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Rollback transaction on failure
            }
            e.printStackTrace();
        } finally {
            entityManager.close();  // Always close EntityManager
        }
        return false;
    }

    private static boolean isPaperTitleUnique(String conferenceName, String paperTitle, EntityManager entityManager) {
        Conference conference = getConferenceByName(conferenceName, entityManager);
        if (conference != null) {
            String query = "SELECT COUNT(p) FROM Paper p WHERE p.conference = :conference AND p.title = :paperTitle";
            Long count = entityManager.createQuery(query, Long.class)
                    .setParameter("conference", conference)
                    .setParameter("paperTitle", paperTitle)
                    .getSingleResult();

            return count == 0;
        }
        return false;
    }

    private static Conference getConferenceByName(String conferenceName, EntityManager entityManager) {
        String query = "SELECT c FROM Conference c WHERE c.name = :name";
        return entityManager.createQuery(query, Conference.class)
                .setParameter("name", conferenceName)
                .getSingleResult();
    }
}
