package gr.conference.papersys;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Date;

import gr.conference.usersys.User;
import gr.conference.confsys.*;

public class PaperDBHandler {

    public static EntityManager ENTITY_MANAGER = Persistence.createEntityManagerFactory("sys").createEntityManager();

    public static boolean createPaper(String conferenceName, String creatorUsername, String title) {
        EntityTransaction transaction = ENTITY_MANAGER.getTransaction();

        try {
            transaction.begin();

            if (isPaperTitleUnique(conferenceName, title)) {
                Conference conference = getConferenceByName(conferenceName);

                if (conference != null) {
                	User creator = ENTITY_MANAGER.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                		    .setParameter("username", creatorUsername)
                		    .getSingleResult();

                    if (creator != null) {
                        Paper newPaper = new Paper();
                        newPaper.setTitle(title);
                        newPaper.setAuthorNames(creator.getName());
                        newPaper.setContent(null);
                        newPaper.setCreationDate(new Date());
                        newPaper.setU_id(creator.getUserId());
                        newPaper.setConf_id(conference.getId());
                        newPaper.setPaperState("CREATED");

                        creator.setRole("AUTHOR");

                        ENTITY_MANAGER.persist(newPaper);
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
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            ENTITY_MANAGER.close();
        }

        return false;
    }

    private static boolean isPaperTitleUnique(String conferenceName, String paperTitle) {
        Conference conference = getConferenceByName(conferenceName);

        if (conference != null) {
            String query = "SELECT COUNT(p) FROM Paper p WHERE p.conf_id = :conf_id AND p.title = :paperTitle";
            Long count = ENTITY_MANAGER.createQuery(query, Long.class)
                    .setParameter("conf_id", conference.getId()) 
                    .setParameter("paperTitle", paperTitle)
                    .getSingleResult();

            return count == 0;
        }

        return false;
    }

    private static Conference getConferenceByName(String conferenceName) {
        String query = "SELECT c FROM Conference c WHERE c.name = :name";
        return ENTITY_MANAGER.createQuery(query, Conference.class)
                .setParameter("name", conferenceName)
                .getSingleResult();
    }
}
