package gr.conference.papersys;

import gr.conference.usersys.User;

import jakarta.persistence.*;
import java.util.List;

public class PaperDBHandler {

    private EntityManagerFactory emf;

    public PaperDBHandler() {
        this.emf = Persistence.createEntityManagerFactory("conference_persistence");
    }

    public ResponseMessage createPaper(Paper paper) {
        ResponseMessage response = new ResponseMessage();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Paper p WHERE p.title = :title", Long.class);
            query.setParameter("title", paper.getTitle());
            Long count = query.getSingleResult();
            if (count > 0) {
                response.setMessage("Paper with this title already exists.");
                response.setSuccess(false);
                return response;
            }

            em.persist(paper);
            em.getTransaction().commit();
            response.setMessage("Paper created successfully.");
            response.setSuccess(true);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.setMessage("Error creating paper: " + e.getMessage());
            response.setSuccess(false);
        } finally {
            em.close();
        }
        return response;
    }

    public ResponseMessage updatePaper(Paper paper) {
        ResponseMessage response = new ResponseMessage();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Paper existingPaper = em.find(Paper.class, paper.getId());
            if (existingPaper == null) {
                response.setMessage("Paper not found.");
                response.setSuccess(false);
                return response;
            }
            existingPaper.setTitle(paper.getTitle());
            existingPaper.setAbstractText(paper.getAbstractText());
            existingPaper.setContent(paper.getContent());

            em.getTransaction().commit();
            response.setMessage("Paper updated successfully.");
            response.setSuccess(true);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.setMessage("Error updating paper: " + e.getMessage());
            response.setSuccess(false);
        } finally {
            em.close();
        }
        return response;
    }

    public ResponseMessage submitPaper(Long paperId) {
        ResponseMessage response = new ResponseMessage();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Paper paper = em.find(Paper.class, paperId);
            if (paper == null) {
                response.setMessage("Paper not found.");
                response.setSuccess(false);
                return response;
            }
            paper.setState("SUBMITTED");
            em.getTransaction().commit();
            response.setMessage("Paper submitted successfully.");
            response.setSuccess(true);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.setMessage("Error submitting paper: " + e.getMessage());
            response.setSuccess(false);
        } finally {
            em.close();
        }
        return response;
    }

    public ResponseMessage assignReviewer(Long paperId, User reviewer) {
        ResponseMessage response = new ResponseMessage();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Paper paper = em.find(Paper.class, paperId);
            if (paper == null) {
                response.setMessage("Paper not found.");
                response.setSuccess(false);
                return response;
            }

            Review review = new Review();
            review.setReviewer(reviewer);
            review.setScore(0);
            review.setJustification("");

            paper.getReviews().add(review);
            em.getTransaction().commit();
            response.setMessage("Reviewer assigned successfully.");
            response.setSuccess(true);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.setMessage("Error assigning reviewer: " + e.getMessage());
            response.setSuccess(false);
        } finally {
            em.close();
        }
        return response;
    }
}
