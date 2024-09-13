package gr.conference.papersys;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gr.conference.confsys.Conference;
import gr.conference.usersys.User;

@Entity
@Table(name = "paper")
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "paper_title", nullable = false, unique = true)
    private String title;

    @Column(name = "abstract_text")
    private String abstractText;

    @Column(name = "author_names")
    private String authorNames;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;  // Αναφορά στον δημιουργό αντί για το αναγνωριστικό (u_id)

    @ManyToOne
    @JoinColumn(name = "conf_id", nullable = false)
    private Conference conference;  // Αναφορά στο Conference αντί για το conf_id

    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "score")
    private int score;

    @Column(name = "paper_state", nullable = false)
    private String paperState;

    // Getters and setters

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setPaper(this);  // Συσχέτιση του review με το paper
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPaperState() {
        return paperState;
    }

    public void setPaperState(String paperState) {
        this.paperState = paperState;
    }

    @Override
    public String toString() {
        return "Paper [id=" + id + ", title=" + title + ", abstractText=" + abstractText + ", authorNames="
                + authorNames + ", creationDate=" + creationDate + ", creator=" + creator.getName()
                + ", conference=" + conference.getName() + ", score=" + score + ", paperState=" + paperState + "]";
    }
}
