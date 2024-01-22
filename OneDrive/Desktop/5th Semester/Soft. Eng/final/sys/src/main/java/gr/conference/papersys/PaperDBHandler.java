package gr.conference.papersys;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gr.conference.usersys.User;

public class PaperDBHandler {

    private Map<Long, Paper> papers;

    public PaperDBHandler() {
        this.papers = new HashMap<>();
    }

    public void savePaper(Paper paper) {
        paper.setId(generateId());
        paper.setCreationDate(LocalDateTime.now());

        paper.setState(Paper.PaperState.CREATED);

        validateObligatoryInformation(paper);

        
        papers.put(paper.getId(), paper);
    }

    public Paper getPaperById(Long paperId) {
        return papers.get(paperId);
    }

    public List<Paper> getAllPapers() {
        return new ArrayList<>(papers.values());
    }

    public void updatePaper(Paper updatedPaper) {
        
        papers.put(updatedPaper.getId(), updatedPaper);
    }

    public void deletePaper(Long paperId) {
        papers.remove(paperId);
    }

    public List<Paper> getPapersByState(Paper.PaperState state) {
        List<Paper> papersByState = new ArrayList<>();
        for (Paper paper : papers.values()) {
            if (paper.getState() == state) {
                papersByState.add(paper);
            }
        }
        return papersByState;
    }

    public List<Paper> getPapersByReviewer(User reviewer) {
        List<Paper> papersByReviewer = new ArrayList<>();
        for (Paper paper : papers.values()) {
            if (paper.getReviewers().contains(reviewer)) {
                papersByReviewer.add(paper);
            }
        }
        return papersByReviewer;
    }

    public void submitPaper(Long paperId) {
        Paper paper = papers.get(paperId);
        if (paper != null && paper.getState() == Paper.PaperState.CREATED) {
            paper.setState(Paper.PaperState.SUBMITTED);
        }
    }

    public void reviewPaper(Long paperId) {
        Paper paper = papers.get(paperId);
        if (paper != null && paper.getState() == Paper.PaperState.SUBMITTED) {
            paper.setState(Paper.PaperState.REVIEWED);
        }
    }

    public void rejectPaper(Long paperId) {
        Paper paper = papers.get(paperId);
        if (paper != null && paper.getState() == Paper.PaperState.REVIEWED) {
            paper.setState(Paper.PaperState.REJECTED);
        }
    }

    public void approvePaper(Long paperId) {
        Paper paper = papers.get(paperId);
        if (paper != null && paper.getState() == Paper.PaperState.REVIEWED) {
            paper.setState(Paper.PaperState.APPROVED);
        }
    }

    public void acceptPaper(Long paperId) {
        Paper paper = papers.get(paperId);
        if (paper != null && paper.getState() == Paper.PaperState.APPROVED) {
            paper.setState(Paper.PaperState.ACCEPTED);
        }
    }

    
    private Long generateId() {
        return System.currentTimeMillis(); 
    }

    private void validateObligatoryInformation(Paper paper) {
        if (paper.getTitle() == null || paper.getAbstractText() == null ||
            paper.getAuthorNames() == null || paper.getSubmitter() == null ||
            paper.getReviewers() == null || paper.getReviewers().size() != 2) {
            throw new IllegalArgumentException("Invalid paper: Obligatory information missing");
        }
    }
}
