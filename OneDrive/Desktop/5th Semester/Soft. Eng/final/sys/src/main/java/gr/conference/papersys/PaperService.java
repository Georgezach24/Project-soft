package gr.conference.papersys;

public class PaperService {

    // Synchronized method to ensure thread safety
    public synchronized void createPaperInThread(String conferenceName, String creatorUsername, String title) {
        PaperCreationThread thread = new PaperCreationThread(conferenceName, creatorUsername, title);
        thread.start();
    }
}
