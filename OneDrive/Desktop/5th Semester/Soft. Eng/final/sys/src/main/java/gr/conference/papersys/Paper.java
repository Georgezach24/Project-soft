package gr.conference.papersys;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Date;


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

    @Column(name = "author_names", nullable = true)
    private String authorNames;

    @Column(name = "content")
    private byte[] content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name="user_id")
    private long u_id;

    @Column(name = "conf_id")
    private float conf_id;

    @Column(name="score")
    private int score;

    @Column(name = "paper_state", nullable = false)
    private String paperState;

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

	public long getU_id() {
		return u_id;
	}

	public void setU_id(long u_id) {
		this.u_id = u_id;
	}

	public float getConf_id() {
		return conf_id;
	}

	public void setConf_id(float conf_id) {
		this.conf_id = conf_id;
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
				+ authorNames + ", content=" + Arrays.toString(content) + ", creationDate=" + creationDate + ", u_id="
				+ u_id + ", conf_id=" + conf_id + ", score=" + score + ", paperState=" + paperState + "]";
	}


}
