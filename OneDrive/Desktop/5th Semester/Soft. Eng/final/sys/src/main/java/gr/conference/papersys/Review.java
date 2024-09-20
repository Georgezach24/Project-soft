package gr.conference.papersys;

import jakarta.persistence.*;
import gr.conference.usersys.User;

@Embeddable
public class Review {

    @Column(name = "score")
    private int score;

    @Column(name = "justification")
    private String justification;

    @ManyToOne
    private User reviewer;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

    
}
