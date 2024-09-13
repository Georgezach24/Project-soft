package gr.conference.papersys;

public class PaperCreationThread extends Thread {
    private String conferenceName;
    private String creatorUsername;
    private String title;

    public PaperCreationThread(String conferenceName, String creatorUsername, String title) {
        this.conferenceName = conferenceName;
        this.creatorUsername = creatorUsername;
        this.title = title;
    }

    @Override
    public void run() {
        try {
            boolean result = PaperDBHandler.createPaper(conferenceName, creatorUsername, title);
            if (result) {
                System.out.println("Paper created successfully");
            } else {
                System.out.println("Paper creation failed");
            }
        } catch (Exception e) {
            // Exception handling to prevent thread crash
            System.err.println("Error in thread execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
