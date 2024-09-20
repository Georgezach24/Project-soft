package gr.conference.papersys;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RestClient {

    private static final String BASE_URI = "http://localhost:8080/conference/api/papers";

    public static String paperCreatePost(String title, String username, String confName, String paperAbstract, String content) {
        Client client = ClientBuilder.newClient();
        Paper paper = new Paper();
        paper.setTitle(title);
        paper.setAuthorNames(username);
        paper.setAbstractText(paperAbstract);
        paper.setContent(content.getBytes());
        Response response = client.target(BASE_URI + "/create")
                                  .request(MediaType.APPLICATION_JSON)
                                  .post(Entity.entity(paper, MediaType.APPLICATION_JSON));
        client.close();
        return response.readEntity(String.class);
    }

    public static void confUpdateRequest() {
        // Update logic
    }

    public static void confUpdatePost(String confName, String newName, String desc) {
        // Post logic
    }
}
