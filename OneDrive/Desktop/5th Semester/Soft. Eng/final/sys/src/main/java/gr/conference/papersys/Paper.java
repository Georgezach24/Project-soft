package gr.conference.papersys;

import gr.conference.usersys.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paper")
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String abstractText;

    @Column(nullable = false)
    private String authorNames;

    @Lob
    private byte[] content;

    @Column(nullable = false)
    private LocalDateTime creationDate; 

    @ManyToOne
    @JoinColumn(name = "submitter_id", nullable = false)
    private User submitter;

    @ManyToMany
    @JoinTable(
            name = "paper_reviewers",
            joinColumns = @JoinColumn(name = "paper_id"),
            inverseJoinColumns = @JoinColumn(name = "reviewer_id")
    )
    private Set<User> reviewers = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaperState state = PaperState.CREATED;

    
    public Paper() {
      
    }

    public Paper(String title, String abstractText, String authorNames, byte[] content,
                 LocalDateTime creationDate, User submitter) {
        this.title = title;
        this.abstractText = abstractText;
        this.authorNames = authorNames;
        this.content = content;
        this.creationDate = creationDate;
        this.submitter = submitter;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(String authorNames) {
        this.authorNames = authorNames;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    public Set<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Set<User> reviewers) {
        this.reviewers = reviewers;
    }

    public PaperState getState() {
        return state;
    }

    public void setState(PaperState state) {
        this.state = state;
    }

   
    public enum PaperState {
        CREATED, SUBMITTED, REVIEWED, REJECTED, APPROVED, ACCEPTED
    }

}
